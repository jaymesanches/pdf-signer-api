package com.js.pdfsigner.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.js.pdfsigner.api.model.Certificate;

@SpringBootTest
public class SignServiceImplTest {

	public static final String SRC = "C:\\dev\\hello.pdf";
	public static final String DEST = "./target/signatures/hello_signed.pdf";
	public static final String KEYSTORE_PFX = "C:\\Users\\jayme\\certificados\\testes\\jsteste.pfx";
	public static final String KEYSTORE_PFX_2 = "C:\\Users\\jayme\\certificados\\testes\\jaymeteste.pfx";
	public static final String KEYSTORE = "C:\\dev\\sts-4.12.0.RELEASE\\workspace1\\itext\\src\\main\\resources\\keystore.jks";
	public static final String PASSWORD = "senha";

	@Autowired
	private SignService signService;

//	@Test
	public void signTest() {
//		Path path = Paths.get(SRC);
//		String name = "hello.pdf";
//		String originalFileName = "hello.pdf";
//		String contentType = "application/pdf";
//		byte[] content = null;
//		try {
//			content = Files.readAllBytes(path);
//		} catch (final IOException e) {
//		}
//		MultipartFile file = new MockMultipartFile(name, originalFileName, contentType, content);
//
//		var signer = Certificate.builder().name("JaymeSanches").keystore(KEYSTORE_PFX).password("senha").build();
//
//		var exception = Assertions.catchThrowable(() -> signService.sign(file, signer));
//
//		assertThat(exception).isNull();
	}


}
