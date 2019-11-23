package com.happy.springboot.security.handler;

import com.alibaba.fastjson.JSON;
import com.happy.springboot.common.constant.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* 登录成功
* 
* @author Happy
* @date 2019/11/8
*/
@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		BaseResult baseResult = new BaseResult(CodeEnums.LOGIN_SUCCESS);
		httpServletResponse.setCharacterEncoding(Constants.CHARSET_UTF8);
		httpServletResponse.getWriter().print(JSON.toJSONString(baseResult));
	}
}
