package com.js.pdfsigner.api.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.js.pdfsigner.api.model.Certificate;
import com.js.pdfsigner.api.util.PdfUtil;

@Service
public class CreateTableService {

	public static final String SRC = "C:\\dev\\hello.pdf";
	public static final String DEST = "./target/signatures/hello_signed.pdf";
	public static final String KEYSTORE_PFX = "C:\\Users\\jayme\\certificados\\testes\\jsteste.pfx";
	public static final String KEYSTORE_PFX_2 = "C:\\Users\\jayme\\certificados\\testes\\jaymeteste.pfx";

	public void createFromFile() {
		try {
			PdfReader reader = new PdfReader(SRC);
			var baos = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			PdfImportedPage page;
//			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;

			while (pageOfCurrentReaderPDF < reader.getNumberOfPages()) {
				if (pageOfCurrentReaderPDF > 0) {
					document.newPage();
				}
				pageOfCurrentReaderPDF++;
//				currentPageNumber++;
				page = writer.getImportedPage(reader, pageOfCurrentReaderPDF);
				cb.addTemplate(page, 0, 0);
			}

//			pageOfCurrentReaderPDF = 0;
			var table = new PdfUtil().createTable(writer);
			table.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));

			table.writeSelectedRows(0, -1, document.left(document.leftMargin()),
					table.getTotalHeight() + document.bottom(document.bottomMargin()), writer.getDirectContent());
//			document.add(table);

			baos.flush();
			document.close();
			baos.close();
			try (OutputStream outputStream = new FileOutputStream("C:\\DEV\\PDF_TEMP.pdf")) {
				baos.writeTo(outputStream);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] createFromBytes(byte[] fileBytes, Certificate certificate) {
		try {
			PdfReader reader = new PdfReader(fileBytes);
			var baos = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			PdfImportedPage page;
//			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;

			while (pageOfCurrentReaderPDF < reader.getNumberOfPages()) {
				if (pageOfCurrentReaderPDF > 0) {
					document.newPage();
				}
				pageOfCurrentReaderPDF++;
//				currentPageNumber++;
				page = writer.getImportedPage(reader, pageOfCurrentReaderPDF);
				cb.addTemplate(page, 0, 0);
			}

//			pageOfCurrentReaderPDF = 0;
			var table = new PdfUtil().createTable(writer, certificate);
			table.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));

			table.writeSelectedRows(0, -1, document.left(document.leftMargin()),
					table.getTotalHeight() + document.bottom(document.bottomMargin()), writer.getDirectContent());
//			document.add(table);

			baos.flush();
			document.close();
			baos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
