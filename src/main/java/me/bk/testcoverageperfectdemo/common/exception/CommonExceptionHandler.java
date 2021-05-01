package me.bk.testcoverageperfectdemo.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.bk.testcoverageperfectdemo.common.exception.dto.ErrorResponse;
import me.bk.testcoverageperfectdemo.common.exception.impl.CommonBadRequestException;

/**
 * @author : byungkyu
 * @date : 2021/04/26
 * @description :
 **/
@RestControllerAdvice
public class CommonExceptionHandler {
	@ExceptionHandler(CommonBadRequestException.class)
	protected ResponseEntity<ErrorResponse> commonBadRequestException(CommonBadRequestException exception) {
		return ResponseEntity.badRequest().body(new ErrorResponse(exception));
	}

}
