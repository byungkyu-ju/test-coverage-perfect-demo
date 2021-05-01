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
import me.bk.testcoverageperfectdemo.common.exception.impl.CommonBadRequestException;
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

		CreateMemberRequest request = CreateMemberRequest.createMemberRequestBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD_CONFIRM)
			.nickname(NICK_NAME)
			.build();

		Member mockMember = mock(Member.class);
		when(mockMember.getId()).thenReturn(dummyId);

		when(memberRepository.save(any())).thenReturn(mockMember);
		when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);

		// when
		Long savedId = memberService.createMember(request);

		// then
		assertThat(savedId).isEqualTo(dummyId);

	}

	@DisplayName("일치하지 않는 비밀번호")
	@Test
	void createMemberExceptionWhenInvalidPassword(){
		//given
		CreateMemberRequest request = CreateMemberRequest.createMemberRequestBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm("1234")
			.nickname(NICK_NAME)
			.build();

		MemberService memberService = new MemberService(passwordEncoder, memberRepository);

		// when - then
		assertThatThrownBy(() -> memberService.createMember(request)).isInstanceOf(CommonBadRequestException.class)
			.extracting("errorMessage")
			.isEqualTo("입력한 비밀번호가 일치하지 않습니다.");
	}

	@DisplayName("중복된 이메일 에러")
	@Test
	void duplicateEmailException() {
		CreateMemberRequest request = CreateMemberRequest.createMemberRequestBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm("1234")
			.nickname(NICK_NAME)
			.build();

		Member mockMember = mock(Member.class);
		when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(mockMember));
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);

		// when - then
		assertThatThrownBy(() -> memberService.createMember(request)).isInstanceOf(CommonBadRequestException.class)
			.extracting("errorMessage")
			.isEqualTo("중복된 이메일이 존재합니다.");
	}


	@DisplayName("회원조회")
	@Test
	void findMember() {
		Long dummyId = 1L;

		Member expectMember = mock(Member.class);
		when(expectMember.getId()).thenReturn(dummyId);

		// when
		when(memberRepository.findById(dummyId)).thenReturn(Optional.of(expectMember));
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);
		FindMemberResponse memberResponse = memberService.findMember(dummyId);

		// then
		assertThat(dummyId).isEqualTo(memberResponse.getId());

	}
}