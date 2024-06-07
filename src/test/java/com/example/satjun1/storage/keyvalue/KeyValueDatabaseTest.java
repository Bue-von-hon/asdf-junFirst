package com.example.satjun1.storage.keyvalue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.satjun1.Brand;
import com.example.satjun1.Brand.Category;
import com.example.satjun1.storage.keyvalue.utils.IdGenerator;
import com.example.satjun1.storage.mapset.SortedValueDatabase;

@ExtendWith(MockitoExtension.class)
class KeyValueDatabaseTest {

    @BeforeEach
    void init() {
        KeyValueDatabase database = KeyValueDatabase.getInstance();
        database.clear();
    }

    @Test
    @DisplayName("여러 데이터중 정확히 일치하는 데이터를 가져와야함")
    void test1() {
        final KeyValueDatabase database = KeyValueDatabase.getInstance();
        final int key1 = IdGenerator.generateIntegerKeyByString("상의");
        KeyValueDatabase.map.put(key1, new BrandLowHighPricePair("C", 10000, "I", 11400, "상의"));

        final int key2 = IdGenerator.generateIntegerKeyByString("아우터");
        KeyValueDatabase.map.put(key2, new BrandLowHighPricePair("E", 5000, "F", 7200, "아우터"));

        final int key3 = IdGenerator.generateIntegerKeyByString("바지");
        KeyValueDatabase.map.put(key3, new BrandLowHighPricePair("D", 3000, "A", 4200, "바지"));

        final BrandLowHighPricePair pair = database.find("상의");
        assertNotNull(pair);
        assertEquals("C", pair.lowBrand());
        assertEquals("I", pair.highBrand());
        assertEquals(10000, pair.lowPrice());
        assertEquals(11400, pair.highPrice());
        assertEquals("상의", pair.category());
    }

    @Test
    @DisplayName("전체 데이터 다 가져와야함")
    void tes2() {
        final KeyValueDatabase database = KeyValueDatabase.getInstance();
        final int key1 = IdGenerator.generateIntegerKeyByString("상의");
        KeyValueDatabase.map.put(key1, new BrandLowHighPricePair("C", 10000, "I", 11400, "상의"));
        final int key2 = IdGenerator.generateIntegerKeyByString("아우터");
        KeyValueDatabase.map.put(key2, new BrandLowHighPricePair("E", 5000, "F", 7200, "아우터"));
        final int key3 = IdGenerator.generateIntegerKeyByString("바지");
        KeyValueDatabase.map.put(key3, new BrandLowHighPricePair("D", 3000, "A", 4200, "바지"));
        final List<BrandLowHighPricePair> all = database.findAll();

        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("신규 브랜드 추가 및 삭제")
    void test3() {
        final KeyValueDatabase database = KeyValueDatabase.getInstance();
        final int key1 = IdGenerator.generateIntegerKeyByString("상의");
        KeyValueDatabase.map.put(key1, new BrandLowHighPricePair("C", 10000, "I", 11400, "상의"));
        final int key2 = IdGenerator.generateIntegerKeyByString("아우터");
        KeyValueDatabase.map.put(key2, new BrandLowHighPricePair("E", 5000, "F", 7200, "아우터"));
        final int key3 = IdGenerator.generateIntegerKeyByString("바지");
        KeyValueDatabase.map.put(key3, new BrandLowHighPricePair("D", 3000, "A", 4200, "바지"));

        // 신규 브랜드를 생성
        // 최저가 상의와 최고가 바지, 중간 가격의 아우터
        final List<Category> categories = new ArrayList<>();
        // 10000 ~ 11400
        categories.add(new Category("상의", 100));
        // 5000 ~ 7200
        categories.add(new Category("아우터", 6700));
        // 3000 ~ 4200
        categories.add(new Category("바지", 99999));

        final Brand newBrand = new Brand("NEW", categories);
        database.add(newBrand);
        final SortedValueDatabase sortedValueDatabase = SortedValueDatabase.getInstance();
        sortedValueDatabase.add(newBrand);

        // 상의 카테고리의 가장 저렴한 브랜드가 NEW여야함
        final BrandLowHighPricePair data1 = database.find("상의");
        assertNotNull(data1);
        assertEquals("NEW", data1.lowBrand());

        // 바지 카테고리의 가장 비싼 브랜드가 NEW여야함
        final BrandLowHighPricePair data2 = database.find("바지");
        assertNotNull(data2);
        assertEquals("NEW", data2.highBrand());

        // 아우터 카테고리에는 아무런 영향이 없어야함
        final BrandLowHighPricePair data3 = database.find("아우터");
        assertNotNull(data3);
        assertEquals("E", data3.lowBrand());
        assertEquals("F", data3.highBrand());

        // NEW 브랜드 삭제
        database.delete("NEW");

        // 상의 카테고리의 가장 저렴한 브랜드가 C여야함
        final BrandLowHighPricePair data4 = database.find("상의");
        assertNotNull(data4);
        assertEquals("C", data4.lowBrand());

        // 바지 카테고리의 가장 비싼 브랜드가 A여야함
        final BrandLowHighPricePair data5 = database.find("바지");
        assertNotNull(data5);
        assertEquals("A", data5.highBrand());

        // 아우터 카테고리에는 아무런 영향이 없어야함
        final BrandLowHighPricePair data6 = database.find("아우터");
        assertNotNull(data6);
        assertEquals("E", data6.lowBrand());
        assertEquals("F", data6.highBrand());
    }
}