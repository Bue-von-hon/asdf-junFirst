package com.example.satjun1.customer.third.service;

import org.springframework.stereotype.Service;

import com.example.satjun1.storage.keyvalue.BrandLowHighPricePair;
import com.example.satjun1.storage.keyvalue.KeyValueDatabase;

@Service
public class CategoryMinMaxService {

    private static final KeyValueDatabase DATABASE = KeyValueDatabase.getInstance();

    public BrandLowHighPricePair execute(String name) {
        return DATABASE.find(name);
    }
}
