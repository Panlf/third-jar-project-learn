package com.plf.learn.hutool;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ReflectUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * @author panlf
 * @date 2022/12/19
 */
public class HutoolTutorial {

    @Test
    public void testReflect(){
       Class<?> aClass= InfoConstants.class;
        Field[] fields = aClass.getFields();
        Arrays.stream(fields).forEach(field -> {
            System.out.println(ReflectUtil.getFieldName(field)+":"+ReflectUtil.getStaticFieldValue(field));
        });

    }

    @Test
    public void test(){
        System.out.println(DateUtil.format(new Date(),"yyyy-MM-dd"));
        System.out.println(LocalDateTimeUtil.format(LocalDateTime.now(),"yyyy-MM-dd"));
    }
}
