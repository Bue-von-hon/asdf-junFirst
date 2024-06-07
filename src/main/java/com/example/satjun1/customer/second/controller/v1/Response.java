package com.example.satjun1.customer.second.controller.v1;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.satjun1.storage.rank.CategoryPricePair;
import com.example.satjun1.storage.rank.LowestPriceListByCategory;

public final class Response {
    private final int status;
    private final String message;
    private final BrandLowPriceData data;

    private Response(int status, String message, BrandLowPriceData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private static String formatNumberWithCommas(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,###,###");
        return formatter.format(number);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public BrandLowPriceData getData() {
        return data;
    }

    public static Response from(LowestPriceListByCategory data) {
        List<Category> categories = new ArrayList<>();
        List<CategoryPricePair> lowestPriceList = data.getLowestPriceList();
        int total = 0;
        for (CategoryPricePair pair: lowestPriceList) {
            int priceOfPair = pair.price();
            total += priceOfPair;
            String price = formatNumberWithCommas(priceOfPair);
            Category category = new Category(pair.categoryName(), price);
            categories.add(category);
        }
        BrandLowPriceData brandLowPriceData = new BrandLowPriceData(data.getBrand(), categories, formatNumberWithCommas(total),
                                                                    ZonedDateTime.now(), "1.0.0", "1.0.0", 8);
        return new Response(200, "OK", brandLowPriceData);
    }

    public static Response error() {
        return new Response(500, "FAIL", null);
    }

    private record BrandLowPriceData(String brand, List<Category> categories, String total_price_sum,
                                     ZonedDateTime timestamp, String version, String serverVersion,
                                     int dataCount) {
    }

    private record Category(String category, String price) {
    }
}
