package com.happy.springboot.security.handler;

import com.alibaba.fastjson.JSON;
import com.happy.springboot.common.constant.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
* 登录失败
* 
* @author Happy
* @date 2019/11/8
*/
@Component
@Log4j2
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		log.info("登录失败:{}",e.getMessage());
		BaseResult baseResult = new BaseResult(CodeEnums.LOGIN_FAILURE);
		httpServletResponse.setCharacterEncoding(Constants.CHARSET_UTF8);
		httpServletResponse.getWriter().print(JSON.toJSONString(baseResult));
	}
}
