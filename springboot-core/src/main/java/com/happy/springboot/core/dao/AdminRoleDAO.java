package com.happy.springboot.core.dao;

import com.happy.springboot.core.model.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
public interface AdminRoleDAO extends BaseMapper<AdminRole> {

	/**
	 * 根据用户ID获取用户角色列表
	 *
	 * @param adminUserId
	 * @return java.util.List<com.happy.springboot.core.model.AdminRole>
	 * @author Happy
	 * @date 2019/11/19
	 */
	List<AdminRole> listByAdminUserId(Integer adminUserId);
}
