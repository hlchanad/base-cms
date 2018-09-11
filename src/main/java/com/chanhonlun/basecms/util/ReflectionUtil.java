package com.chanhonlun.basecms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static List<Field> getPojoFields(Class<?> pojoClass) {
        return Arrays.asList(pojoClass.getDeclaredFields());
    }
}