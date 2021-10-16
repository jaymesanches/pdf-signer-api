package com.js.pdfsigner.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.js.pdfsigner.api.model.Certificate;
import com.js.pdfsigner.api.service.SignService;

@RestController
@RequestMapping("/sign")
public class SignController {

	private SignService signService;

	@Autowired
	public SignController(SignService signService) {
		this.signService = signService;
	}

	@PostMapping("teste")
	public ResponseEntity<Certificate> teste(@RequestBody Certificate signer) {
		return ResponseEntity.ok(signer);
	}

	@PostMapping
	public ResponseEntity<Void> sign(@RequestParam("file") MultipartFile file,
			@RequestParam("certificate") String certificateJson) {
		try {
			var certificate = new ObjectMapper().readValue(certificateJson, Certificate.class);
			signService.signA1(file, certificate);
		} catch (GeneralSecurityException | IOException | DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

//	@PostMapping
//	public ResponseEntity<Void> sign(@RequestParam("file") MultipartFile file,
//			@RequestParam("signer") String signerJson) {
//		try {
//			var signer = new ObjectMapper().readValue(signerJson, Signer.class);
//			signService.sign(file, signer);
//		} catch (GeneralSecurityException | IOException | DocumentException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
