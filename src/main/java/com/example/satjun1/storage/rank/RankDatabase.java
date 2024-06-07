package com.example.satjun1.storage.rank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.lang.Nullable;

import com.example.satjun1.Brand;
import com.example.satjun1.Brand.Category;


public final class RankDatabase {
    @VisibleForTesting
    static final ConcurrentSkipListSet<LowestPriceListByCategory> SET = new ConcurrentSkipListSet<>();
    private static final RankDatabase INSTANCE = new RankDatabase();

    // 카테고리 : {가격, 브랜드}, 정렬은 가격 기준 (최고/최저 다 가능해야)
    static {
        // A
        final List<CategoryPricePair> aList = new ArrayList<>();
        aList.add(new CategoryPricePair("상의", 11200));
        aList.add(new CategoryPricePair("아우터", 5500));
        aList.add(new CategoryPricePair("바지", 4200));
        aList.add(new CategoryPricePair("스니커즈", 9000));
        aList.add(new CategoryPricePair("가방", 2000));
        aList.add(new CategoryPricePair("모자", 1700));
        aList.add(new CategoryPricePair("양말", 1800));
        aList.add(new CategoryPricePair("액세서리", 2300));
        final LowestPriceListByCategory dataA = new LowestPriceListByCategory(aList, 37700, "A");


        // B
        final List<CategoryPricePair> bList = new ArrayList<>();
        bList.add(new CategoryPricePair("상의", 10500));
        bList.add(new CategoryPricePair("아우터", 5900));
        bList.add(new CategoryPricePair("바지", 3800));
        bList.add(new CategoryPricePair("스니커즈", 9100));
        bList.add(new CategoryPricePair("가방", 2100));
        bList.add(new CategoryPricePair("모자", 2000));
        bList.add(new CategoryPricePair("양말", 2000));
        bList.add(new CategoryPricePair("액세서리", 2200));
        final LowestPriceListByCategory dataB = new LowestPriceListByCategory(bList, 37600, "B");

        // C
        final List<CategoryPricePair> cList = new ArrayList<>();
        cList.add(new CategoryPricePair("상의", 10000));
        cList.add(new CategoryPricePair("아우터", 6200));
        cList.add(new CategoryPricePair("바지", 3300));
        cList.add(new CategoryPricePair("스니커즈", 9200));
        cList.add(new CategoryPricePair("가방", 2200));
        cList.add(new CategoryPricePair("모자", 1900));
        cList.add(new CategoryPricePair("양말", 2200));
        cList.add(new CategoryPricePair("액세서리", 2100));
        final LowestPriceListByCategory dataC = new LowestPriceListByCategory(cList, 37100, "C");

        // D
        final List<CategoryPricePair> dList = new ArrayList<>();
        dList.add(new CategoryPricePair("상의", 10100));
        dList.add(new CategoryPricePair("아우터", 5100));
        dList.add(new CategoryPricePair("바지", 3000));
        dList.add(new CategoryPricePair("스니커즈", 9500));
        dList.add(new CategoryPricePair("가방", 2500));
        dList.add(new CategoryPricePair("모자", 1500));
        dList.add(new CategoryPricePair("양말", 2400));
        dList.add(new CategoryPricePair("액세서리", 2000));
        final LowestPriceListByCategory dataD = new LowestPriceListByCategory(dList, 36100, "D");

        // E
        final List<CategoryPricePair> eList = new ArrayList<>();
        eList.add(new CategoryPricePair("상의", 10700));
        eList.add(new CategoryPricePair("아우터", 5000));
        eList.add(new CategoryPricePair("바지", 3800));
        eList.add(new CategoryPricePair("스니커즈", 9900));
        eList.add(new CategoryPricePair("가방", 2300));
        eList.add(new CategoryPricePair("모자", 1800));
        eList.add(new CategoryPricePair("양말", 2100));
        eList.add(new CategoryPricePair("액세서리", 2100));
        final LowestPriceListByCategory dataE = new LowestPriceListByCategory(eList, 37700, "E");

        // F
        final List<CategoryPricePair> fList = new ArrayList<>();
        fList.add(new CategoryPricePair("상의", 11200));
        fList.add(new CategoryPricePair("아우터", 7200));
        fList.add(new CategoryPricePair("바지", 4000));
        fList.add(new CategoryPricePair("스니커즈", 9300));
        fList.add(new CategoryPricePair("가방", 2100));
        fList.add(new CategoryPricePair("모자", 1600));
        fList.add(new CategoryPricePair("양말", 2300));
        fList.add(new CategoryPricePair("액세서리", 1900));
        final LowestPriceListByCategory dataF = new LowestPriceListByCategory(fList, 39600, "F");

        // G
        final List<CategoryPricePair> gList = new ArrayList<>();
        gList.add(new CategoryPricePair("상의", 10500));
        gList.add(new CategoryPricePair("아우터", 5800));
        gList.add(new CategoryPricePair("바지", 3900));
        gList.add(new CategoryPricePair("스니커즈", 9000));
        gList.add(new CategoryPricePair("가방", 2200));
        gList.add(new CategoryPricePair("모자", 1700));
        gList.add(new CategoryPricePair("양말", 2100));
        gList.add(new CategoryPricePair("액세서리", 2000));
        final LowestPriceListByCategory dataG = new LowestPriceListByCategory(gList, 37200, "G");

        // H
        final List<CategoryPricePair> hList = new ArrayList<>();
        hList.add(new CategoryPricePair("상의", 10800));
        hList.add(new CategoryPricePair("아우터", 6300));
        hList.add(new CategoryPricePair("바지", 3100));
        hList.add(new CategoryPricePair("스니커즈", 9700));
        hList.add(new CategoryPricePair("가방", 2100));
        hList.add(new CategoryPricePair("모자", 1600));
        hList.add(new CategoryPricePair("양말", 2000));
        hList.add(new CategoryPricePair("액세서리", 2000));
        final LowestPriceListByCategory dataH = new LowestPriceListByCategory(hList, 37600, "H");

        // I
        final List<CategoryPricePair> iList = new ArrayList<>();
        iList.add(new CategoryPricePair("상의", 11400));
        iList.add(new CategoryPricePair("아우터", 6700));
        iList.add(new CategoryPricePair("바지", 3200));
        iList.add(new CategoryPricePair("스니커즈", 9500));
        iList.add(new CategoryPricePair("가방", 2400));
        iList.add(new CategoryPricePair("모자", 1700));
        iList.add(new CategoryPricePair("양말", 1700));
        iList.add(new CategoryPricePair("액세서리", 2400));
        final LowestPriceListByCategory dataI = new LowestPriceListByCategory(iList, 39000, "I");

        SET.add(dataA);
        SET.add(dataB);
        SET.add(dataC);
        SET.add(dataD);
        SET.add(dataE);
        SET.add(dataF);
        SET.add(dataG);
        SET.add(dataH);
        SET.add(dataI);
    }

