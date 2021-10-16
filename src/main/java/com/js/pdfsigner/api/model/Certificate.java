package com.js.pdfsigner.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
	private String name;
	private String password;
	private String info;
	private String serial;
}
