package com.example.satjun1.customer.first.controller.v1;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.satjun1.storage.keyvalue.BrandLowHighPricePair;

public final class Response {
    private final int status;
    private final String message;
    private final CategoryLowPriceData data;

    private Response(int status, String message, CategoryLowPriceData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private static String formatNumberWithCommas(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,###,###");
        return formatter.format(number);
    }

    public static Response from(List<BrandLowHighPricePair> pairs) {
        List<Category> categories = new ArrayList<>();
        int totalPrice = 0;
        for (BrandLowHighPricePair pair : pairs) {
            Category category = new Category(pair.category(), pair.lowBrand(),
                                             formatNumberWithCommas(pair.lowPrice()));
            categories.add(category);
            totalPrice += pair.lowPrice();
        }
        CategoryLowPriceData data = new CategoryLowPriceData(categories, formatNumberWithCommas(totalPrice),
                                                                             ZonedDateTime.now(), "1.0.0",
                                                                             "1.0.0", pairs.size());
        return new Response(200, "OK", data);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CategoryLowPriceData getData() {
        return data;
    }

    private record CategoryLowPriceData(List<Category> categories, String total_lowest_price_sum, ZonedDateTime timestamp, String version, String serverVersion,
                                        int dataCount) {
    }

    private record Category(String category, String lowest_price_brand, String lowest_price) {
    }
}
