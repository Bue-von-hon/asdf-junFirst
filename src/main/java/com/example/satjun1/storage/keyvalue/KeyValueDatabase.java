package com.example.satjun1.storage.keyvalue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.lang.Nullable;

import com.example.satjun1.Brand;
import com.example.satjun1.Brand.Category;
import com.example.satjun1.storage.mapset.SortedValueDatabase;
import com.example.satjun1.storage.mapset.SortedValueDatabase.BrandPrice;
import com.example.satjun1.storage.keyvalue.utils.IdGenerator;

public final class KeyValueDatabase {
    @VisibleForTesting
    static final ConcurrentHashMap<Integer, BrandLowHighPricePair> map = new ConcurrentHashMap<>();
    private static final KeyValueDatabase INSTANCE = new KeyValueDatabase();
    private static final SortedValueDatabase SORTED_VALUE_DATABASE = SortedValueDatabase.getInstance();

    static {
        map.put(IdGenerator.generateIntegerKeyByString("상의"), new BrandLowHighPricePair("C", 10000, "I", 11400, "상의"));
        map.put(IdGenerator.generateIntegerKeyByString("아우터"), new BrandLowHighPricePair("E", 5000, "F", 7200, "아우터"));
        map.put(IdGenerator.generateIntegerKeyByString("바지"), new BrandLowHighPricePair("D", 3000, "A", 4200, "바지"));
        map.put(IdGenerator.generateIntegerKeyByString("스니커즈"), new BrandLowHighPricePair("A", 9000, "E", 9900, "스니커즈"));
        map.put(IdGenerator.generateIntegerKeyByString("가방"), new BrandLowHighPricePair("A", 2000, "D", 2500, "가방"));
        map.put(IdGenerator.generateIntegerKeyByString("모자"), new BrandLowHighPricePair("D", 1500, "B", 2000, "모자"));
        map.put(IdGenerator.generateIntegerKeyByString("양말"), new BrandLowHighPricePair("I", 1700, "D", 2400, "양말"));
        map.put(IdGenerator.generateIntegerKeyByString("액세서리"), new BrandLowHighPricePair("F", 1900, "I", 2400, "액세서리"));
    }

    private KeyValueDatabase() {
    }

    public static KeyValueDatabase getInstance() {
        return INSTANCE;
    }

    /**
     * low와 high 사이의 가격인지 확인함.
     *
     * @param low
     * @param high
     * @param price
     * @return
     */
    private static boolean ignoreNewBrandIfPriceWithinRange(int low, int high, int price) {
        return low < price && high > price;
    }

    /**
     * 동일한 카테고리인지 문자열을 비교함.
     *
     * @param a
     * @param b
     * @return
     */
    private static boolean isNotSameCategory(String a, String b) {
        return !a.equals(b);
    }

    @VisibleForTesting
    void clear() {
        map.clear();
    }

    /**
     * 카테고리 이름을 키로, 아래의 데이터를 리턴.
     * 카테고리: {최저가 브랜드의 이름, 최저가, 최고가 브랜드의 이름, 최고가}
     *
     * @param categoryName
     * @return
     */
    @Nullable
    public BrandLowHighPricePair find(String categoryName) {
        if (categoryName == null) {throw new AssertionError();}
        return map.getOrDefault(IdGenerator.generateIntegerKeyByString(categoryName), null);
    }

    /**
     * 키-벨류 스토리지에 저장된 모든 아래와 같은 데이터를 리턴.
     * 카테고리: {최저가 브랜드의 이름, 최저가, 최고가 브랜드의 이름, 최고가}
     *
     * @return
     */
    public List<BrandLowHighPricePair> findAll () {
        if (map.isEmpty()) {
            return Collections.emptyList();
        }
        final List<BrandLowHighPricePair> pricePairs = new ArrayList<>();
        map.forEach((key, value) -> pricePairs.add(value));
        return pricePairs;
    }

    /**
     * 신규 브랜드를 키-벨류 스토리지에 추가함
     * key-value 스토리지는 카테고리별 아래와 같은 데이터를 저장중임.
     * 카테고리: {최저가 브랜드의 이름, 최저가, 최고가 브랜드의 이름, 최고가}
     * 신규 브랜드 추가시, 브랜드의 이름과 여러가지 카테고리와 그것들의 가격이 들어옴.
     * 따라서 카테고리를 순회하면서, 키-벨류 스트로지를 업데이트해주어야함
     *
     * @param newBrand
     */
    public void add(Brand newBrand) {
        // key-value storage 추가: 신규 브랜드에 담겨있는 모든 카테고리를 순회하면서 키-벨류 스토리지에 추가
        final List<Category> categoriesOfNewBrand = newBrand.categories();
        final String nameOfNewBrand = newBrand.brand();
        final List<BrandLowHighPricePair> brandPricesByCategoryList = findAll();

        for (BrandLowHighPricePair pricePair : brandPricesByCategoryList) {
            for (Category categoryOfNewBrand : categoriesOfNewBrand) {
                // 카테고리가 일치하는 경우만 고려
                if (isNotSameCategory(categoryOfNewBrand.category(), pricePair.category())) {
                    continue;
                }

                final int priceOfNewBrand = categoryOfNewBrand.price();
                final int lowPrice = pricePair.lowPrice();
                final int highPrice = pricePair.highPrice();
                // 만약 low < 신규 브랜드 가격 < high 이라면 무시
                if (ignoreNewBrandIfPriceWithinRange(lowPrice, highPrice, priceOfNewBrand)) {
                    continue;
                }
                updateLowesBrand(pricePair, priceOfNewBrand, nameOfNewBrand);
                updateHighestBrand(pricePair, priceOfNewBrand, nameOfNewBrand);
            }
        }
    }

