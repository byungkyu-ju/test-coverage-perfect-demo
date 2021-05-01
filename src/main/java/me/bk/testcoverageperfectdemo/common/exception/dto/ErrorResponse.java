package me.bk.testcoverageperfectdemo.common.exception.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bk.testcoverageperfectdemo.common.exception.CommonException;

/**
 * @author : byungkyu
 * @date : 2021/04/26
 * @description :
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponse {
	private String code;
	private String message;

	public ErrorResponse(CommonException exception) {
		this.code = exception.getErrorCode();
		this.message = exception.getErrorMessage();
	}
}
