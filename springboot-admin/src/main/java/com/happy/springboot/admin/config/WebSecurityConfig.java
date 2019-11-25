package com.happy.springboot.admin.config;

import com.happy.springboot.admin.filter.JwtAuthenticationFilter;
import com.happy.springboot.admin.service.AdminUserDetailsServiceImpl;
import com.happy.springboot.security.component.*;
import com.happy.springboot.security.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security配置
 *
 * @author Happy
 * @date 2019/11/7
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AdminUserDetailsServiceImpl adminUserDetailsService;

	@Autowired
	private SecurityAccessDeniedHandler securityAccessDeniedHandler;

	@Autowired
	private SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

	@Autowired
	private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

	@Autowired
	private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;

	@Autowired
	private SecurityLogoutSuccessHandler securityLogoutSuccessHandler;

	@Autowired
	private AdminUserAuthenticationProvider adminUserAuthenticationProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(adminUserAuthenticationProvider);
		auth.userDetailsService(adminUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();

		// 不需要进行登录拦截的url
		for (String url : ignoreUrlsConfig().getUrls()) {
			registry.antMatchers(url).permitAll();
		}

		//允许跨域请求的OPTIONS请求
		registry.antMatchers(HttpMethod.OPTIONS)
				.permitAll()
				// 任何请求需要身份认证
				.and()
				.authorizeRequests()
				// 其他的路径都是登录后才可访问
				.anyRequest()
				.authenticated()
				// 去掉CSRF,使用 JWT，关闭session
				.and().csrf().disable()
				// 开启跨域
				.cors()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// 自定义未登录返回
				.and()
				.httpBasic().authenticationEntryPoint(securityAuthenticationEntryPoint)

				// RBAC 动态 url 认证
//				.access("@rbacauthorityservice.hasPermission(request,authentication)")

				//开启登录
				.and()
				.formLogin()
//				.successForwardUrl("/login/success")
//				.loginPage("http://localhost:9527/")
//				.loginProcessingUrl("/login")
				// 登录成功
				.successHandler(securityAuthenticationSuccessHandler)
				// 登录失败
				.failureHandler(securityAuthenticationFailureHandler)
				.permitAll()
				// 无权访问 JSON 格式的数据
				.and()
				.exceptionHandling().accessDeniedHandler(securityAccessDeniedHandler)
				// 添加自定义jwtToken拦截器
				.and()
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

				// 注销用户自定义方法
				.logout()
				.logoutSuccessHandler(securityLogoutSuccessHandler)
				.permitAll();
	}

	/**
	 * 释放静态资源
	 *
	 * @param webSecurity
	 * @return void
	 * @author Happy
	 * @date 2019/11/8
	 */
	@Override
	public void configure(WebSecurity webSecurity){
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public IgnoreUrlsConfig ignoreUrlsConfig() {
		return new IgnoreUrlsConfig();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
}
