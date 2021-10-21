package com.js.pdfsigner.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.js.pdfsigner.api.model.Certificate;
import com.js.pdfsigner.api.service.CreateTableService;
import com.js.pdfsigner.api.service.SignService;

@RestController
@RequestMapping("/sign")
public class SignController {

	private SignService signService;
	private CreateTableService tableService;

	@Autowired
	public SignController(SignService signService, CreateTableService tableService) {
		this.signService = signService;
		this.tableService = tableService;
	}

	@PostMapping
	public ResponseEntity<byte[]> sign(@RequestParam("file") MultipartFile file,
			@RequestParam("certificate") String certificateJson) {
		try {
			var certificate = new ObjectMapper().readValue(certificateJson, Certificate.class);
			var fileWithSignatureField = tableService.createFromBytes(file.getBytes(), certificate);
			var result = signService.signA1(fileWithSignatureField, certificate);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(result);

		} catch (GeneralSecurityException | IOException | DocumentException e) {
			e.printStackTrace();
		}

		return ResponseEntity.internalServerError().build();
	}
}
