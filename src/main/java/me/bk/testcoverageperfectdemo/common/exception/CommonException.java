package me.bk.testcoverageperfectdemo.common.exception;

/**
 * @author : byungkyu
 * @date : 2021/04/26
 * @description :
 **/
public class CommonException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
