package com.example.satjun1.customer.first.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.satjun1.storage.keyvalue.BrandLowHighPricePair;
import com.example.satjun1.storage.keyvalue.KeyValueDatabase;

@Service
public class AllCategoryService {
    private static final KeyValueDatabase DATABASE = KeyValueDatabase.getInstance();
    public List<BrandLowHighPricePair> execute() {
        return DATABASE.findAll();
    }
}
