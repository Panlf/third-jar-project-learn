package com.plf.learn.grok;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.Map;

public class GrokTutorial {
	public static void main(String[] args) {
		GrokCompiler grokCompiler = GrokCompiler.newInstance();
		// 进行注册, registerDefaultPatterns()方法注册的是Grok内置的patterns
		grokCompiler.registerDefaultPatterns();
		/*
		 * 传入自定义的pattern, 会从已注册的patterns里面进行配对, 例如: TIMESTAMP_ISO8601:timestamp1,
		 * TIMESTAMP_ISO8601在注册的 patterns里面有对应的解析格式, 配对成功后,
		 * 会在match时按照固定的解析格式将解析结果存入map中, 此处timestamp1作为输出的key
		 */
		grokCompiler.register("THREAD", "\\[.*\\]");
		grokCompiler.register("MESSAGE", "[\\s\\S]*");
		Grok grok = grokCompiler
				.compile("%{TIMESTAMP_ISO8601:access_time} %{THREAD:thread} %{LOGLEVEL:loglevel} %{MESSAGE:message}");
		String logMsg = "2021-03-18 14:27:39.272 [main] WARN Engine - prioriy set to 0, because NumberFormatException, the value is: null\n"
				+ "dasnfjhsdj;fldfhasudoijlfdfbdashFJDGHFasdas --  <>aasfsdf <<><>. sdas<>《》》、、\n"
				+ "asjdfsdkfla;APHJAKFS;LAGSL;NM??QWEQ？？ada";
		// 通过match()方法进行匹配, 对log进行解析, 按照指定的格式进行输出
		Match grokMatch = grok.match(logMsg);
		// 获取结果
		Map<String, Object> resultMap = grokMatch.capture();
		System.out.println(resultMap);
	}
}
