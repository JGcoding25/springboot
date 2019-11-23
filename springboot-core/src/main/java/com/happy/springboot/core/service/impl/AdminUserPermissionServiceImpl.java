package com.happy.springboot.core.service.impl;

import com.happy.springboot.core.model.AdminUserPermission;
import com.happy.springboot.core.dao.AdminUserPermissionDAO;
import com.happy.springboot.core.service.AdminUserPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author Happy
 * @since 2019-11-19
 */
@Service
public class AdminUserPermissionServiceImpl extends ServiceImpl<AdminUserPermissionDAO, AdminUserPermission> implements AdminUserPermissionService {

}
