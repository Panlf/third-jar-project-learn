package com.plf.learn.hutool;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author panlf
 * @date 2022/12/19
 */
public class HutoolTutorial {

    @Test
    public void test(){
        System.out.println(DateUtil.format(new Date(),"yyyy-MM-dd"));
        System.out.println(LocalDateTimeUtil.format(LocalDateTime.now(),"yyyy-MM-dd"));
    }
}
