package me.bk.testcoverageperfectdemo;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * @author : byungkyu
 * @date : 2021/05/01
 * @description :
 **/
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class CommonSpringRestdocsTest {
	private RequestSpecification spec;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {

		this.spec = new RequestSpecBuilder()
			.addFilter(documentationConfiguration(restDocumentation))
			.build();
	}

	public RequestSpecification getSpec() {
		return spec;
	}
}
