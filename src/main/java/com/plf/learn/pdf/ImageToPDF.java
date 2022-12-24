package com.plf.learn.pdf;


import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ImageToPDF {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageToPDF.class);

    /**
     * 多张图转为pdf
     * param imageFolderPath
     * param pdfPath
     */
    public static void toPdf(String imageFolderPath, String pdfPath) {
        try {
            long start = System.currentTimeMillis();
            ArrayList<String> list = new ArrayList<>();
            // 图片地址
            String imagePath = null;
            FileOutputStream fos = new FileOutputStream(pdfPath);
            // 创建文档
            Document doc = new Document(null, 20, 20, 20, 20);
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img;
            // 实例化图片
            Image image;
            // 获取图片文件夹对象
            File file = new File(imageFolderPath);
            File[] files = file.listFiles();
            // 循环获取图片文件夹内的图片
            for (File file1 : files) {
                if (file1.getName().endsWith(".png") || file1.getName().endsWith(".jpg") || file1.getName().endsWith(".gif")
                        || file1.getName().endsWith(".jpeg") || file1.getName().endsWith(".tif")) {
                    imagePath = imageFolderPath + file1.getName();
                    list.add(file1.getName());
                    // 读取图片流
                    img = ImageIO.read(new File(imagePath));
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 实例化图片
                    image = Image.getInstance(imagePath);
                    // 添加图片到文档
                    doc.open();
                    doc.add(image);
                }
            }
            LOGGER.info("fileName is list={}", list);
            // 关闭文档
            doc.close();
            long endTime = System.currentTimeMillis();
            int time = (int) ((endTime - start) / 1000);
            LOGGER.info("用时：{}:秒！",time);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        toPdf("C:\\Users\\Breeze\\Desktop\\", "C:\\Users\\Breeze\\Desktop\\出生证明.pdf");
    }
}
