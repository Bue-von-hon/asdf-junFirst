package com.example.satjun1.storage.mapset;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.satjun1.storage.keyvalue.utils.IdGenerator;
import com.example.satjun1.storage.mapset.SortedValueDatabase.BrandPrice;

class SortedValueDatabaseTest {

    @BeforeEach
    void init() {
        SortedValueDatabase database = SortedValueDatabase.getInstance();
        database.clear();

        // 상의만 삽입
        final ConcurrentSkipListSet<BrandPrice> a = new ConcurrentSkipListSet<>();
        final int key1 = IdGenerator.generateIntegerKeyByString("상의");
        // 2번째로 고가의 브랜드
        a.add(new BrandPrice("A", 11200));
        // 3번째로 저렴한 브랜드
        a.add(new BrandPrice("B", 10300));
        // 가장 저렴한 브핸드
        a.add(new BrandPrice("C", 10000));
        // 2번째로 저렴한 브랜드
        a.add(new BrandPrice("D", 10100));
        a.add(new BrandPrice("E", 10700));
        // 3번째로 고가의 브랜드
        a.add(new BrandPrice("F", 11100));
        a.add(new BrandPrice("G", 10500));
        a.add(new BrandPrice("H", 10800));
        // 가장 고가의 브랜드
        a.add(new BrandPrice("I", 11400));
        SortedValueDatabase.MAP_SET.put(key1, a);
    }

    @Test
    @DisplayName("2번째로 고가/저가인 브랜드를 가져와야함")
    void test1() {
        SortedValueDatabase database = SortedValueDatabase.getInstance();

        // 2번째로 저렴한 브랜드 D를 가져와야함, 가장 저렴한건 {C, 10000}
        BrandPrice brandPrice1 = database.secondFirst("상의");
        assertNotNull(brandPrice1);
        assertEquals(10100, brandPrice1.getPrice());
        assertEquals("D", brandPrice1.getBrand());

        // 2번째로 고가의 브랜드 A를 가져와야함, 가장 고가는 {I, 11400}
        BrandPrice brandPrice2 = database.secondLast("상의");
        assertNotNull(brandPrice2);
        assertEquals(11200, brandPrice2.getPrice());
        assertEquals("A", brandPrice2.getBrand());
    }

    @Test
    @DisplayName("2번째로 고가/저가인 브랜드가 삭제되면, 3번째로 고가/저가인 브랜드가 승격되어야 한다.")
    void test2() {
        SortedValueDatabase database = SortedValueDatabase.getInstance();

        final List<String> categories1 = Arrays.asList("상의");
        database.delete("D", categories1);
        // 2번째로 저가의 브랜드 D가 지워졌음
        // 그래서 B를 가져와야함, 가장 저렴한건 {C, 10000}
        BrandPrice brandPrice2 = database.secondFirst("상의");
        assertNotNull(brandPrice2);
        assertEquals(10300, brandPrice2.getPrice());
        assertEquals("B", brandPrice2.getBrand());


        final List<String> categories2 = Arrays.asList("상의");
        database.delete("A", categories2);
        // 2번째로 고가의 브랜드 A가 지워졌음
        // 그래서 F를 가져와야함, 가장 고가는 {I, 11400}
        BrandPrice brandPrice4 = database.secondLast("상의");
        assertNotNull(brandPrice4);
        assertEquals(11100, brandPrice4.getPrice());
        assertEquals("F", brandPrice4.getBrand());
    }

    @Test
    @DisplayName("가장 고가/저가의 브랜드가 삭제되면, 2번재로 고가/저가인 브랜드는 최고가/최저가 브랜드가 된다. 3번째로 고가/저가의 브랜드가 2번째 브랜드로 승격되야함.")
    void test3() {
        SortedValueDatabase database = SortedValueDatabase.getInstance();

        // 가장 고가의 브랜드 삭제
        final List<String> categories1 = Arrays.asList("상의");
        database.delete("I", categories1);
        BrandPrice brandPrice1 = database.secondLast("상의");
        assertNotNull(brandPrice1);
        assertEquals(11100, brandPrice1.getPrice());
        assertEquals("F", brandPrice1.getBrand());

        // 가장 저가의 브랜드 삭제
        final List<String> categories2 = Arrays.asList("상의");
        database.delete("C", categories2);
        BrandPrice brandPrice2 = database.secondFirst("상의");
        assertNotNull(brandPrice2);
        assertEquals(10300, brandPrice2.getPrice());
        assertEquals("B", brandPrice2.getBrand());
    }

    @Test
    @DisplayName("3번재로 고가/저가의 브랜드가 삭제되어도, 2번째로 고가/저가인 브랜드는 영향을 받으면 안된다.")
    void test4() {
        SortedValueDatabase database = SortedValueDatabase.getInstance();

        // 3번재로 고가인 브랜드 삭제
        final List<String> categories1 = Arrays.asList("상의");
        database.delete("F", categories1);
        // 2번째로 저렴한 브랜드 D를 가져와야함, 가장 저렴한건 {C, 10000}
        BrandPrice brandPrice1 = database.secondFirst("상의");
        assertNotNull(brandPrice1);
        assertEquals(10100, brandPrice1.getPrice());
        assertEquals("D", brandPrice1.getBrand());

        // 3번째로 저가의 브랜드 삭제
        final List<String> categories2 = Arrays.asList("상의");
        database.delete("B", categories2);
        // 2번째로 고가의 브랜드 A를 가져와야함, 가장 고가는 {I, 11400}
        BrandPrice brandPrice2 = database.secondLast("상의");
        assertNotNull(brandPrice2);
        assertEquals(11200, brandPrice2.getPrice());
        assertEquals("A", brandPrice2.getBrand());
    }
}