package com.example.satjun1.storage.keyvalue;

/**
 * 유저에게 보여줄 {저렴한 브랜드의 이름, 저렴한 브랜드의 가격, 고가 브랜드의 이름, 고가 브랜드의 가격} 정보.
 *
 * @param lowBrand 가장 저렴한 브랜드의 이름
 * @param lowPrice 가장 저렴한 브랜드의 가격
 * @param highBrand 가장 고가 브랜드의 이름
 * @param highPrice 가장 고가 브랜드의 가격
 * @param category 상품의 카테고리
 */
public record BrandLowHighPricePair(String lowBrand, int lowPrice, String highBrand, int highPrice, String category) {
}
