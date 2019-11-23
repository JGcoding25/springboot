package com.happy.springboot.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.happy.springboot.core.dao.AdminUserRoleDAO;
import com.happy.springboot.core.model.AdminUserRole;
import com.happy.springboot.core.service.AdminUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
@Service
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleDAO, AdminUserRole> implements AdminUserRoleService {

}
