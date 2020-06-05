package com.plf.learn.segmentation;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.IWord;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;

public class JecegExample {
	@Test
	public void TestSegmenterWord() throws IOException {
		// 创建SegmenterConfig分词配置实例，自动查找加载jcseg.properties配置项来初始化
		SegmenterConfig config = new SegmenterConfig(true);

		// 创建默认单例词库实现，并且按照config配置加载词库
		ADictionary dic = DictionaryFactory.createSingletonDictionary(config);
		
		// 依据给定的ADictionary和SegmenterConfig来创建ISegment
		// 为了Api往后兼容，建议使用SegmentFactory来创建ISegment对象
		// 使用NLP模式
		ISegment seg = ISegment.NLP.factory.create(config, dic);

		// 备注：以下代码可以反复调用，seg为非线程安全

		// 设置要被分词的文本
		String str = "今日:2020-06-05;身份号码:33068119930914626X;银行账号：620071008312398037654;电子邮箱:1876781654@qq.com;电话号码:13987584105";
		seg.reset(new StringReader(str));

		// 获取分词结果
		IWord word = null;
		while ((word = seg.next()) != null) {
			System.out.println(word.getValue());
		}
	}
}
