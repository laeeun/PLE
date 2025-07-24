package com.springmvc.config;

public class AddressUtil {
	/**
     * 전체 주소에서 시/도 + 구/군 + 동까지만 잘라서 반환
     * 예: "서울특별시 강남구 역삼동 123-45" → "서울특별시 강남구 역삼동"
     */
    public static String extractBaseAddress(String fullAddress) {
        if (fullAddress == null) return "";

        String[] parts = fullAddress.trim().split("\\s+");
        if (parts.length >= 3) {
            return parts[0] + " " + parts[1] + " " + parts[2];
        }
        return fullAddress; // 최소한 자르지 못하면 원본 리턴
    }
}
