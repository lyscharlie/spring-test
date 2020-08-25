package com.lyscharlie.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lyscharlie.common.annotation.JwtIgnore;
import com.lyscharlie.common.utils.JwtTokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 忽略带JwtIgnore注解的请求, 不做后续token认证校验
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
			if (jwtIgnore != null) {
				return true;
			}
		}

		if (HttpMethod.OPTIONS.equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		// 获取请求头信息authorization信息
		final String authHeader = request.getHeader(JwtTokenUtils.AUTH_HEADER_KEY);
		log.info("## authHeader= {}", authHeader);

		if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
			log.info("### 用户未登录，请先登录 ###");
			throw new Exception("用户未登录，请先登录");
		}

		// 获取token
		String token = authHeader.substring(7);

		// 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
		JwtTokenUtils.parseJWT(token);

		return true;
	}
}
