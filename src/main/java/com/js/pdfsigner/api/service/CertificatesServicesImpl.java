package com.js.pdfsigner.api.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.js.pdfsigner.api.model.SignerCertificate;

@Service
public class CertificatesServicesImpl implements CertificatesService {

	@Override
	public List<SignerCertificate> list() {
		List<SignerCertificate> certificates = new ArrayList<>();
		try {
//			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			KeyStore ks = KeyStore.getInstance("Windows-MY");
			ks.load(null, null);

			Enumeration<String> aliases = ks.aliases();

			System.out.println("Listing aliases " + ks.size());
			while (aliases.hasMoreElements()) {
				String ka = aliases.nextElement();
				Certificate cert = ks.getCertificate(ka);
				X509Certificate x509 = (X509Certificate) cert;
				
				PrivateKey pk = (PrivateKey) ks.getKey(ka, "senha".toCharArray());
				
				System.out.println(ka + " " + ks.isKeyEntry(ka) + " Serial: " + x509.getSerialNumber());
				var signerCertificate = SignerCertificate.builder()
						.name(ka)
						.serial(x509.getSerialNumber().toString())
						.build();
				certificates.add(signerCertificate);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return certificates;
	}

	public Certificate[] getCerticate(String keystore, String password)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			IOException, UnrecoverableKeyException {
		BouncyCastleProvider provider = new BouncyCastleProvider();
		Security.addProvider(provider);
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream(keystore), password.toCharArray());
		String alias = ks.aliases().nextElement();
		PrivateKey pk = (PrivateKey) ks.getKey(alias, password.toCharArray());
		Certificate[] chain = ks.getCertificateChain(alias);
		
		return chain;
	}

}
