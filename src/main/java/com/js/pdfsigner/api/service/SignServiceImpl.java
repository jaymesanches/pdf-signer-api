package com.js.pdfsigner.api.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

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

//	private final String DEST = "./target/signatures/%s_signed.pdf";

	@Override
	public byte[] signA1(byte[] file, Certificate certificate)
			throws GeneralSecurityException, IOException, DocumentException {
		return this.signA1(certificate.getName(), certificate.getPassword(), PdfSignatureAppearance.NOT_CERTIFIED, file,
				certificate.getName());
	}

	private byte[] signA1(String alias, String password, int level, byte[] file, String name)
			throws GeneralSecurityException, IOException, DocumentException {
		var passwordChar = password.toCharArray();

		KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");

//		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//		KeyStore ks = KeyStore.getInstance("pkcs12");
//		ks.load(new FileInputStream(keystore, passwordChar);
//		ks.load(null, passwordChar);
		ks.load(null, null);
//		String alias = (String) ks.aliases().nextElement();
		PrivateKey pk = (PrivateKey) ks.getKey(alias, passwordChar);
		java.security.cert.Certificate[] chain = ks.getCertificateChain(alias);
		var cert = ks.getCertificate(alias);
		// Creating the reader and the stamper
		PdfReader reader = new PdfReader(file);
//		FileOutputStream fos = new FileOutputStream(String.format(DEST, name));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfStamper stamper = PdfStamper.createSignature(reader, baos, '\0', null, true);
		// Creating the appearance
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setVisibleSignature("sig1");
		appearance.setSignatureCreator("Protocolo Digital");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		appearance.setLayer2Text(
				String.format("Assinado Digitalmente por %s em %s", alias, LocalDateTime.now().format(formatter)));
		appearance.setLayer4Text(String.format("Assinado Digitalmente por %s.", alias));
		appearance.setCertificate(cert);
//		appearance.setVisibleSignature(new Rectangle(36, 88, 200, 0), 1, name);
		appearance.setCertificationLevel(level);

//		BouncyCastleProvider provider = new BouncyCastleProvider();
//		Security.addProvider(provider);
		// Creating the signature
//		ExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, provider.getName());
		ExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, ks.getProvider().getName());
		ExternalDigest digest = new BouncyCastleDigest();
		MakeSignature.signDetached(appearance, digest, pks, chain, null, null, null, 0, CryptoStandard.CMS);

//		baos.writeTo(fos);
		var result = baos.toByteArray();
		baos.flush();
		baos.close();
		stamper.close();
		return result;
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
