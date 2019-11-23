package com.happy.springboot.core.service.impl;

import com.happy.springboot.core.model.AdminRole;
import com.happy.springboot.core.dao.AdminRoleDAO;
import com.happy.springboot.core.service.AdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleDAO, AdminRole> implements AdminRoleService {

	@Autowired
	private AdminRoleDAO adminUserDAO;

	@Override
	public List<AdminRole> listByAdminUserId(Integer adminUserId) {
		return adminUserDAO.listByAdminUserId(adminUserId);
	}
}
