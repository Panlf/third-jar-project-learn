package com.plf.learn.segmentation;

import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.jupiter.api.Test;

/**
 * Ansj的文本分词
 * @author Panlf
 *
 */
public class AnsjExample {
	@Test
	public void TestSegmenterWord(){
		String str = "今日:2020/06/05;身份号码:33068119930914626X;银行账号：620071008312398037654;浙江无敌强科技有限公司;电子邮箱:1876781654@qq.com;电话号码:13987584105";
		System.out.println(ToAnalysis.parse(str));
	}
}
