package com.js.pdfsigner.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.js.pdfsigner.api.model.SignerCertificate;
import com.js.pdfsigner.api.service.CertificatesService;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
	private CertificatesService certificateService;

	@Autowired
	public CertificateController(CertificatesService certificateService) {
		this.certificateService = certificateService;
	}

	@GetMapping
	public ResponseEntity<List<SignerCertificate>> certificatesList() {
		var certificates = certificateService.list();
		return ResponseEntity.ok(certificates);
	}

}