    private RankDatabase() {
    }

    @VisibleForTesting
    void clear() {
        SET.clear();
    }

    public static RankDatabase getInstance() {
        return INSTANCE;
    }

    /**
     * 정렬된 데이터셋 중 가장 첫번째 원소를 가져옴.
     * 데이터셋은 다음과 같음.
     * {브랜드, 총합, 개별 카테고리들의 가격}
     *
     * @return LowestPriceListByCategory
     */
    @Nullable
    public LowestPriceListByCategory topOne() {
        if (SET.isEmpty()) {
            return null;
        }
        return SET.first();
    }

    /**
     * 신규 브랜드를 랭크 스토리지에 추가해야함.
     * 랭크 스토리지는 브랜드별 총 가격 데이터셋을 저장하고 있음. (총합 기준으로 정렬중임)
     * {브랜드, 총합, 개별 카테고리들의 가격}
     * 신규 브랜드 추가시, 브랜드의 이름과 여러가지 카테고리와 그것들의 가격이 들어옴.
     * 따라서 단순하게 브랜드 이름과 총합을 구해서 여러 카테고리들과 같이 삽입하면됌.
     * @param newBrand
     */
    public void add(Brand newBrand) {
        // rank storage 추가: 신규 브랜드에 담긴 모든 카테고리를 추가
        final List<Category> categories = newBrand.categories();
        final List<CategoryPricePair> lowestPriceList = new ArrayList<>();
        int total = 0;
        for (Category category : categories) {
            final String nameOfCategory = category.category();
            final int priceOfCategory = category.price();
            final CategoryPricePair pair = new CategoryPricePair(nameOfCategory, priceOfCategory);
            lowestPriceList.add(pair);
            total += category.price();
        }

        // 신규 브랜드 정보를 스토리지에 삽입
        final String nameOfNewBrand = newBrand.brand();
        final LowestPriceListByCategory data = new LowestPriceListByCategory(
                lowestPriceList,
                total,
                nameOfNewBrand
        );
        SET.add(data);
    }

    /**
     * 랭크 스토리지에서는 그냥 브랜드 이름으로 비교해서, 일치하는 데이터셋을 삭제하면 끝
     * @param nameOfBrandToDelete
     */
    public void delete(String nameOfBrandToDelete) {
        final Iterator<LowestPriceListByCategory> iterator = SET.iterator();

        // todo: 삭제시 전체 순회할 필요 없음.
        // 전체 순회하면서, 브랜드 이름이 일치하는 브랜드 삭제
        while (iterator.hasNext()) {
            final LowestPriceListByCategory lowestPriceListByCategory = iterator.next();
            final String nameOfBrand = lowestPriceListByCategory.getBrand();
            if (nameOfBrand.equals(nameOfBrandToDelete)) {
                iterator.remove();
                break;
            }
        }
    }
}
