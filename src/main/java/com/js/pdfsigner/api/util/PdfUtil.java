package com.js.pdfsigner.api.util;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.js.pdfsigner.api.model.Certificate;

public class PdfUtil {

	public PdfPTable createTable(PdfWriter writer, Certificate certificate) throws IOException, DocumentException {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(50);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(createSignatureFieldCell(writer, "sig1"));
		var cell = new PdfPCell(new Phrase(certificate.getName()));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP);
		table.addCell(cell);
		
		return table;
	}
	
	public PdfPTable createTable(PdfWriter writer) throws IOException, DocumentException {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell("Signer 1: Jayme Sanches");
		table.addCell(createSignatureFieldCell(writer, "sig1"));
		table.addCell("Signer 2: JS Sistemas");
		table.addCell(createSignatureFieldCell(writer, "sig2"));
		
		return table;
	}
	
	protected PdfPCell createSignatureFieldCell(PdfWriter writer, String name) {
		PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(50);
		cell.setBorder(Rectangle.NO_BORDER);
		PdfFormField field = PdfFormField.createSignature(writer);
		field.setFieldName(name);
		field.setFlags(PdfAnnotation.FLAGS_PRINT);
		cell.setCellEvent(new SignatureFieldEvent(field));
		return cell;
	}
}
