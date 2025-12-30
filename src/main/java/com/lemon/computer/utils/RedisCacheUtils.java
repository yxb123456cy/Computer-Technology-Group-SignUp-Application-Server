package com.lemon.computer.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 * 基于 StringRedisTemplate 实现，主要用于操作字符串类型的 Key-Value
 */
@Component
@RequiredArgsConstructor
public class RedisCacheUtils {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间单位
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value), timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public String getCacheObject(final String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 键
     */
    public boolean deleteObject(final String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return 删除的数量
     */
    public long deleteObject(final Collection<String> collection) {
        Long count = stringRedisTemplate.delete(collection);
        return count == null ? 0 : count;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<String> dataList) {
        Long count = stringRedisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<String> getCacheList(final String key) {
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> long setCacheSet(final String key, final Set<String> dataSet) {
        Long count = stringRedisTemplate.opsForSet().add(key, dataSet.toArray(new String[0]));
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的set
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public Set<String> getCacheSet(final String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key     缓存的键值
     * @param dataMap 缓存的数据
     */
    public <T> void setCacheMap(final String key, final Map<String, String> dataMap) {
        if (dataMap != null) {
            stringRedisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public Map<Object, Object> getCacheMap(final String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final String value) {
        stringRedisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public String getCacheMapValue(final String key, final String hKey) {
        Object result = stringRedisTemplate.opsForHash().get(key, hKey);
        return result == null ? null : String.valueOf(result);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public List<Object> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return stringRedisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return stringRedisTemplate.keys(pattern);
    }
}
