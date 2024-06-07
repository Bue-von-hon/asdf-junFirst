package com.example.satjun1.customer.second.service;

import org.springframework.stereotype.Service;

import com.example.satjun1.storage.rank.RankDatabase;
import com.example.satjun1.storage.rank.LowestPriceListByCategory;

@Service
public class BrandMinService {
    private static final RankDatabase DATABASE = RankDatabase.getInstance();

    public LowestPriceListByCategory execute() {
        return DATABASE.topOne();
    }
}
