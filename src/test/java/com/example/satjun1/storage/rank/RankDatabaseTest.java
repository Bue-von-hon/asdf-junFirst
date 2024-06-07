package com.example.satjun1.storage.rank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RankDatabaseTest {

    @Test
    void test1() {
        RankDatabase database = RankDatabase.getInstance();
        LowestPriceListByCategory data1 = database.topOne();
        assertNotNull(data1);
        assertEquals("D", data1.getBrand());
        assertEquals(36100, data1.getTotal());

        // 가장 작은 total 수정 36100(D) -> 8(DD)
        LowestPriceListByCategory data4 = new LowestPriceListByCategory(null, 8, "DD");
        database.SET.add(data4);
        LowestPriceListByCategory rank3 = database.topOne();

        // 변경된 가장 작은 total을 반영하는지 확인
        assertEquals(8, rank3.getTotal());
        assertEquals("DD", rank3.getBrand());

        // 신규 추가된 데이터 삭제, 가장 저렴한 브랜드로 D가 나와야함
        database.delete("DD");
        LowestPriceListByCategory data2 = database.topOne();
        assertNotNull(data2);
        assertEquals("D", data2.getBrand());
        assertEquals(36100, data2.getTotal());
    }
}