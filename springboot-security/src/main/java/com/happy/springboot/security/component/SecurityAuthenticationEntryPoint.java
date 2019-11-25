package com.happy.springboot.security.component;

import com.alibaba.fastjson.JSON;
import com.happy.springboot.common.constant.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* 未登录
* 
* @author Happy
* @date 2019/11/8
*/
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		BaseResult baseResult = new BaseResult(CodeEnums.NO_LOGIN);
		httpServletResponse.setCharacterEncoding(Constants.CHARSET_UTF8);
		httpServletResponse.getWriter().print(JSON.toJSONString(baseResult));
	}
}
