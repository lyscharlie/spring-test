package com.lyscharlie.web.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyscharlie.common.dto.R;
import com.lyscharlie.common.utils.RedisUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "redis")
@RestController
@RequestMapping(value = "redis")
@Slf4j
public class RedisController {

	@Autowired
	private RedisUtils redisUtils;

	@ApiOperation(value = "setString")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "key", value = "key", required = true, dataType = "string"),
			@ApiImplicitParam(name = "value", value = "value", required = true, dataType = "string")
	})
	@RequestMapping(value = "/setString.htm", method = RequestMethod.POST)
	public R<Object> setString(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
		boolean success = this.redisUtils.set(key, value);

		R<Object> r = new R<>();
		r.setSuccess(success);
		return r;
	}

	@ApiOperation(value = "getString")
	@ApiImplicitParam(name = "key", value = "key", required = true, dataType = "string")
	@RequestMapping(value = "/getString.htm", method = RequestMethod.POST)
	public R<Object> getString(@RequestParam(value = "key") String key) {
		Object o = this.redisUtils.get(key);

		R<Object> r = new R<>();
		r.setSuccess(true);
		r.setData(o);
		return r;
	}
}
