package me.bk.testcoverageperfectdemo.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bk.testcoverageperfectdemo.member.domain.Member;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FindMemberResponse {
	private Long id;
	private String email;
	private String nickname;

	public static FindMemberResponse of(Member member) {
		return new FindMemberResponse(member.getId(), member.getEmail(), member.getNickname());
	}
}
