package com.happy.springboot.core.service;

import com.happy.springboot.core.model.AdminRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
public interface AdminRoleService extends IService<AdminRole> {

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
