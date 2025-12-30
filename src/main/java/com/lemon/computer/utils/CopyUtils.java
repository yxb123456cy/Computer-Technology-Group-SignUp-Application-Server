
package com.lemon.computer.utils;





import com.lemon.computer.common.exceptions.BizException;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通用对象拷贝工具类
 * 支持浅拷贝、深拷贝、对象属性拷贝、集合拷贝等多种场景
 *
 * @author Generated
 * @version 1.0
 */
public final class CopyUtils {

    private CopyUtils() {
        // 工具类，禁止实例化
    }

    // ==================== 浅拷贝方法 ====================

    /**
     * 浅拷贝对象
     * 使用Cloneable接口实现，要求目标对象实现Cloneable接口
     *
     * @param <T> 对象类型
     * @param source 源对象
     * @return 拷贝后的对象
     * @throws RuntimeException 拷贝失败时抛出
     */
    @SuppressWarnings("unchecked")
    public static <T extends Cloneable> T shallowCopy(T source) {
        if (source == null) {
            return null;
        }

        try {
            Method cloneMethod = source.getClass().getMethod("clone");
            cloneMethod.setAccessible(true);
            return (T) cloneMethod.invoke(source);
        } catch (Exception e) {
            throw new RuntimeException("浅拷贝失败: " + e.getMessage(), e);
        }
    }

    // ==================== 深拷贝方法 ====================

    /**
     * 深拷贝对象（使用序列化方式）
     * 要求对象及其所有引用对象都实现Serializable接口
     *
     * @param <T> 对象类型
     * @param source 源对象
     * @return 拷贝后的对象
     * @throws RuntimeException 拷贝失败时抛出
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepCopy(T source) {
        if (source == null) {
            return null;
        }

        try {
            // 序列化到字节数组
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(source);
            oos.close();

            // 从字节数组反序列化
            ByteArrayInputStream BAS = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(BAS);
            T copy = (T) ois.readObject();
            ois.close();

            return copy;
        } catch (Exception e) {
            throw new RuntimeException("深拷贝失败: " + e.getMessage(), e);
        }
    }

    // ==================== 对象属性拷贝方法 ====================

    /**
     * 拷贝对象属性（同名同类型属性）
     * 使用反射机制实现属性拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     * @throws RuntimeException 拷贝失败时抛出
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        // 获取源对象的所有字段
        Field[] sourceFields = getAllFields(sourceClass);

        for (Field sourceField : sourceFields) {
            try {
                // 跳过静态字段和final字段
                if (Modifier.isStatic(sourceField.getModifiers()) ||
                        Modifier.isFinal(sourceField.getModifiers())) {
                    continue;
                }

                // 设置可访问
                sourceField.setAccessible(true);

                // 获取源字段值
                Object value = sourceField.get(source);
                if (value == null) {
                    continue;
                }

                // 查找目标对象中同名字段
                Field targetField = findField(targetClass, sourceField.getName());
                if (targetField == null) {
                    continue;
                }

                // 检查类型是否兼容
                if (isTypeCompatible(sourceField.getType(), targetField.getType())) {
                    continue;
                }

                // 设置目标字段值
                targetField.setAccessible(true);
                targetField.set(target, value);

            } catch (IllegalAccessException e) {
                // 忽略访问异常，继续处理下一个字段
                throw new BizException("500",e.getMessage());
            }
        }
    }

    /**
     * 拷贝对象属性（支持类型转换）
     *
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreNull 是否忽略null值
     */
    public static void copyPropertiesWithConvert(Object source, Object target, boolean ignoreNull) {
        if (source == null || target == null) {
            return;
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = getAllFields(sourceClass);

        for (Field sourceField : sourceFields) {
            try {
                if (Modifier.isStatic(sourceField.getModifiers()) ||
                        Modifier.isFinal(sourceField.getModifiers())) {
                    continue;
                }

                sourceField.setAccessible(true);
                Object value = sourceField.get(source);

                if (ignoreNull && value == null) {
                    continue;
                }

                Field targetField = findField(targetClass, sourceField.getName());
                if (targetField == null) {
                    continue;
                }

                targetField.setAccessible(true);

                // 类型转换
                Object convertedValue = convertType(value, targetField.getType());
                if (convertedValue != null) {
                    targetField.set(target, convertedValue);
                }

            } catch (Exception e) {
                throw new BizException("500",e.getMessage());
            }
        }
    }

    // ==================== 集合拷贝方法 ====================

    /**
     * 拷贝List集合（浅拷贝）
     *
     * @param <T> 元素类型
     * @param sourceList 源列表
     * @return 拷贝后的列表
     */
    public static <T> List<T> copyList(List<T> sourceList) {
        if (sourceList == null) {
            return null;
        }

        List<T> targetList = new ArrayList<>(sourceList.size());
        targetList.addAll(sourceList);
        return targetList;
    }

    /**
     * 拷贝List集合（深拷贝）
     *
     * @param <T> 元素类型（必须实现Serializable）
     * @param sourceList 源列表
     * @return 拷贝后的列表
     */
    public static <T extends Serializable> List<T> deepCopyList(List<T> sourceList) {
        if (sourceList == null) {
            return null;
        }

        List<T> targetList = new ArrayList<>(sourceList.size());
        for (T item : sourceList) {
            targetList.add(deepCopy(item));
        }
        return targetList;
    }

    /**
     * 拷贝Set集合（浅拷贝）
     *
     * @param <T> 元素类型
     * @param sourceSet 源集合
     * @return 拷贝后的集合
     */
    public static <T> Set<T> copySet(Set<T> sourceSet) {
        if (sourceSet == null) {
            return null;
        }

        Set<T> targetSet = new HashSet<>(sourceSet.size());
        targetSet.addAll(sourceSet);
        return targetSet;
    }

    /**
     * 拷贝Map集合（浅拷贝）
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param sourceMap 源Map
     * @return 拷贝后的Map
     */
    public static <K, V> Map<K, V> copyMap(Map<K, V> sourceMap) {
        if (sourceMap == null) {
            return null;
        }

        Map<K, V> targetMap = new HashMap<>(sourceMap.size());
        targetMap.putAll(sourceMap);
        return targetMap;
    }

    // ==================== 数组拷贝方法 ====================

    /**
     * 拷贝数组
     *
     * @param <T> 数组元素类型
     * @param sourceArray 源数组
     * @return 拷贝后的数组
     */
    public static <T> T[] copyArray(T[] sourceArray) {
        if (sourceArray == null) {
            return null;
        }

        return Arrays.copyOf(sourceArray, sourceArray.length);
    }

    // ==================== 辅助方法 ====================

    /**
     * 获取类的所有字段（包括父类）
     */
    private static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();

        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            clazz = clazz.getSuperclass();
        }

