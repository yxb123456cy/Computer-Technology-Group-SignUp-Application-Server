package com.lemon.computer.utils;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * 自定义 UUID 生成工具类
 */
public class CustomUUIDUtils {
    /** 安全随机数生成器，线程安全 */
    private static final SecureRandom RANDOM = new SecureRandom();

    /** 禁止实例化 */
    private CustomUUIDUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * 生成标准 UUID（包含横线）
     *
     * @return 形如：550e8400-e29b-41d4-a716-446655440000
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成无横线 UUID（32 位）
     *
     * @return 形如：550e8400e29b41d4a716446655440000
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 生成基于时间戳的短 UUID（22~26 位）
     */
    public static String shortUUID() {
        long timestamp = System.currentTimeMillis();
        long randomLong = RANDOM.nextLong();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(timestamp);
        buffer.putLong(randomLong);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
    }


    /**
     * 生成 Base64 编码的 UUID（长度 22）
     *
     * @return 形如：N94EJtnRQxO6Oa2Z1o42Zw
     */
    public static String base64UUID() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
    }


}
