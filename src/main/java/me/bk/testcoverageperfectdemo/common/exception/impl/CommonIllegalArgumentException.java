package me.bk.testcoverageperfectdemo.common.exception.impl;

import me.bk.testcoverageperfectdemo.common.exception.CommonException;

/**
 * @author : byungkyu
 * @date : 2021/04/30
 * @description :
 **/
public class CommonIllegalArgumentException extends CommonException {
	public static final String ERROR_CODE = "ILLEGAL_ARGUMENT_EXCEPTION";
	public static final String ERROR_MESSAGE = "입력값이 올바르지 않습니다.";

	private String errorCode;
	private String errorMessage;

	public CommonIllegalArgumentException() {
		this.errorCode = ERROR_CODE;
		this.errorMessage = ERROR_MESSAGE;
	}

	public CommonIllegalArgumentException(String message) {
		this.errorCode = ERROR_CODE;
		this.errorMessage = message;
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
