package com.happy.springboot.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.happy.springboot.admin.model.AdminUserDetails;
import com.happy.springboot.core.dao.AdminPermissionDAO;
import com.happy.springboot.core.dao.AdminRoleDAO;
import com.happy.springboot.core.dao.AdminUserDAO;
import com.happy.springboot.core.model.AdminPermission;
import com.happy.springboot.core.model.AdminRole;
import com.happy.springboot.core.model.AdminUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
* 自定义UserDetailsService
* 
* @author Happy
* @date 2019/11/8
*/
@Component
@Log4j2
public class AdminUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminUserDAO adminUserDAO;

	@Autowired
	private AdminRoleDAO adminRoleDAO;

	@Autowired
	private AdminPermissionDAO adminPermissionDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username={}",username);

		QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username",username);
		// 根据用户名获取用户
		AdminUser adminUser = adminUserDAO.selectOne(queryWrapper);

		if(adminUser == null){
			throw new UsernameNotFoundException("请输入正确的用户名！");
		}

		// 根据用户ID获取用户角色
		List<AdminRole> roleList = adminRoleDAO.listByAdminUserId(adminUser.getId());

		// 获取用户觉得ID列表
		List<Integer> roleIds = roleList.stream().map(e -> e.getId()).collect(Collectors.toList());

		// 根据用户角色和用户ID，获取当前用户所有的权限列表
		List<AdminPermission> permissionList = adminPermissionDAO.listSecurityPermission(roleIds ,adminUser.getId());

		//把用户信息，角色信息和权限给security
		AdminUserDetails adminUserDetails = new AdminUserDetails(adminUser, roleList, permissionList);
		return adminUserDetails;
	}
}
