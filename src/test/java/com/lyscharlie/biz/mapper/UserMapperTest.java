package com.lyscharlie.biz.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lyscharlie.SpringTestApplication;
import com.lyscharlie.biz.entity.UserDO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringTestApplication.class)
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void selectCountByRegisterReferId() {
		Long registerReferId = 1L;
		Integer count = this.userMapper.selectCountByRegisterReferId(registerReferId);
		System.out.println(count);
		Assert.assertTrue(count > 0);
	}

	@Test
	public void updateById() {
		// 指定字段修改
		// UserDO user = new UserDO();
		// user.setUserId(1L);
		// user.setEmail("aaa@aaa.com");
		// Assert.assertTrue(this.userMapper.updateById(user) > 0);

		// 全部字段修改
		UserDO user = this.userMapper.selectById(1L);
		user.setEmail("lyscharlie@abc.com");
		Assert.assertTrue(this.userMapper.updateById(user) > 0);
	}
}