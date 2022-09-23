package com.sip.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sip.ams.controllers.ArticleController;
import com.sip.ams.controllers.ProviderController;

import java.io.File;
@SpringBootApplication
public class AmsApplication {

	public static void main(String[] args) { 
		new File(ArticleController.uploadDirectory).mkdir();  
		new File(ProviderController.providerDirectory).mkdir();
		SpringApplication.run(AmsApplication.class, args);
	}

}
