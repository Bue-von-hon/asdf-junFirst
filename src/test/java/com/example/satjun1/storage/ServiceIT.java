package com.example.satjun1.storage;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.satjun1.Brand;
import com.example.satjun1.Brand.Category;
import com.example.satjun1.admin.add.service.AddService;
import com.example.satjun1.admin.delete.service.DeleteService;
import com.example.satjun1.customer.first.service.AllCategoryService;
import com.example.satjun1.customer.second.service.BrandMinService;
import com.example.satjun1.customer.third.service.CategoryMinMaxService;
import com.example.satjun1.storage.keyvalue.BrandLowHighPricePair;
import com.example.satjun1.storage.rank.LowestPriceListByCategory;

import static org.junit.jupiter.api.Assertions.*;

class ServiceIT {

    private final AllCategoryService allCategoryService = new AllCategoryService();
    private final BrandMinService brandMinService = new BrandMinService();
    private final CategoryMinMaxService categoryMinMaxService = new CategoryMinMaxService();

    private final AddService addService = new AddService();
    private final DeleteService deleteService = new DeleteService();

    @Test
    void test() {
        // 최초에 적재된 정보들이 올바른지 테스트
        testInitialData();

        // 모든 카테고리가 최저가인 브랜드 NEW_LOW 생성
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("상의", 1));
        categories.add(new Category("아우터", 1));
        categories.add(new Category("바지", 1));
        categories.add(new Category("스니커즈", 1));
        categories.add(new Category("가방", 1));
        categories.add(new Category("모자", 1));
        categories.add(new Category("양말", 1));
        categories.add(new Category("액세서리", 1));
        Brand brand1 = new Brand("NEW_LOW", categories);
        addService.execute(brand1);

        // 올바르게 최저가 브랜드 NEW_LOW가 확인되는지 테스트
        testAllLowBrandOfService("NEW_LOW");

        // 모든 카테고리의 최고가 브랜드 NEW_HIGH 추가
        List<Category> categories2 = new ArrayList<>();
        categories2.add(new Category("상의", 99999));
        categories2.add(new Category("아우터", 99999));
        categories2.add(new Category("바지", 99999));
        categories2.add(new Category("스니커즈", 99999));
        categories2.add(new Category("가방", 99999));
        categories2.add(new Category("모자", 99999));
        categories2.add(new Category("양말", 99999));
        categories2.add(new Category("액세서리", 99999));
        Brand brand2 = new Brand("NEW_HIGH", categories2);
        addService.execute(brand2);

        // 올바르게 최고가 브랜드 NEW_HIGH가 확인되는지 테스트
        testAllHighBrandOfService("NEW_HIGH");

        // 신규 추가된 브랜드들이 삭제되면, 최초의 정보들이 리턴되어야함.
        deleteService.execute("NEW_LOW");
        deleteService.execute("NEW_HIGH");
        testInitialData();

        // 삽입-삭제 이후 재삽입되어도, 동작에 영향이 있으면 안됌.
        addService.execute(brand1);
        testAllLowBrandOfService("NEW_LOW");
        deleteService.execute("NEW_LOW");

        // 모든 가격이 중앙값인 브랜드 추가
        List<Category> categories3 = new ArrayList<>();
        categories3.add(new Category("상의", 11000));
        categories3.add(new Category("아우터", 6000));
        categories3.add(new Category("바지", 4000));
        categories3.add(new Category("스니커즈", 9300));
        categories3.add(new Category("가방", 2200));
        categories3.add(new Category("모자", 1700));
        categories3.add(new Category("양말", 1900));
        categories3.add(new Category("액세서리", 2000));
        Brand brand3 = new Brand("NEW_MIDDLE", categories3);
        addService.execute(brand3);
        // 모든 가격이 중앙값이라, 아무런 영향도 주면 안됌
        testInitialData();

