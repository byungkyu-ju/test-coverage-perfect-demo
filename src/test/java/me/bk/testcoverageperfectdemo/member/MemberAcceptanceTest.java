package me.bk.testcoverageperfectdemo.member;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import me.bk.testcoverageperfectdemo.AcceptanceTest;
import me.bk.testcoverageperfectdemo.common.exception.dto.ErrorResponse;
import me.bk.testcoverageperfectdemo.member.dto.CreateMemberRequest;
import me.bk.testcoverageperfectdemo.member.dto.FindMemberResponse;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
public class MemberAcceptanceTest extends AcceptanceTest {
	//global given
	public static final String EMAIL = "test@gmail.com";
	public static final String PASSWORD = "password";
	public static final String PASSWORD_CONFIRM = "password";
	public static final String NICK_NAME = "tester";

	@DisplayName("회원가입을 할 수 있다")
	@Test
	void 회원가입을_할_수_있다() {
		//given
		CreateMemberRequest request = CreateMemberRequest.createMemberRequestBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD_CONFIRM)
			.nickname(NICK_NAME)
			.build();

		// when - then
		RestAssured
			.given(getSpec())
			.accept(ContentType.JSON)
			.filter(RestAssuredRestDocumentationWrapper.document("create member", requestFields(
				fieldWithPath("email").description("email"),
				fieldWithPath("password").description("password"),
				fieldWithPath("passwordConfirm").description("passwordConfirm"),
				fieldWithPath("nickname").description("nickname")
			)))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().post("/members")
			.then()
			.assertThat().statusCode(201);
	}

	@DisplayName("등록된 회원을 조회할 수 있다")
	@Test
	void 등록된_회원을_조회할_수_있다(){
		// given
		ExtractableResponse<Response> createdMemberResponse = 회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);
		String location = createdMemberResponse.header("Location");
		String[] splitLocation = location.split("/");

		ValidatableResponse body = RestAssured
			.given(getSpec())
			.accept(ContentType.JSON)
			.filter(RestAssuredRestDocumentationWrapper.document("find member by id", pathParameters(
				parameterWithName("id").description("member id")
			),
				responseFields(
					fieldWithPath("id").description("id"),
					fieldWithPath("email").description("member email"),
					fieldWithPath("nickname").description("member nickname")
				)
				))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/members/{id}", splitLocation[2])
			.then()
			.assertThat().statusCode(200);
	}

	@DisplayName("회원 가입시 중복이메일 에러")
	@Test
	void createMemberExceptionWithDuplicateEmail(){
		// given
		회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);

		// when
		ExtractableResponse<Response> duplicateEmailResponse = 회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);

		// then
		중복된_이메일(duplicateEmailResponse);
	}


	private void 중복된_이메일(ExtractableResponse<Response> response) {
		ErrorResponse errorResponse = response.body().jsonPath().getObject(".", ErrorResponse.class);

		assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(response.statusCode());
		assertThat("중복된 이메일이 존재합니다.").isEqualTo(errorResponse.getMessage());
		assertThat("ILLEGAL_ARGUMENT_EXCEPTION").isEqualTo(errorResponse.getCode());

	}

	@DisplayName("존재하지 않는 회원 조회 에러")
	@Test
	void findMemberNotExistMemberException(){
		//given
		ExtractableResponse<Response> createMemberResponse = 회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);

		// when
		String createdMemberLocation = createMemberResponse.response().getHeader("Location");
		String[] splitLocation = createdMemberLocation.split("/");
		String invalidMemberLocation = splitLocation[1] + "/" + Long.parseLong("2");

		ExtractableResponse<Response> findMemberResponse = Location으로_URL_호출(invalidMemberLocation);

		// then
		찾을_수_없는_회원(findMemberResponse);

	}

	private void 찾을_수_없는_회원(ExtractableResponse<Response> response) {
		ErrorResponse errorResponse = response.body().jsonPath().getObject(".", ErrorResponse.class);

		assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(response.statusCode());
		assertThat("회원정보를 찾을 수 없습니다.").isEqualTo(errorResponse.getMessage());
		assertThat("ILLEGAL_ARGUMENT_EXCEPTION").isEqualTo(errorResponse.getCode());
	}

	private void 저장된_회원이_조회됨(ExtractableResponse<Response> response) {
		assertThat(HttpStatus.OK.value()).isEqualTo(response.statusCode());

		FindMemberResponse findMemberResponse = response.as(FindMemberResponse.class);
		assertThat(findMemberResponse.getId()).isNotNull();
		assertThat(EMAIL).isEqualTo(findMemberResponse.getEmail());
		assertThat(NICK_NAME).isEqualTo(findMemberResponse.getNickname());
	}

	private ExtractableResponse<Response> 저장된_회원을_조회(ExtractableResponse<Response> response) {
		return Location으로_URL_호출(response.header("Location"));
	}

	private ExtractableResponse<Response> Location으로_URL_호출(String location) {
		return RestAssured
			.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get(location)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 회원_생성(String email, String password, String passwordConfirm,
		String nickname) {
		CreateMemberRequest request = CreateMemberRequest.createMemberRequestBuilder()
			.email(email)
			.password(password)
			.passwordConfirm(passwordConfirm)
			.nickname(nickname)
			.build();

		return RestAssured
			.given()
			.log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().post("/members")
			.then().log().all()
			.extract();
	}

	private void 회원이_등록됨(ExtractableResponse<Response> response) {
		assertThat(HttpStatus.CREATED.value()).isEqualTo(response.statusCode());
	}
}
