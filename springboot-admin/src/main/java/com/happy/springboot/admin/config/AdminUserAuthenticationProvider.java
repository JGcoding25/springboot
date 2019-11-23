package com.happy.springboot.admin.config;

import com.happy.springboot.admin.service.AdminUserDetailsService;
import com.happy.springboot.common.util.RsaUtils;
import com.happy.springboot.core.constant.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
* 自定义security验证方法
* 
* @author Happy
* @date 2019/11/22
*/
@Component
@Log4j2
public class AdminUserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AdminUserDetailsService adminUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 认证处理，返回一个Authentication的实现类则代表认证成功，抛出CredentialsExpiredException则失败
	 * @param authentication 认证信息
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		//获取用户信息
		UserDetails adminUser = adminUserDetailsService.loadUserByUsername(username);

		//解密登录密码
		String decryptPassword = null;
		try {
			decryptPassword = RsaUtils.decryptByPrivateKey(password, Constants.LOGIN_PRIVATE_KEY,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(decryptPassword)){
			log.error("密码解析错误");
			throw new CredentialsExpiredException("请输入正确的用户名或密码！");
		}

		if (!passwordEncoder.matches(decryptPassword, adminUser.getPassword())) {
			throw new CredentialsExpiredException("密码不一致");
		}

		//获取用户权限信息
		Collection<? extends GrantedAuthority> authorities = adminUser.getAuthorities();
		return new UsernamePasswordAuthenticationToken(adminUser, password, authorities);
	}

	/**
	 * 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UsernamePasswordAuthenticationToken.class);
	}

}