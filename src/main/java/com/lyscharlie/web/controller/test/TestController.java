package com.lyscharlie.web.controller.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "/test", tags = "test")
@RestController
@RequestMapping(value = "test")
@Slf4j
public class TestController {

	@Value("${file.path}")
	private String filePath;

	@ApiOperation(value = "hello")
	@GetMapping(value = "hello.htm")
	public String hello() {
		return "hello";
	}

	@ApiOperation(value = "test1")
	@ApiImplicitParam(name = "v", value = "v", dataType = "string")
	@RequestMapping(value = "/test1.htm",method = RequestMethod.GET)
	public String test1(@RequestParam(value = "v", defaultValue = "") String v) {
		return v;
	}

	@ApiOperation(value = "test2")
	@RequestMapping(value = "/test2.htm", method = RequestMethod.GET)
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("maxInteger", Integer.MAX_VALUE);
		map.put("minInteger", Integer.MIN_VALUE);
		map.put("maxDouble", Double.MAX_VALUE);
		map.put("minDouble", Double.MIN_VALUE);
		return map;
	}

	@ApiOperation(value = "test3")
	@RequestMapping(value = "/test3.htm", method = RequestMethod.GET)
	public List<Integer> test3() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		return list;
	}

	@ApiOperation(value = "test4")
	@ApiImplicitParam(name = "v", value = "v", dataType = "int", required = true)
	@RequestMapping(value = "/test4.htm", method = RequestMethod.POST)
	public Integer test4(@RequestParam Integer v) {
		return v;
	}

	@ApiOperation(value = "test5")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "a", value = "a", dataType = "string"),
			@ApiImplicitParam(name = "b", value = "b", dataType = "string")
	})
	@RequestMapping(value = "/test5/{a}-{b}.htm", method = RequestMethod.GET)
	public Map<String, Object> test5(@PathVariable String a, @PathVariable String b) {
		Map<String, Object> map= new HashMap<>();
		map.put("a", a);
		map.put("b", b);
		return map;
	}

	@ApiOperation(value = "test6")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "a", value = "a", dataType = "string"),
			@ApiImplicitParam(name = "b", value = "b", dataType = "string")
	})
	@RequestMapping(value = "/test6.htm", method = RequestMethod.POST)
	public Map<String, Object> test6(@RequestParam String a, @RequestParam String b) {
		Map<String, Object> map= new HashMap<>();
		map.put("a", a);
		map.put("b", b);
		return map;
	}

	@ApiOperation(value = "test7")
	@ApiImplicitParam(name = "file", value = "file", dataType = "file")
	@PostMapping("/test7.htm")
	public String test7(@RequestParam MultipartFile file){
		String fileName = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss") + "_" + file.getOriginalFilename();

		File newFile = new File(filePath + "/"+ fileName);
		try {
			file.transferTo(newFile);
		} catch (IOException e) {
			log.error("test7", e);
		}

		return newFile.getPath();
	}


}
