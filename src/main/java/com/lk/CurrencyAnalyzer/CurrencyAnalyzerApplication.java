package com.lk.CurrencyAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CurrencyAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyAnalyzerApplication.class, args);
	}

	@RequestMapping("/getExample")
	@ResponseBody
	public String purchase() {
		return "example";
	}



}
