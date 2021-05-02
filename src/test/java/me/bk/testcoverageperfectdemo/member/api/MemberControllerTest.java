package me.bk.testcoverageperfectdemo.member.api;

import static java.util.Optional.*;
import static me.bk.testcoverageperfectdemo.member.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.restassured.RestAssured;
import me.bk.testcoverageperfectdemo.CommonSpringRestdocsTest;
import me.bk.testcoverageperfectdemo.common.config.CommonPasswordEncoder;
import me.bk.testcoverageperfectdemo.common.exception.impl.CommonBadRequestException;
import me.bk.testcoverageperfectdemo.member.application.MemberService;
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
class MemberControllerTest{
	private CommonPasswordEncoder passwordEncoder;

	@Mock
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		passwordEncoder = new CommonPasswordEncoder();
	}

	@DisplayName("회원 생성")
	@Test
	void createMember() {
		//given
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
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);

		MemberController memberController = new MemberController(memberService);

		// when
		ResponseEntity<Long> createMemberResponse = memberController.createMember(request);

		// then
		URI uri = createMemberResponse.getHeaders().getLocation();
		assertThat(HttpStatus.CREATED).isEqualTo(createMemberResponse.getStatusCode());
		assertThat(uri).isNotNull();
		assertThat(mockMember.getId()).isEqualTo(Long.parseLong(uri.getPath().split("/")[2]));

	}

	@DisplayName("회원 생성시 중복이메일 에러")
	@Test
	void createMemberErrorWithDuplicateEmail() {
		//given
		CreateMemberRequest request = CreateMemberRequest.createMemberRequestBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD_CONFIRM)
			.nickname(NICK_NAME)
			.build();

		Member member = Member.createMemberBuilder()
			.email(EMAIL)
			.password(PASSWORD)
			.nickname(NICK_NAME)
			.build();

		when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);

		MemberController memberController = new MemberController(memberService);

		// when - then
		assertThatThrownBy(() -> memberController.createMember(request)).isInstanceOf(CommonBadRequestException.class)
			.extracting("errorMessage")
			.isEqualTo("중복된 이메일이 존재합니다.");
	}

	@DisplayName("저장된 회원 조회")
	@Test
	void findMember() {
		// given
		Long dummyId = 1L;

		Member mockMember = mock(Member.class);
		when(mockMember.getId()).thenReturn(dummyId);
		when(mockMember.getEmail()).thenReturn(EMAIL);
		when(mockMember.getNickname()).thenReturn(NICK_NAME);

		when(memberRepository.findById(dummyId)).thenReturn(of(mockMember));
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);
		MemberController memberController = new MemberController(memberService);

		// when
		ResponseEntity<FindMemberResponse> findMemberResponse = memberController.findMember(dummyId);

		// then
		assertThat(HttpStatus.OK).isEqualTo(findMemberResponse.getStatusCode());
		FindMemberResponse results = findMemberResponse.getBody();

		assert results != null;
		assertThat(mockMember.getId()).isEqualTo(results.getId());
		assertThat(mockMember.getEmail()).isEqualTo(results.getEmail());
		assertThat(mockMember.getNickname()).isEqualTo(results.getNickname());
	}

	@DisplayName("존재하지 않는 회원 조회")
	@Test
	void findMemberErrorWithNotExistMember() {
		// when
		MemberService memberService = new MemberService(passwordEncoder, memberRepository);
		MemberController memberController = new MemberController(memberService);
		// then
		assertThatThrownBy(() -> memberController.findMember(any())).isInstanceOf(CommonBadRequestException.class)
			.extracting("errorMessage")
			.isEqualTo("회원정보를 찾을 수 없습니다.");

	}
}