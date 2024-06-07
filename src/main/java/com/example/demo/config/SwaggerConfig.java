package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		 return new OpenAPI()
	                .info(new Info()
	                        .title("Employee Restful API")
	                        .description("This is the Employee RESTful API documentation")
	                        .version("1.0.0")
	                        .termsOfService("http://example.com/terms/")
	                        .contact(new Contact()
	                                .name("A")
	                                .url("http://yourwebsite.com")
	                                .email("your-email@yourdomain.com"))
	                        .license(new License()
	                                .name("Apache 2.0")
	                                .url("http://springdoc.org")));
	}
}
