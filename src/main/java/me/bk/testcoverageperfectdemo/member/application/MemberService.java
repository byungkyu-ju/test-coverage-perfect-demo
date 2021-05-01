package me.bk.testcoverageperfectdemo.member.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.bk.testcoverageperfectdemo.common.config.CommonPasswordEncoder;
import me.bk.testcoverageperfectdemo.common.exception.impl.CommonBadRequestException;
import me.bk.testcoverageperfectdemo.member.domain.Member;
import me.bk.testcoverageperfectdemo.member.dto.CreateMemberRequest;
import me.bk.testcoverageperfectdemo.member.dto.FindMemberResponse;
import me.bk.testcoverageperfectdemo.member.repository.MemberRepository;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
@Service
@RequiredArgsConstructor
public class MemberService {
	private final CommonPasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public Long createMember(CreateMemberRequest createMemberRequest) {
		validateMemberDuplicateEmail(createMemberRequest);
		Member savedMember = memberRepository.save(memberCreateRequestToMember(createMemberRequest));
		return savedMember.getId();
	}

	private Member memberCreateRequestToMember(CreateMemberRequest createMemberRequest) {
		validateMemberPassword(createMemberRequest);
		return Member.createMemberBuilder()
			.email(createMemberRequest.getEmail())
			.password(passwordEncoder.encoder().encode(createMemberRequest.getEmail()))
			.nickname(createMemberRequest.getNickname())
			.build();
	}

	private void validateMemberPassword(CreateMemberRequest createMemberRequest) {
		if (!createMemberRequest.getPassword().equals(createMemberRequest.getPasswordConfirm())) {
			throw new CommonBadRequestException("입력한 비밀번호가 일치하지 않습니다.");
		}
	}

	private void validateMemberDuplicateEmail(CreateMemberRequest createMemberRequest) {
		Optional<Member> findMember = memberRepository.findByEmail(createMemberRequest.getEmail());
		if (findMember.isPresent()) {
			throw new CommonBadRequestException("중복된 이메일이 존재합니다.");
		}
	}

	public FindMemberResponse findMember(Long id) {
		Member member = findMemberById(id);
		return FindMemberResponse.of(member);
	}

	private Member findMemberById(Long id) {
		return memberRepository.findById(id).orElseThrow(() -> new CommonBadRequestException("회원정보를 찾을 수 없습니다."));
	}

}