        // 삽입-삭제 이후 재삽입되어도, 동작에 영향이 있으면 안됌.
        deleteService.execute("NEW_MIDDLE");
        addService.execute(brand3);
        testInitialData();
    }

    private void testInitialData() {
        // 8개 카테고리에 대한 최저/최고 가격 정보가 리턴되어야함.
        List<BrandLowHighPricePair> data1 = allCategoryService.execute();
        assertNotNull(data1);
        assertEquals(8, data1.size());

        // 총합 기준 최저가 브랜드 D를 가져와야함.
        LowestPriceListByCategory data2 = brandMinService.execute();
        assertNotNull(data2);
        assertEquals("D", data2.getBrand());
        assertEquals(36100, data2.getTotal());

        // 상의 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh1 = categoryMinMaxService.execute("상의");
        assertNotNull(lowhigh1);
        assertEquals("I", lowhigh1.highBrand());
        assertEquals(11400, lowhigh1.highPrice());
        assertEquals("C", lowhigh1.lowBrand());
        assertEquals(10000, lowhigh1.lowPrice());

        // 아우터 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh2 = categoryMinMaxService.execute("아우터");
        assertNotNull(lowhigh2);
        assertEquals("F", lowhigh2.highBrand());
        assertEquals(7200, lowhigh2.highPrice());
        assertEquals("E", lowhigh2.lowBrand());
        assertEquals(5000, lowhigh2.lowPrice());

        // 바지 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh3 = categoryMinMaxService.execute("바지");
        assertNotNull(lowhigh3);
        assertEquals("A", lowhigh3.highBrand());
        assertEquals(4200, lowhigh3.highPrice());
        assertEquals("D", lowhigh3.lowBrand());
        assertEquals(3000, lowhigh3.lowPrice());

        // 스니커즈 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh4 = categoryMinMaxService.execute("스니커즈");
        assertNotNull(lowhigh4);
        assertEquals("E", lowhigh4.highBrand());
        assertEquals(9900, lowhigh4.highPrice());
        assertEquals("A", lowhigh4.lowBrand());
        assertEquals(9000, lowhigh4.lowPrice());

        // 가방 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh5 = categoryMinMaxService.execute("가방");
        assertNotNull(lowhigh5);
        assertEquals("D", lowhigh5.highBrand());
        assertEquals(2500, lowhigh5.highPrice());
        assertEquals("A", lowhigh5.lowBrand());
        assertEquals(2000, lowhigh5.lowPrice());

        // 모자 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh6 = categoryMinMaxService.execute("모자");
        assertNotNull(lowhigh6);
        assertEquals("B", lowhigh6.highBrand());
        assertEquals(2000, lowhigh6.highPrice());
        assertEquals("D", lowhigh6.lowBrand());
        assertEquals(1500, lowhigh6.lowPrice());

        // 양말 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh7 = categoryMinMaxService.execute("양말");
        assertNotNull(lowhigh7);
        assertEquals("D", lowhigh7.highBrand());
        assertEquals(2400, lowhigh7.highPrice());
        assertEquals("I", lowhigh7.lowBrand());
        assertEquals(1700, lowhigh7.lowPrice());

        // 액세서리 카테고리의 최고가/최저가 브랜드를 가져와야함.
        BrandLowHighPricePair lowhigh8 = categoryMinMaxService.execute("액세서리");
        assertNotNull(lowhigh8);
        assertEquals("I", lowhigh8.highBrand());
        assertEquals(2400, lowhigh8.highPrice());
        assertEquals("F", lowhigh8.lowBrand());
        assertEquals(1900, lowhigh8.lowPrice());
    }

    private void testAllLowBrandOfService(String nameOfBrand) {
        // 카테고리 8개에 대한 정보를 올바르게 가져왔는지 체크
        List<BrandLowHighPricePair> data4 = allCategoryService.execute();
        assertNotNull(data4);
        assertEquals(8, data4.size());

        // 모든 카테고리의 최저가 브랜드 데이터가 NEW_LOW인지 확인
        for (BrandLowHighPricePair data : data4) {
            assertNotNull(data);
            assertEquals(nameOfBrand, data.lowBrand());
        }

        // 가장 최저가 브랜드가 NEW_LOW인지 체크
        LowestPriceListByCategory data5 = brandMinService.execute();
        assertNotNull(data5);
        assertEquals(nameOfBrand, data5.getBrand());

        // 카테고리별 최저가 브랜드 전부가 NEW_LOW인지 체크
        BrandLowHighPricePair pair1 = categoryMinMaxService.execute("상의");
        assertNotNull(pair1);
        assertEquals(nameOfBrand, pair1.lowBrand());

        BrandLowHighPricePair pair2 = categoryMinMaxService.execute("아우터");
        assertNotNull(pair2);
        assertEquals(nameOfBrand, pair2.lowBrand());

        BrandLowHighPricePair pair3 = categoryMinMaxService.execute("바지");
        assertNotNull(pair3);
        assertEquals(nameOfBrand, pair3.lowBrand());

        BrandLowHighPricePair pair4 = categoryMinMaxService.execute("스니커즈");
        assertNotNull(pair4);
        assertEquals(nameOfBrand, pair4.lowBrand());

        BrandLowHighPricePair pair5 = categoryMinMaxService.execute("가방");
        assertNotNull(pair5);
        assertEquals(nameOfBrand, pair5.lowBrand());

        BrandLowHighPricePair pair6 = categoryMinMaxService.execute("모자");
        assertNotNull(pair6);
        assertEquals(nameOfBrand, pair6.lowBrand());

        BrandLowHighPricePair pair7 = categoryMinMaxService.execute("양말");
        assertNotNull(pair7);
        assertEquals(nameOfBrand, pair7.lowBrand());

        BrandLowHighPricePair pair8 = categoryMinMaxService.execute("액세서리");
        assertNotNull(pair8);
        assertEquals(nameOfBrand, pair8.lowBrand());
    }

    private void testAllHighBrandOfService(String nameOfBrand) {
        // 카테고리 8개에 대한 정보를 올바르게 가져왔는지 체크
        List<BrandLowHighPricePair> data4 = allCategoryService.execute();
        assertNotNull(data4);
        assertEquals(8, data4.size());

        // 모든 카테고리의 최저가 브랜드 데이터가 NEW_LOW인지 확인
        for (BrandLowHighPricePair data : data4) {
            assertNotNull(data);
            assertEquals(nameOfBrand, data.highBrand());
        }

        // 카테고리별 최저가 브랜드 전부가 NEW_LOW인지 체크
        BrandLowHighPricePair pair1 = categoryMinMaxService.execute("상의");
        assertNotNull(pair1);
        assertEquals(nameOfBrand, pair1.highBrand());

        BrandLowHighPricePair pair2 = categoryMinMaxService.execute("아우터");
        assertNotNull(pair2);
        assertEquals(nameOfBrand, pair2.highBrand());

        BrandLowHighPricePair pair3 = categoryMinMaxService.execute("바지");
        assertNotNull(pair3);
        assertEquals(nameOfBrand, pair3.highBrand());

        BrandLowHighPricePair pair4 = categoryMinMaxService.execute("스니커즈");
        assertNotNull(pair4);
        assertEquals(nameOfBrand, pair4.highBrand());

        BrandLowHighPricePair pair5 = categoryMinMaxService.execute("가방");
        assertNotNull(pair5);
        assertEquals(nameOfBrand, pair5.highBrand());

        BrandLowHighPricePair pair6 = categoryMinMaxService.execute("모자");
        assertNotNull(pair6);
        assertEquals(nameOfBrand, pair6.highBrand());

        BrandLowHighPricePair pair7 = categoryMinMaxService.execute("양말");
        assertNotNull(pair7);
        assertEquals(nameOfBrand, pair7.highBrand());

        BrandLowHighPricePair pair8 = categoryMinMaxService.execute("액세서리");
        assertNotNull(pair8);
        assertEquals(nameOfBrand, pair8.highBrand());
    }
}
