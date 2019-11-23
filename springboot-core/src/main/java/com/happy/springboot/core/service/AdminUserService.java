package com.happy.springboot.core.service;

import com.happy.springboot.core.form.AdminUserForm;
import com.happy.springboot.core.model.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
public interface AdminUserService extends IService<AdminUser> {

	/**
	 * 注册用户
	 *
	 * @param adminUserForm
	 * @return
	 * @throws  Exception
	 * @author Happy
	 * @date 2019/11/21
	 */
	void register(AdminUserForm adminUserForm) throws Exception;

}
