package com.example.satjun1.storage.rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LowestPriceListByCategory implements Comparable<LowestPriceListByCategory> {
    private final List<CategoryPricePair> lowestPriceList;
    private final int total;
    private final String brand;

    public LowestPriceListByCategory(List<CategoryPricePair> lowestPriceList, int total, String brand) {
        this.lowestPriceList = lowestPriceList;
        this.total = total;
        this.brand = brand;
    }

    public List<CategoryPricePair> getLowestPriceList() {
        return Collections.unmodifiableList(new ArrayList<>(lowestPriceList));
    }

    public int getTotal() {
        return total;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public int compareTo(LowestPriceListByCategory other) {
        return Integer.compare(this.total, other.total);
    }
}
