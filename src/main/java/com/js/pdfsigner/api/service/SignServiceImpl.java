package com.js.pdfsigner.api.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.js.pdfsigner.api.model.Certificate;

@Service
public class SignServiceImpl implements SignService {

	private final String DEST = "./target/signatures/%s_signed.pdf";

	@Override
	public void signA1(MultipartFile file, Certificate certificate)
			throws GeneralSecurityException, IOException, DocumentException {
		this.signA1(certificate.getName(), 
				certificate.getPassword(), 
				PdfSignatureAppearance.NOT_CERTIFIED, 
				file,
				certificate.getName());
	}

	private void signA1(String alias, String password, int level, MultipartFile file, String name)
			throws GeneralSecurityException, IOException, DocumentException {
		var PASSWORD = password.toCharArray();
		
		KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
		
//		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//		KeyStore ks = KeyStore.getInstance("pkcs12");
//		ks.load(new FileInputStream(keystore), PASSWORD);
		ks.load(null, PASSWORD);
//		String alias = (String) ks.aliases().nextElement();
		PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
		java.security.cert.Certificate[] chain = ks.getCertificateChain(alias);
		// Creating the reader and the stamper
		PdfReader reader = new PdfReader(file.getBytes());
//		FileOutputStream os = new FileOutputStream("C:\\temp\\uploads\\hello_signed.pdf");
		FileOutputStream os = new FileOutputStream(String.format(DEST, name));
		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
		// Creating the appearance
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//		appearance.setVisibleSignature(name);
//		appearance.setVisibleSignature(new Rectangle(36, 88, 200, 0), 1, name);
		appearance.setCertificationLevel(level);

//		BouncyCastleProvider provider = new BouncyCastleProvider();
//		Security.addProvider(provider);
		// Creating the signature
//		ExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, provider.getName());
		ExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, ks.getProvider().getName());
		ExternalDigest digest = new BouncyCastleDigest();
		MakeSignature.signDetached(appearance, digest, pks, chain, null, null, null, 0, CryptoStandard.CMS);
	}
	
		public void addAnnotation(String src, String dest) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest), '\0', true);
		PdfAnnotation comment = PdfAnnotation.createText(stamper.getWriter(), new Rectangle(200, 800, 250, 820),
				"Finally Signed!", "Document Signed", true, "Comment");
		stamper.addAnnotation(comment, 1);
		stamper.close();
	}
}
