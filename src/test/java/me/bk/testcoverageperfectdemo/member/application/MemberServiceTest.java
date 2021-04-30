package me.bk.testcoverageperfectdemo.member.application;

import static me.bk.testcoverageperfectdemo.member.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.bk.testcoverageperfectdemo.common.config.CommonPasswordEncoder;
import me.bk.testcoverageperfectdemo.member.domain.Member;
import me.bk.testcoverageperfectdemo.member.dto.CreateMemberRequest;
import me.bk.testcoverageperfectdemo.member.dto.FindMemberResponse;
import me.bk.testcoverageperfectdemo.member.repository.MemberRepository;

/**
 * @author : byungkyu
 * @date : 2021/04/30
 * @description :
 **/
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
	private CommonPasswordEncoder passwordEncoder;

	@Mock
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		passwordEncoder = new CommonPasswordEncoder();
	}

	@DisplayName("회원 등록")
	@Test
	void createMember() {
		// given
		Long dummyId = 1L;

		CreateMemberRequest request = CreateMemberRequest.builder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD_CONFIRM)
			.nickname(NICK_NAME)
			.build();

		Member mockMember = mock(Member.class);
		when(mockMember.getId()).thenReturn(1L);

		when(memberRepository.save(any())).thenReturn(mockMember);
		when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);

		// when
		Long savedId = memberService.createMember(request);

		// then
		assertThat(savedId).isEqualTo(1L);

	}

	@DisplayName("회원조회")
	@Test
	void findMember() {
		Long dummyId = 1L;

		Member expectMember = mock(Member.class);
		when(expectMember.getId()).thenReturn(dummyId);

		// when
		when(memberRepository.findById(dummyId)).thenReturn(Optional.ofNullable(expectMember));
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);
		FindMemberResponse memberResponse = memberService.findMember(dummyId);

		// then
		assertThat(dummyId).isEqualTo(memberResponse.getId());

	}
}