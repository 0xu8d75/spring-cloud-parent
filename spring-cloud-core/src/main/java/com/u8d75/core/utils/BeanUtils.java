package com.u8d75.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * BeanUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtils {

    /**
     * set property value via reflection
     *
     * @param object        object
     * @param propertyName  propertyName
     * @param propertyValue propertyValue
     */
    public static void setPropertyValue(Object object, String propertyName, Object propertyValue) {
        if (null == object || null == propertyName || null == propertyValue) {
            return;
        }
        Field field = ReflectionUtils.findField(object.getClass(), propertyName);
        if (null != field) {
            ReflectionUtils.makeAccessible(field);
            Object value = ReflectionUtils.getField(field, object);
            if (null == value) {
                ReflectionUtils.setField(field, object, propertyValue);
            }
        }
    }

    /**
     * get property value via reflection
     * @param object object
     * @param propertyName propertyName
     * @return property value
     */
    public static Object getPropertyValue(Object object, String propertyName) {
        if (null == object || null == propertyName) {
            return null;
        }
        Field field = ReflectionUtils.findField(object.getClass(), propertyName);
        if (null != field) {
            ReflectionUtils.makeAccessible(field);
            return ReflectionUtils.getField(field, object);
        }
        return null;
    }

}
