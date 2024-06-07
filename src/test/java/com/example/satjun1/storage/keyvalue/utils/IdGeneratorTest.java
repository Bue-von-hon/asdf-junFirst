package com.example.satjun1.storage.keyvalue.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IdGeneratorTest {

    @Test
    void generateIntegerKeyByString() {
        String name1 = "abc";
        int key1 = IdGenerator.generateIntegerKeyByString(name1);
        String name2 = "abc";
        int key2 = IdGenerator.generateIntegerKeyByString(name2);
        assertEquals(key1, key2);

        String name3 = "def";
        int key3 = IdGenerator.generateIntegerKeyByString(name3);
        assertNotEquals(key1, key3);
    }
}