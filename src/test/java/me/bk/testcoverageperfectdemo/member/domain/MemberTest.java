package me.bk.testcoverageperfectdemo.member.domain;

import static me.bk.testcoverageperfectdemo.member.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author : byungkyu
 * @date : 2021/04/30
 * @description :
 **/
class MemberTest {

	@DisplayName("createMemberBuilderTest")
	@Test
	void createMemberBuilderTest() {
		Member member = Member.createMemberBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.nickname(NICK_NAME)
			.build();

		assertThat(EMAIL).isEqualTo(member.getEmail());
		assertThat(PASSWORD).isEqualTo(member.getPassword());
		assertThat(NICK_NAME).isEqualTo(member.getNickname());
	}
}