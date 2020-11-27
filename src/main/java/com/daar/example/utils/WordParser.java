package com.daar.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordParser {

	public static String parseDocx(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		XWPFDocument doc = new XWPFDocument(fis);
		XWPFWordExtractor we = new XWPFWordExtractor(doc);
		String parsedText = we.getText();
		parsedText = parsedText.replace("\n", " ").replace("\t", " ");
		doc.close();
		fis.close();
		return parsedText;
	}

	public static String parseDoc(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		HWPFDocument doc = new HWPFDocument(fis);
		WordExtractor we = new WordExtractor(doc);
		String parsedText = we.getText();
		parsedText = parsedText.replace("\n", " ").replace("\r", " ").replace("\t", " ");
		doc.close();
		fis.close();
		return parsedText;
	}

}
