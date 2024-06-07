package com.example.satjun1.admin.delete.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.satjun1.storage.mapset.SortedValueDatabase;
import com.example.satjun1.storage.keyvalue.KeyValueDatabase;
import com.example.satjun1.storage.rank.RankDatabase;

@Service
public class DeleteService {
    private static final KeyValueDatabase KEY_VALUE_DATABASE = KeyValueDatabase.getInstance();
    private static final RankDatabase RANK_DATABASE = RankDatabase.getInstance();
    private static final SortedValueDatabase SORTED_VALUE_DATABASE = SortedValueDatabase.getInstance();

    public void execute(String nameOfBrand) {
        RANK_DATABASE.delete(nameOfBrand);
        // key-value 먼저 지우고, sortedValue 디비 삭제해야함
        // 키-벨류 삭제시, sortedvalue 디비가 사용되기 때문
        KEY_VALUE_DATABASE.delete(nameOfBrand);
        final List<String> categories = Arrays.asList("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리");
        SORTED_VALUE_DATABASE.delete(nameOfBrand, categories);
    }
}
