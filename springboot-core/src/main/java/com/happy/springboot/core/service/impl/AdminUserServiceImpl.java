package com.happy.springboot.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.happy.springboot.common.util.RsaUtils;
import com.happy.springboot.core.constant.Constants;
import com.happy.springboot.core.dao.AdminUserDAO;
import com.happy.springboot.core.form.AdminUserForm;
import com.happy.springboot.core.model.AdminUser;
import com.happy.springboot.core.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDAO, AdminUser> implements AdminUserService {

	@Autowired
	private AdminUserDAO adminUserDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void register(AdminUserForm adminUserForm) throws Exception {
		AdminUser adminUser = new AdminUser();
		adminUser.setUsername(adminUserForm.getUsername());
		// 对前台传输的密码进行解密
		String decryptPassword = RsaUtils.decryptByPrivateKey(adminUser.getPassword(), Constants.LOGIN_PRIVATE_KEY,null);
		// 对解密后的明文密码进行Security加密存入数据库
		adminUser.setPassword(passwordEncoder.encode(decryptPassword));
		adminUser.setPhoneNumber(adminUserForm.getPhoneNumber());
		adminUser.setNickName(adminUserForm.getNickName());
		adminUser.setRealName(adminUserForm.getRealName());
		adminUser.setAge(adminUserForm.getAge());
		adminUser.setStatus(adminUserForm.getStatus());
		adminUser.setLoginTime(LocalDateTime.now());
		adminUser.setCreateTime(LocalDateTime.now());
		adminUser.setUpdateTime(LocalDateTime.now());
		adminUserDAO.insert(adminUser);
	}
}
