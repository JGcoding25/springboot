package com.happy.springboot.admin.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.happy.springboot.admin.service.AdminUserDetailsServiceImpl;
import com.happy.springboot.common.constant.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import com.happy.springboot.security.util.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* jwtToken 拦截器
* 
* @author Happy
* @date 2019/11/23
*/
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${jwt.tokenHeader}")
	private String jwtTokenHeader;
	@Value("${jwt.tokenHead}")
	private String jwtTokenHead;

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AdminUserDetailsServiceImpl adminUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = httpServletRequest.getHeader(jwtTokenHeader);
		if(!Strings.isNullOrEmpty(authHeader) && authHeader.startsWith(jwtTokenHead)){

			// 获取token
			String token = authHeader.substring(jwtTokenHead.length());

			// 根据token获取用户名
			String username = jwtUtils.getUserNameFromToken(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = adminUserDetailsService.loadUserByUsername(username);

				// 验证用户名信息是否正确，token是否过期
				if (jwtUtils.validateToken(token, userDetails)) {
					// token认证成功，验证用户名密码
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}else{
					httpServletResponse.setCharacterEncoding(Constants.CHARSET_UTF8);
					httpServletResponse.getWriter().print(JSON.toJSONString(new BaseResult(CodeEnums.TOKEN_AUTHENTICATION_FAILURE)));
					return;
				}
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
