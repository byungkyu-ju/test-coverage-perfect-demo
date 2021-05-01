package me.bk.testcoverageperfectdemo.member.dto;

import static me.bk.testcoverageperfectdemo.member.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author : byungkyu
 * @date : 2021/04/30
 * @description :
 **/
class CreateMemberRequestTest {

	@DisplayName("createMemberRequestBuilderTest")
	@Test
	void createMemberRequestBuilderTest() {
		CreateMemberRequest request = CreateMemberRequest.createMemberRequestBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD_CONFIRM)
			.nickname(NICK_NAME)
			.build();

		assertThat(EMAIL).isEqualTo(request.getEmail());
		assertThat(PASSWORD).isEqualTo(request.getPassword());
		assertThat(PASSWORD_CONFIRM).isEqualTo(request.getPasswordConfirm());
		assertThat(NICK_NAME).isEqualTo(request.getNickname());
	}
}