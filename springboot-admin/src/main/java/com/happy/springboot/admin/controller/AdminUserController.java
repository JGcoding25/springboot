package com.happy.springboot.admin.controller;
import java.time.LocalDateTime;


import com.alibaba.fastjson.JSONObject;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.model.BaseResult;
import com.happy.springboot.core.form.AdminUserForm;
import com.happy.springboot.core.model.AdminUser;
import com.happy.springboot.core.service.AdminUserService;
import com.happy.springboot.core.vo.AdminUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Happy
 * @since 2019-11-12
 */
@RestController
@RequestMapping("/adminUser")
@Api(tags = "后台用户控制类")
@Log4j2
public class AdminUserController {

	@Autowired
	private AdminUserService adminUserService;

	@ApiOperation(value = "根据ID获取用户信息")
	@GetMapping(value = "/{id}")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true)
	public BaseResult<AdminUserVO> getUserById(@PathVariable Integer id){
		AdminUser adminUser = adminUserService.getById(id);
		AdminUserVO adminUserVO = new AdminUserVO();
		BeanUtils.copyProperties(adminUser,adminUserVO);

		return new BaseResult<>(CodeEnums.SUCCESS,adminUserVO);
	}

	/**
	 * 用户注册接口
	 *
	 * @param adminUserForm
	 * @return com.happy.springboot.common.model.BaseResult
	 * @author Happy
	 * @date 2019/11/21
	 */
	@PostMapping(value = "/register")
	@ApiOperation(value = "用户注册接口")
	public BaseResult register(AdminUserForm adminUserForm){
		try {
			adminUserService.register(adminUserForm);
		} catch (Exception e) {
			log.error("用户注册异常",e);
			return new BaseResult(CodeEnums.REGISTER_FAILED);
		}
		return new BaseResult(CodeEnums.SUCCESS);
	}


	/**
	 * 用户登录接口
	 * @param username 用户名
	 * @param password 密码密文
	 * @return com.cityhouse.cityhousecommon.common.ResultBean<com.alibaba.fastjson.JSONObject>
	 * @author Happy
	 * @date 2019/6/14
	 */
	@PostMapping(value = "/login")
	@ApiOperation(value = "登录接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名" , required = true),
			@ApiImplicitParam(name = "password", value = "密码" , required = true)
	})
	public BaseResult<JSONObject> login(String username, String password){
		JSONObject object = new JSONObject();
		log.debug("username={},password={}",username,password);
		return new BaseResult<>(CodeEnums.SUCCESS,object);
	}
}