        return fieldList.toArray(new Field[0]);
    }

    /**
     * 查找类中的指定字段
     */
    private static Field findField(Class<?> clazz, String fieldName) {
        Class<?> searchClass = clazz;

        while (searchClass != null && searchClass != Object.class) {
            try {
                return searchClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                searchClass = searchClass.getSuperclass();
            }
        }

        return null;
    }

    /**
     * 检查类型是否兼容
     */
    private static boolean isTypeCompatible(Class<?> sourceType, Class<?> targetType) {
        if (sourceType.equals(targetType)) {
            return false;
        }

        // 处理基本类型和包装类型的兼容性
        if (isPrimitiveWrapperMatch(sourceType, targetType)) {
            return false;
        }

        return !targetType.isAssignableFrom(sourceType);
    }

    /**
     * 检查基本类型和包装类型是否匹配
     */
    private static boolean isPrimitiveWrapperMatch(Class<?> type1, Class<?> type2) {
        Map<Class<?>, Class<?>> primitiveToWrapper = new HashMap<>();
        primitiveToWrapper.put(boolean.class, Boolean.class);
        primitiveToWrapper.put(byte.class, Byte.class);
        primitiveToWrapper.put(char.class, Character.class);
        primitiveToWrapper.put(short.class, Short.class);
        primitiveToWrapper.put(int.class, Integer.class);
        primitiveToWrapper.put(long.class, Long.class);
        primitiveToWrapper.put(float.class, Float.class);
        primitiveToWrapper.put(double.class, Double.class);

        return primitiveToWrapper.get(type1) == type2 ||
                primitiveToWrapper.get(type2) == type1;
    }

    /**
     * 类型转换
     */
    private static Object convertType(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }

        // 字符串转换
        if (targetType == String.class) {
            return value.toString();
        }

        // 数字类型转换
        if (value instanceof Number number) {

            if (targetType == Integer.class || targetType == int.class) {
                return number.intValue();
            } else if (targetType == Long.class || targetType == long.class) {
                return number.longValue();
            } else if (targetType == Double.class || targetType == double.class) {
                return number.doubleValue();
            } else if (targetType == Float.class || targetType == float.class) {
                return number.floatValue();
            } else if (targetType == Short.class || targetType == short.class) {
                return number.shortValue();
            } else if (targetType == Byte.class || targetType == byte.class) {
                return number.byteValue();
            }
        }

        // 字符串转数字
        if (value instanceof String str) {

            try {
                if (targetType == Integer.class || targetType == int.class) {
                    return Integer.parseInt(str);
                } else if (targetType == Long.class || targetType == long.class) {
                    return Long.parseLong(str);
                } else if (targetType == Double.class || targetType == double.class) {
                    return Double.parseDouble(str);
                } else if (targetType == Float.class || targetType == float.class) {
                    return Float.parseFloat(str);
                } else if (targetType == Boolean.class || targetType == boolean.class) {
                    return Boolean.parseBoolean(str);
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }

    // ==================== 性能优化的拷贝方法 ====================

    /**
     * 缓存字段信息，提高反射性能
     */
    private static final Map<Class<?>, Field[]> FIELD_CACHE = new ConcurrentHashMap<>();

    /**
     * 高性能对象属性拷贝（使用缓存）
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesFast(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        // 从缓存获取字段信息
        Field[] sourceFields = FIELD_CACHE.computeIfAbsent(sourceClass, CopyUtils::getAllFields);

        for (Field sourceField : sourceFields) {
            try {
                if (Modifier.isStatic(sourceField.getModifiers()) ||
                        Modifier.isFinal(sourceField.getModifiers())) {
                    continue;
                }

                sourceField.setAccessible(true);
                Object value = sourceField.get(source);
                if (value == null) {
                    continue;
                }

                Field targetField = findField(targetClass, sourceField.getName());
                if (targetField == null) {
                    continue;
                }

                if (isTypeCompatible(sourceField.getType(), targetField.getType())) {
                    continue;
                }
                targetField.setAccessible(true);
                targetField.set(target, value);
            } catch (IllegalAccessException e) {
                throw new BizException("500",e.getMessage());
            }
        }
    }
}
