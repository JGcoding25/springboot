package com.happy.springboot.core.dao;

import com.happy.springboot.core.model.AdminPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
public interface AdminPermissionDAO extends BaseMapper<AdminPermission> {

	/**
	 * 根据角色ID和用户ID获取权限列表
	 *
	 * @param roleIds
	 * @param adminUserId
	 * @return void
	 * @author Happy
	 * @date 2019/11/19
	 */
	List<AdminPermission> listSecurityPermission(@Param("roleIds") List<Integer> roleIds,@Param("adminUserId") Integer adminUserId);
}
