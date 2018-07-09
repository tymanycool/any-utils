package com.tiany.util.io;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WordUtil {
    /**
     * 读取word文件内容(只读取文本)
     *
     * @param wordFile
     * @return buffer
     */
    public static String read(File wordFile) {
        String buffer = "";
        try {
            if (wordFile.getName().endsWith(".doc")) {
                InputStream is = new FileInputStream(wordFile);
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (wordFile.getName().endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(wordFile.getAbsolutePath());
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
                throw new IllegalArgumentException("此文件不是word文件！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("解析Word文档失败！");
        }
        return buffer;
    }
}
