package com.daar.example.utils;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFParser {
	
	
	public static String parsePDF(File f) throws IOException {
		PDDocument pdf = PDDocument.load(f);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String parsedText = pdfStripper.getText(pdf);
		parsedText = parsedText.replace("\n", " ").replace("\r", " ");
		pdf.close();
		return parsedText;
	}

}
