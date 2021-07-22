package com.plf.learn.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;

public class ReadAndWriteWord {

	/**
	 * 读取后缀为doc的文档
	 * 
	 * @throws Exception
	 */
	@Test
	public void readDOC() throws Exception {
		InputStream inputStream = new FileInputStream(new File("E://temp//测试.doc"));

		WordExtractor wordExtractor = new WordExtractor(inputStream);

		String text = wordExtractor.getText();

		System.out.println(text);

		wordExtractor.close();
	}

	/**
	 * 读取后缀为docx的文档
	 * 
	 * @throws Exception
	 */
	@Test
	public void readDOCX() throws Exception {
		InputStream inputStream = new FileInputStream(new File("E://temp//测试.docx"));
		XWPFDocument doc = new XWPFDocument(inputStream);
		XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
		String text = extractor.getText();
		System.out.println(text);
		extractor.close();
	}

	/**
	 * 写DOCX的文件
	 * @throws Exception
	 */
	@Test
	public void writeDOCX() throws Exception {
		// 新建一个文档
		XWPFDocument doc = new XWPFDocument();
		// 创建一个段落
		XWPFParagraph para = doc.createParagraph();

		// 一个XWPFRun代表具有相同属性的一个区域。
		XWPFRun run = para.createRun();
		run.setBold(true); // 加粗
		run.setText("加粗的内容");
		run = para.createRun();
		run.setColor("FF0000");
		run.setText("红色的字。");
		OutputStream os = new FileOutputStream("E:\\temp\\test.docx");
		// 把doc输出到输出流
		doc.write(os);

		doc.close();
	}

	/**
	 * 写DOC需要一个模板
	 * @throws Exception
	 */
	@Test
	public void testWrite() throws Exception {

		String templatePath = "E:\\temp\\test.doc";
		InputStream is = new FileInputStream(templatePath);
		HWPFDocument doc = new HWPFDocument(is);

		Range range = doc.getRange();

		// 把range范围内的
		range.replaceText("${title}", "测试");
		range.replaceText("${content}", "测试测试测试测试测试测试测试测试测试测试测试测试测试测试");

		
		OutputStream os = new FileOutputStream("E:\\temp\\write.doc");

		// 把doc输出到输出流中
		doc.write(os);

		os.close();
		is.close();
		doc.close();

	}
	
	@Test
	public void test(){
		writeDocument("E:\\temp\\翻译.doc","翻译翻译翻译翻译翻译翻译翻译翻译翻译翻译翻译翻译翻译翻译");
	}
	
	public boolean writeDocument(String path, String content) {
		boolean flag = false;
		XWPFDocument doc = null;
		FileOutputStream fileOutputStream = null;
		try {
			// 新建一个文档
			doc = new XWPFDocument();
			// 创建一个段落
			XWPFParagraph paraTitle = doc.createParagraph();

			paraTitle.setAlignment(ParagraphAlignment.CENTER);
			// 一个XWPFRun代表具有相同属性的一个区域。
			XWPFRun run = paraTitle.createRun();
			run.setBold(true); // 加粗
			run.setFontFamily("宋体");//字体
			run.setFontSize(28);
			String allTitle = path.substring(path.lastIndexOf("\\")+1);
			String title=allTitle.substring(0, allTitle.lastIndexOf("."));
			run.setText(title);
			
			XWPFParagraph paraContent = doc.createParagraph();
			
			//设置首行缩进
			//1厘米=567
			paraContent.setAlignment(ParagraphAlignment.BOTH);
			paraContent.setIndentationFirstLine(567);	
			
			run = paraContent.createRun();
			run.setText(content);
			fileOutputStream = new FileOutputStream(path);
			// 把doc输出到输出流
			doc.write(fileOutputStream);
			
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			if(doc!=null){
				try {
					doc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	
	@Test
	public void subTitle(){
		String path = "E:\\temp\\翻译.doc";
		String allTitle = path.substring(path.lastIndexOf("\\")+1);
		String title=allTitle.substring(0, allTitle.lastIndexOf(".")); 
		System.out.println(title);
	}
}
