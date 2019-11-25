package com.happy.springboot.security.component;

import com.alibaba.fastjson.JSON;
import com.happy.springboot.common.constant.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import com.happy.springboot.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		httpServletResponse.setCharacterEncoding(Constants.CHARSET_UTF8);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String token = jwtUtils.generateToken(userDetails);
		httpServletResponse.getWriter().print(JSON.toJSONString(new BaseResult(CodeEnums.LOGIN_SUCCESS,token)));
	}
}
