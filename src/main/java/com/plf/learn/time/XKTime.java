package com.plf.learn.time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Date;

import com.xkzhangsan.time.LunarDate;
import com.xkzhangsan.time.calculator.DateTimeCalculatorUtil;
import com.xkzhangsan.time.converter.DateTimeConverterUtil;
import com.xkzhangsan.time.cron.CronExpressionUtil;
import com.xkzhangsan.time.formatter.DateTimeFormatterUtil;
import org.junit.jupiter.api.Test;

/**
 * 时间工具类
 * @author panlf
 *
 */
public class XKTime {

	/**
	 * 时间转换
	 */
	@Test
	public void converterTime() {
		Date date = DateTimeConverterUtil.toDate(LocalDateTime.now());
		System.out.println(date);
	}
	
	/**
	 * 时间计算
	 */
	@Test
	public void calculatorTime() {
		//两天之后
		Date date = DateTimeCalculatorUtil.plusDays(new Date(), 2);
		System.out.println(date);
		
		// 下一个周六
		System.out.println(DateTimeCalculatorUtil.next(new Date(),DayOfWeek.SATURDAY));
		
		//获取星座
		String xingZuo = DateTimeCalculatorUtil.getConstellationNameCn("09-27");
		System.out.println(xingZuo);
	}
	
	/**
	 * 时间解析
	 */
	@Test
	public void formatterTime() {
		System.out.println(DateTimeFormatterUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 日历解析
	 */
	@Test
	public void lunarDate() {
		long[] lunar = LunarDate.calElement(2020, 9, 2);
		for(long l:lunar) {
			System.out.println(l);
		}
		System.out.println(LunarDate.cyclical(2020));
	}
	
	/**
	 * Cron表达式工具类
	 */
	@Test
	public void cronExpression() {
		 System.out.println(CronExpressionUtil.getNextTime("0 0 2 1 * ? * "));
	}
}
