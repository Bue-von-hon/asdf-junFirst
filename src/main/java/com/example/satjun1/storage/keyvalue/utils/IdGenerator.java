package com.example.satjun1.storage.keyvalue.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.lang.Nullable;

public final class IdGenerator {

    /**
     * 주어진 문자열을 해싱해서 키를 리턴함.
     * 동일한 문자열에 대해서는 동일한 키를 리턴하고, 다른 문자열은 다른 키를 리턴함을 보장함.
     * 만약 빈 문자열이 주어지면, 익셉션을 던짐.
     *
     * @param inputString
     * @return
     */
    public static int generateIntegerKeyByString(@Nullable String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            throw new RuntimeException("키 생성 오류: 빈 문자열이 주어짐.");
        }
        try {
            // todo: 느린 해싱을 사용할 필요가 없음
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hashBytes = digest.digest(inputString.getBytes(StandardCharsets.UTF_8));
            return Math.abs(ByteBuffer.wrap(hashBytes).getInt());
        } catch (Exception e) {
            throw new RuntimeException("키 생성 오류: 빈 문자열이 주어짐.");
        }
    }

    private IdGenerator() {}
}
