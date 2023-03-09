package com.example.otasmeservice;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

@SpringBootApplication
public class OtaSmeServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(OtaSmeServiceApplication.class, args);
	}
	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setDefaultEncoding("UTF-8");
		freeMarkerConfigurer.setTemplateLoaderPath("classpath:invoice");
		return freeMarkerConfigurer;}
	@Bean
	public Template getFreemarkerTemplate(FreeMarkerConfigurer configuration) throws TemplateException, IOException {
		return configuration.createConfiguration().getTemplate("invoice.ftlh", "UTF-8");
	}


}
