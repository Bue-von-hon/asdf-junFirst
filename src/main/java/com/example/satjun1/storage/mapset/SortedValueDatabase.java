package com.example.satjun1.storage.mapset;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.assertj.core.util.VisibleForTesting;

import com.example.satjun1.Brand;
import com.example.satjun1.Brand.Category;
import com.example.satjun1.storage.keyvalue.utils.IdGenerator;

// 브랜드가 추가/삭제 될때마다 카테고리별 브랜드의 가격 리스트를 수정해야함
// 브랜드 추가시: 카테고리별 브랜드의 가격 리스트에 add 호출
// 브랜드 삭제시: 카테고리별 브랜드의 가격 리스트에 delete 호출
// rank 데이터베이스 대체용 -> 대체못함, 전체 브랜드중 최저가 브랜드 알 수 없음
public class SortedValueDatabase {
    @VisibleForTesting
    static final ConcurrentHashMap<Integer, ConcurrentSkipListSet<BrandPrice>> MAP_SET = new ConcurrentHashMap<>();
    private static final SortedValueDatabase INSTANCE = new SortedValueDatabase();

    private SortedValueDatabase() {
    }

    public static SortedValueDatabase getInstance() {
        return INSTANCE;
    }

    static {
        // 상의
        final ConcurrentSkipListSet<BrandPrice> a = new ConcurrentSkipListSet<>();
        final int key1 = IdGenerator.generateIntegerKeyByString("상의");
        a.add(new BrandPrice("A", 11200));
        a.add(new BrandPrice("B", 10500));
        a.add(new BrandPrice("C", 10000));
        a.add(new BrandPrice("D", 10100));
        a.add(new BrandPrice("E", 10700));
        a.add(new BrandPrice("F", 11200));
        a.add(new BrandPrice("G", 10500));
        a.add(new BrandPrice("H", 10800));
        a.add(new BrandPrice("I", 11400));
        MAP_SET.put(key1, a);

        // 아우터
        final ConcurrentSkipListSet<BrandPrice> b = new ConcurrentSkipListSet<>();
        final int key2 = IdGenerator.generateIntegerKeyByString("아우터");
        b.add(new BrandPrice("A", 5500));
        b.add(new BrandPrice("B", 5900));
        b.add(new BrandPrice("C", 6200));
        b.add(new BrandPrice("D", 5100));
        b.add(new BrandPrice("E", 5000));
        b.add(new BrandPrice("F", 7200));
        b.add(new BrandPrice("G", 5800));
        b.add(new BrandPrice("H", 6300));
        b.add(new BrandPrice("I", 6700));
        MAP_SET.put(key2, b);

        // 바지
        final ConcurrentSkipListSet<BrandPrice> c = new ConcurrentSkipListSet<>();
        final int key3 = IdGenerator.generateIntegerKeyByString("바지");
        c.add(new BrandPrice("A", 4200));
        c.add(new BrandPrice("B", 3800));
        c.add(new BrandPrice("C", 3300));
        c.add(new BrandPrice("D", 3000));
        c.add(new BrandPrice("E", 3800));
        c.add(new BrandPrice("F", 4000));
        c.add(new BrandPrice("G", 3900));
        c.add(new BrandPrice("H", 3100));
        c.add(new BrandPrice("I", 3200));
        MAP_SET.put(key3, c);

        // 스니커지
        final ConcurrentSkipListSet<BrandPrice> d = new ConcurrentSkipListSet<>();
        final int key4 = IdGenerator.generateIntegerKeyByString("스니커즈");
        d.add(new BrandPrice("A", 9000));
        d.add(new BrandPrice("B", 9100));
        d.add(new BrandPrice("C", 9200));
        d.add(new BrandPrice("D", 9500));
        d.add(new BrandPrice("E", 9900));
        d.add(new BrandPrice("F", 9300));
        d.add(new BrandPrice("G", 9000));
        d.add(new BrandPrice("H", 9700));
        d.add(new BrandPrice("I", 9500));
        MAP_SET.put(key4, d);

        // 가방
        final ConcurrentSkipListSet<BrandPrice> e = new ConcurrentSkipListSet<>();
        final int key5 = IdGenerator.generateIntegerKeyByString("가방");
        e.add(new BrandPrice("A", 2000));
        e.add(new BrandPrice("B", 2100));
        e.add(new BrandPrice("C", 2200));
        e.add(new BrandPrice("D", 2500));
        e.add(new BrandPrice("E", 2300));
        e.add(new BrandPrice("F", 2100));
        e.add(new BrandPrice("G", 2200));
        e.add(new BrandPrice("H", 2100));
        e.add(new BrandPrice("I", 2400));
        MAP_SET.put(key5, e);

        // 모자
        final ConcurrentSkipListSet<BrandPrice> f = new ConcurrentSkipListSet<>();
        final int key6 = IdGenerator.generateIntegerKeyByString("모자");
        f.add(new BrandPrice("A", 1700));
        f.add(new BrandPrice("B", 2000));
        f.add(new BrandPrice("C", 1900));
        f.add(new BrandPrice("D", 1500));
        f.add(new BrandPrice("E", 1800));
        f.add(new BrandPrice("F", 1600));
        f.add(new BrandPrice("G", 1700));
        f.add(new BrandPrice("H", 1600));
        f.add(new BrandPrice("I", 1700));
        MAP_SET.put(key6, f);

        // 양말
        final ConcurrentSkipListSet<BrandPrice> g = new ConcurrentSkipListSet<>();
        final int key7 = IdGenerator.generateIntegerKeyByString("양말");
        g.add(new BrandPrice("A", 1800));
        g.add(new BrandPrice("B", 2000));
        g.add(new BrandPrice("C", 2200));
        g.add(new BrandPrice("D", 2400));
        g.add(new BrandPrice("E", 2100));
        g.add(new BrandPrice("F", 2300));
        g.add(new BrandPrice("G", 2100));
        g.add(new BrandPrice("H", 2000));
        g.add(new BrandPrice("I", 1700));
        MAP_SET.put(key7, g);

        // 액세서리
        final ConcurrentSkipListSet<BrandPrice> h = new ConcurrentSkipListSet<>();
        final int key8 = IdGenerator.generateIntegerKeyByString("액세서리");
        h.add(new BrandPrice("A", 2300));
        h.add(new BrandPrice("B", 2200));
        h.add(new BrandPrice("C", 2100));
        h.add(new BrandPrice("D", 2000));
        h.add(new BrandPrice("E", 2100));
        h.add(new BrandPrice("F", 1900));
        h.add(new BrandPrice("G", 2000));
        h.add(new BrandPrice("H", 2000));
        h.add(new BrandPrice("I", 2400));
        MAP_SET.put(key8, h);
    }

