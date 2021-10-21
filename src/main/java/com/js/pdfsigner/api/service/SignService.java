package com.js.pdfsigner.api.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.itextpdf.text.DocumentException;
import com.js.pdfsigner.api.model.Certificate;

public interface SignService {
	public byte[] signA1(byte[] file, Certificate certificate)
			throws GeneralSecurityException, IOException, DocumentException;
}
