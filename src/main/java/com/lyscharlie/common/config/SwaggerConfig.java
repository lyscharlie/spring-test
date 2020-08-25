package com.lyscharlie.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

	@Value("${swagger.enable}")
	private boolean enableSwagger;

	@Bean
	public Docket createAllRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(enableSwagger)
				.apiInfo(apiInfo())
				.groupName("all")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lyscharlie.web"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(headerAuthorization());
	}

	@Bean
	public Docket createAreaRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(enableSwagger)
				.apiInfo(apiInfo())
				.groupName("area")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lyscharlie.web.area"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public Docket createTestRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(enableSwagger)
				.apiInfo(apiInfo())
				.groupName("test")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lyscharlie.web.test"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public Docket createUserRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(enableSwagger)
				.apiInfo(apiInfo())
				.groupName("user")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lyscharlie.web.user"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("lyscharlie", "www.lyscharlie.com", "lyscharlie@hotmail.com");
		return new ApiInfoBuilder()
				.title("我是文档标题")
				.description("我是文档描述")
				.contact(contact)   // 联系方式
				.version("1.0.0")  // 版本
				.build();
	}

	private List<Parameter> headerAuthorization() {
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<>();
		tokenPar.name("Authorization")
				.description("令牌")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false)
				.build();
		pars.add(tokenPar.build());
		return pars;
	}
}
