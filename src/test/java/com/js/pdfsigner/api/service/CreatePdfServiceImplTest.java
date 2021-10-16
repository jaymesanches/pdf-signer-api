package com.js.pdfsigner.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreatePdfServiceImplTest {
	
	@Autowired
	private CreatePdfService createPdfService;

	@Test
	public void testCreatePdf() {

		var exception = Assertions.catchThrowable(() -> createPdfService.createPdf(null));

		assertThat(exception).isNull();
	}

}
