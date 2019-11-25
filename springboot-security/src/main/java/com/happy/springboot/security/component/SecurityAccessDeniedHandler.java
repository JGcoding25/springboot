package com.happy.springboot.security.component;

import com.alibaba.fastjson.JSON;
import com.happy.springboot.common.constant.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* 没有权限
* 
* @author Happy
* @date 2019/11/8
*/
@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
		BaseResult baseResult = new BaseResult(CodeEnums.NO_PERMISSION);
		httpServletResponse.setCharacterEncoding(Constants.CHARSET_UTF8);
		httpServletResponse.getWriter().print(JSON.toJSONString(baseResult));
	}
}
