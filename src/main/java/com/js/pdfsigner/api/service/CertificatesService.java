package com.js.pdfsigner.api.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.List;

import com.js.pdfsigner.api.model.SignerCertificate;

public interface CertificatesService {

	public List<SignerCertificate> list();

	public Certificate[] getCerticate(String keystore, String password) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException;
}
