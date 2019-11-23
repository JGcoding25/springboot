package com.happy.springboot.admin.model;

import com.happy.springboot.core.model.AdminPermission;
import com.happy.springboot.core.model.AdminRole;
import com.happy.springboot.core.model.AdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
* 
* 
* @author Happy
* @date 2019/11/9
*/
public class AdminUserDetails implements UserDetails {

	private AdminUser adminUser;
	private List<AdminRole> adminRoles;
	private List<AdminPermission> adminPermissions;
	public AdminUserDetails(AdminUser adminUser,List<AdminRole> adminRoles, List<AdminPermission> adminPermissions) {
		this.adminUser = adminUser;
		this.adminRoles = adminRoles;
		this.adminPermissions = adminPermissions;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return adminUser.getPassword();
	}

	@Override
	public String getUsername() {
		return adminUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return adminUser.getDeleted() == true ? false : true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
