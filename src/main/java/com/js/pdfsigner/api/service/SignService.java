package com.js.pdfsigner.api.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.js.pdfsigner.api.model.Certificate;

public interface SignService {
	public void signA1(MultipartFile file, Certificate certificate) throws GeneralSecurityException, IOException, DocumentException;
}
