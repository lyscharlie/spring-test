package com.lyscharlie.web.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyscharlie.biz.entity.UserDO;
import com.lyscharlie.biz.service.UserService;
import com.lyscharlie.common.annotation.JwtIgnore;
import com.lyscharlie.common.dto.R;
import com.lyscharlie.common.utils.JwtTokenUtils;
import com.lyscharlie.web.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "/user", tags = "用户")
@RestController
@RequestMapping(value = "user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户列表")
	@PostMapping(value = "userList.php")
	public List<UserVO> userList() {
		List<UserVO> resultList = new ArrayList<>();
		List<UserDO> list = this.userService.list(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getIsDelete, 0));
		for (UserDO userDO : list) {
			UserVO vo = new UserVO();
			BeanUtils.copyProperties(userDO, vo);
			resultList.add(vo);
		}
		return resultList;
	}

	@JwtIgnore
	@ApiOperation(value = "登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "password", dataType = "string", defaultValue = "")
	})
	@PostMapping(value = "login.php")
	public R<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
		R<String> r = new R<>();

		if (StringUtils.isBlank(username)) {
			r.setSuccess(false);
			r.setMsg("请输入用户名");
			return r;
		}

		if (StringUtils.isBlank(password)) {
			r.setSuccess(false);
			r.setMsg("请输入密码");
			return r;
		}

		UserDO user = this.userService.findUserByNameAndPassword(username, password);

		if (null == user) {
			r.setSuccess(false);
			r.setMsg("用户名密码错误");
			return r;
		}

		r.setSuccess(true);
		r.setData(JwtTokenUtils.getToken(user));
		r.setMsg("登录成功");
		return r;
	}

}
