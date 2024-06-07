package com.example.satjun1.admin.add.service;

import org.springframework.stereotype.Service;

import com.example.satjun1.Brand;
import com.example.satjun1.storage.mapset.SortedValueDatabase;
import com.example.satjun1.storage.keyvalue.KeyValueDatabase;
import com.example.satjun1.storage.rank.RankDatabase;

@Service
public class AddService {
    private static final KeyValueDatabase KEY_VALUE_DATABASE = KeyValueDatabase.getInstance();
    private static final RankDatabase RANK_DATABASE = RankDatabase.getInstance();
    private static final SortedValueDatabase SORTED_VALUE_DATABASE = SortedValueDatabase.getInstance();

    public void execute(Brand newBrand) {
        KEY_VALUE_DATABASE.add(newBrand);
        RANK_DATABASE.add(newBrand);
        SORTED_VALUE_DATABASE.add(newBrand);
    }
}
