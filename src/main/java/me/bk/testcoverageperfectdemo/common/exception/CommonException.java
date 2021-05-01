package me.bk.testcoverageperfectdemo.common.exception;

import lombok.Getter;

/**
 * @author : byungkyu
 * @date : 2021/04/26
 * @description :
 **/
@Getter
public class CommonException extends RuntimeException {
	private String errorCode;
	private String errorMessage;
}