    @VisibleForTesting
    void clear() {
        MAP_SET.forEach((key, value) -> value.clear());
    }

    // 이 데이터베이스에서 관리하는 데이터는 아래와 같은 형태를 띄고 있음
    // 카테고리: [{가격, 브랜드}, {가격, 브랜드}]
    // 카테고리는 절대 추가/삭제/수정되지 않음
    // 카테고리와 함게 추가/삭제될 {가격, 브랜드} 데이터셋이 주어질것
    // -> 수정 := 삭제 후 삽입, 같은 말임

    /**
     * 신규 브랜드를 데이터베이스에 추가.
     *
     * @param newBrand
     */
    public synchronized void add(Brand newBrand) {
        final List<Category> categories = newBrand.categories();
        final String nameOfNewBrand = newBrand.brand();
        for (Category category : categories) {
            // 카테고리 이름으로 아이디 생성
            final String nameOfCategory = category.category();
            final int key = IdGenerator.generateIntegerKeyByString(nameOfCategory);

            // 신규 브랜드를 저장
            final ConcurrentSkipListSet<BrandPrice> brandPrices = MAP_SET.get(key);
            final int priceOfCategory = category.price();
            brandPrices.add(new BrandPrice(nameOfNewBrand, priceOfCategory));
            MAP_SET.put(key, brandPrices);
        }
    }

    /**
     * 기존 브랜드를 데이터베이스에서 제거.
     *
     * @param nameOfBrandToDelete
     * @param categories
     */
    public synchronized void delete(String nameOfBrandToDelete, List<String> categories) {
        for (String nameOfCategory : categories) {
            // 카테고리 이름으로 키 생성
            final int key = IdGenerator.generateIntegerKeyByString(nameOfCategory);

            // 기존 데이터 가져오기
            final ConcurrentSkipListSet<BrandPrice> brandPrices = MAP_SET.get(key);
            // 삭제할 브랜드를 제외시킬 신규 리스트 생성
            final ConcurrentSkipListSet<BrandPrice> list = new ConcurrentSkipListSet<>();
            for (BrandPrice brandPrice : brandPrices) {
                final String nameOfBrand = brandPrice.brand;
                // 삭제하려는 브랜드와 동일한 브랜드면, 신규 리스트에 넣지 않기
                if (nameOfBrand.equals(nameOfBrandToDelete)) {
                    continue;
                }
                list.add(brandPrice);
            }
            MAP_SET.put(key, list);
        }
    }

    /**
     * 2번째 최고가 브랜드 리턴
     * @param category
     * @return
     */
    public BrandPrice secondLast(String category) {
        final int key = IdGenerator.generateIntegerKeyByString(category);
        final ConcurrentSkipListSet<BrandPrice> brandPrices = MAP_SET.get(key);
        final Iterator<BrandPrice> iterator = brandPrices.descendingIterator();
        iterator.next();
        return iterator.next();
    }

    /**
     * 2번째 최저가 브랜드 리턴
     * @param category
     * @return
     */
    public BrandPrice secondFirst(String category) {
        final int key = IdGenerator.generateIntegerKeyByString(category);
        final ConcurrentSkipListSet<BrandPrice> brandPrices = MAP_SET.get(key);
        final Iterator<BrandPrice> iterator = brandPrices.iterator();
        iterator.next();
        return iterator.next();
    }

    public static final class BrandPrice implements Comparable<BrandPrice>{
        private final String brand;
        private final int price;

        public BrandPrice(String brand, int price) {
            this.brand = brand;
            this.price = price;
        }

        @Override
        public int compareTo(BrandPrice data) {
            return Integer.compare(this.price, data.price);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) {return false;}
            final BrandPrice that = (BrandPrice) o;
            return price == that.price && Objects.equals(brand, that.brand);
        }

        @Override
        public int hashCode() {
            return Objects.hash(brand, price);
        }

        public String getBrand() {
            return brand;
        }

        public int getPrice() {
            return price;
        }
    }
}