    /**
     * 만약 신규 브랜드 가격 > high, 가장 비싼 브랜드 정보 업데이트
     * @param pair
     * @param priceOfNewBrand
     * @param nameOfNewBrand
     */
    private void updateHighestBrand(BrandLowHighPricePair pair, int priceOfNewBrand, String nameOfNewBrand) {
        // 만약 "기존의 최고가 >= 신규 가격"이라면 무시
        if (pair.highPrice() >= priceOfNewBrand) {
            return;
        }

        final BrandLowHighPricePair updatedPrice = new BrandLowHighPricePair(
                pair.lowBrand(),
                pair.lowPrice(),
                nameOfNewBrand,
                priceOfNewBrand,
                pair.category()
        );
        update(pair.category(), updatedPrice);
    }

    /**
     * 만약 신규 브랜드 가격 < low, 가장 저렴한 브랜드 정보 업데이트
     * @param pair
     * @param priceOfNewBrand
     * @param nameOfNewBrand
     */
    private void updateLowesBrand(BrandLowHighPricePair pair, int priceOfNewBrand, String nameOfNewBrand) {
        // 만약 "기존의 최저가 <= 신규 가격"이라면 무시
        if (pair.lowPrice() <= priceOfNewBrand) {
            return;
        }

        final BrandLowHighPricePair updatedPrice = new BrandLowHighPricePair(
                nameOfNewBrand,
                priceOfNewBrand,
                pair.highBrand(),
                pair.highPrice(),
                pair.category()
        );
        update(pair.category(), updatedPrice);
    }

    private void update(String categoryName, BrandLowHighPricePair brandLowHighPricePair) {
        if (categoryName == null) {throw new AssertionError();}
        final int key = IdGenerator.generateIntegerKeyByString(categoryName);
        if (!map.containsKey(key)) {
            return;
        }
        map.replace(key, brandLowHighPricePair);
    }

    /**
     * 브랜드 이름으로 키-벨류 스토리지에서 해당 브랜드를 삭제.
     *
     * @param nameOfBrandToDelete
     */
    public void delete(String nameOfBrandToDelete) {
        // 저장된 모든 카테고리를 순회
        for (Entry<Integer, BrandLowHighPricePair> entry : map.entrySet()) {
            final BrandLowHighPricePair oldBrand = entry.getValue();
            final String highBrand = oldBrand.highBrand();
            final String lowBrand = oldBrand.lowBrand();

            // 최저가도 최고가도 아닌 브랜드를 삭제하는 경우, 무시
            if (!nameOfBrandToDelete.equals(highBrand) && !nameOfBrandToDelete.equals(lowBrand)) {
                continue;
            }

            final String categoryOfOldBrand = oldBrand.category();
            String newHighBrand = oldBrand.highBrand();
            String newLowBrand = oldBrand.lowBrand();
            int newHighPrice = oldBrand.highPrice();
            int newLowPrice = oldBrand.lowPrice();

            // 최고가 브랜드를 삭제하는 경우
            if (nameOfBrandToDelete.equals(highBrand)) {
                // 2번째로 비싼걸 제일 비싼걸로 대체
                final BrandPrice secondHighest = SORTED_VALUE_DATABASE.secondLast(categoryOfOldBrand);
                newHighBrand = secondHighest.getBrand();
                newHighPrice = secondHighest.getPrice();
            }
            // 최저가 브랜드를 삭제하는 경우
            else if (nameOfBrandToDelete.equals(lowBrand)) {
                // 2번째로 싼걸 제일 싼거로 대체
                final BrandPrice secondLow = SORTED_VALUE_DATABASE.secondFirst(categoryOfOldBrand);
                newLowBrand = secondLow.getBrand();
                newLowPrice = secondLow.getPrice();
            }
            final BrandLowHighPricePair updatedPrice = new BrandLowHighPricePair(
                    newLowBrand,
                    newLowPrice,
                    newHighBrand,
                    newHighPrice,
                    categoryOfOldBrand
            );
            update(categoryOfOldBrand, updatedPrice);
        }
    }
}
