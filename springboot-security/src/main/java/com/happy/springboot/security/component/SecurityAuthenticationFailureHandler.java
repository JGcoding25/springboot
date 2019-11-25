package com.happy.springboot.security.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happy.springboot.common.constant.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
		httpServletResponse.setCharacterEncoding(Constants.CHARSET_UTF8);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("exception",e.getMessage());
		httpServletResponse.getWriter().print(JSON.toJSONString( new BaseResult(CodeEnums.LOGIN_FAILURE,jsonObject)));
	}
}
