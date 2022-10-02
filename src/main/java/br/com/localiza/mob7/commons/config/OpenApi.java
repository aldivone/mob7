package br.com.localiza.mob7.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class OpenApi {

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI().info(new Info().title("Controller API").version(appVersion)
				.description("Documentação da api de pontos de interesse Mob7.").termsOfService("http://mob7.com.br")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
