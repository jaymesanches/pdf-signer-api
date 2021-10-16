package com.js.pdfsigner.api.service;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

public interface CreatePdfService {
	public void createPdf(String filename) throws IOException, DocumentException;
}
