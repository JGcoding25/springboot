package com.happy.springboot.core.service.impl;

import com.happy.springboot.core.model.AdminPermission;
import com.happy.springboot.core.dao.AdminPermissionDAO;
import com.happy.springboot.core.service.AdminPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
@Service
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionDAO, AdminPermission> implements AdminPermissionService {

	@Autowired
	private AdminPermissionDAO adminPermissionDAO;

	@Override
	public List<AdminPermission> listSecurityPermission(List<Integer> roleIds, Integer adminUserId) {
		return adminPermissionDAO.listSecurityPermission(roleIds,adminUserId);
	}
}
