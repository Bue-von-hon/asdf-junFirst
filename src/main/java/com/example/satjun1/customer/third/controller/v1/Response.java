package com.example.satjun1.customer.third.controller.v1;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;

import com.example.satjun1.storage.keyvalue.BrandLowHighPricePair;

public final class Response {
    private final int status;
    private final String message;
    private final BrandLowHighPricePairData data;

    private Response(int status, String message, BrandLowHighPricePair rawData, String categoryName) {
        this.status = status;
        this.message = message;

        String highPriceString = formatNumberWithCommas(rawData.highPrice());
        BrandPrice high = new BrandPrice(rawData.highBrand(), highPriceString);

        String lowPriceString = formatNumberWithCommas(rawData.lowPrice());
        BrandPrice low = new BrandPrice(rawData.lowBrand(), lowPriceString);

        BrandLowHighPricePairData data = new BrandLowHighPricePairData(categoryName, low, high, ZonedDateTime.now(), "1.0.0", "1.0.0", 2);
        this.data = data;
    }

    public static Response from(BrandLowHighPricePair rawData, String categoryName) {
        return new Response(200, "OK", rawData, categoryName);
    }
    public static Response error() {
        return new Response(500, "FAIL", null, null);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public BrandLowHighPricePairData getData() {
        return data;
    }

    private static String formatNumberWithCommas(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,###,###");
        return formatter.format(number);
    }

    private record BrandLowHighPricePairData(String category, BrandPrice lowestPrice, BrandPrice highestPrice,
                                             ZonedDateTime timestamp, String version, String serverVersion,
                                             int dataCount) {
    }

    private record BrandPrice(String brand, String price) {
    }
}
