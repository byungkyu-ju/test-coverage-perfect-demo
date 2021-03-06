package me.bk.testcoverageperfectdemo;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class AcceptanceTest {
	private static RequestSpecification spec;

	@LocalServerPort
	int port;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		RestAssured.port = port;

		this.spec = new RequestSpecBuilder()
			.addFilter(documentationConfiguration(restDocumentation))
			.build();
	}

	public static RequestSpecification getSpec() {
		return spec;
	}
}
