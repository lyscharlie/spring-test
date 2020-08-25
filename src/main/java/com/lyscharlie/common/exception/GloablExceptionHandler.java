package com.lyscharlie.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyscharlie.common.dto.R;

@ControllerAdvice
public class GloablExceptionHandler {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public R<Object> handleException(Exception e) {
		R<Object> r = new R<>();
		r.setSuccess(false);

		if (StringUtils.isNotBlank(e.getMessage())) {
			r.setMsg(e.getMessage());
		} else {
			r.setMsg("系统异常");
		}

		return r;
	}

}
