package me.bk.testcoverageperfectdemo.member.api;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.bk.testcoverageperfectdemo.member.application.MemberService;
import me.bk.testcoverageperfectdemo.member.dto.CreateMemberRequest;
import me.bk.testcoverageperfectdemo.member.dto.FindMemberResponse;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public ResponseEntity createMember(final @Valid @RequestBody CreateMemberRequest createMemberRequest) {
		Long savedId = memberService.createMember(createMemberRequest);
		return ResponseEntity.created(URI.create("/members/" + savedId)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity findMember(final @PathVariable Long id) {
		FindMemberResponse response = memberService.findMember(id);
		return ResponseEntity.ok().body(response);
	}

}
