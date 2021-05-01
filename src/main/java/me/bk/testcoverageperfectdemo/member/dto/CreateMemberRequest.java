package me.bk.testcoverageperfectdemo.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateMemberRequest {
	@NotNull
	@NotEmpty
	@Email
	private String email;
	@NotNull
	@NotEmpty
	private String password;
	@NotNull
	@NotEmpty
	private String passwordConfirm;
	@NotNull
	@NotEmpty
	private String nickname;

	@Builder(builderMethodName = "createMemberRequestBuilder")
	public CreateMemberRequest(String email, String password, String passwordConfirm, String nickname) {
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.nickname = nickname;
	}
}