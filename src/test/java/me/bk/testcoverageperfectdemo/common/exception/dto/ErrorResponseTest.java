package me.bk.testcoverageperfectdemo.common.exception.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.bk.testcoverageperfectdemo.common.exception.impl.CommonBadRequestException;

/**
 * @author : byungkyu
 * @date : 2021/04/30
 * @description :
 **/
class ErrorResponseTest {
	@DisplayName("CommonException 바인딩 테스트")
	@Test
	void CommonIllegalArgumentExceptionTest() {
		CommonBadRequestException commonBadRequestException = new CommonBadRequestException();
		ErrorResponse errorResponse = new ErrorResponse(commonBadRequestException);

		assertThat(commonBadRequestException.getErrorCode()).isEqualTo(errorResponse.getCode());
		assertThat(commonBadRequestException.getErrorMessage()).isEqualTo(errorResponse.getMessage());

	}
}