package com.happy.springboot.core.service;

import com.happy.springboot.core.model.AdminPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
public interface AdminPermissionService extends IService<AdminPermission> {

	/**
	 * 根据角色ID和用户ID获取权限列表
	 *
	 * @param roleIds
	 * @param adminUserId
	 * @return java.util.List<com.happy.springboot.core.model.AdminPermission>
	 * @author Happy
	 * @date 2019/11/19
	 */
	List<AdminPermission> listSecurityPermission(List<Integer> roleIds,Integer adminUserId);
}
