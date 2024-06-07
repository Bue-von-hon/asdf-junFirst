package com.example.satjun1;

import java.util.List;

public record Brand(String brand, List<Category> categories) {

    public record Category(String category, int price) {
    }
}
