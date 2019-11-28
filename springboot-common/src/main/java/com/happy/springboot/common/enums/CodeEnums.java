package com.happy.springboot.common.enums;

/**
 * 接口返回统一错误码
 *
 * @author Happy
 * @date 2019/5/16
 */
public enum CodeEnums {
	/**
	 * 成功返回值
	 */
	SUCCESS("Success","成功"),
	/**
	 * 错误
	 */
	ERROR("Error","发生错误"),
	/**
	 * 系统异常返回值
	 */
	SYSTEM_ERROR("SystemError", "系统异常"),
	/**
	 * 错误的请求参数返回值
	 */
	BAD_REQUEST("BadRequest", "错误的请求参数"),
	/**
	 * 找不到请求路径返回值
	 */
	PATH_NOT_FOUND("PathNotFound", "找不到请求路径"),
	/**
	 * 网络连接请求失败返回值
	 */
	CONNECTION_ERROR("ConnectionError", "网络连接请求失败"),
	/**
	 * 数据库异常返回值
	 */
	DATABASE_ERROR("DataBaseError", "数据库异常"),
	/**
	 * 重复注册返回值
	 */
	REPEAT_REGISTER("RepeatRegister", "重复注册"),
	/**
	 * 用户不存在返回值
	 */
	NO_USER_EXIST("NoUserExist", "用户不存在"),
	/**
	 * 密码错误返回值
	 */
	INVALID_PASSWORD("InvalidPassword", "密码错误"),
	/**
	 * 无效参数返回值
	 */
	INVALID_ARGUMENT("InvalidArgument", "无效参数"),
	/**
	 * 缺少参数返回值
	 */
	MISSING_PARAMETER("MissingParameter", "缺少参数"),
	/**
	 * 注册用户失败返回值
	 */
	REGISTER_FAILED("RegisterFailed", "注册用户失败"),
	/**
	 * 登录成功返回值
	 */
	LOGIN_SUCCESS("LoginSuccess", "登录成功"),
	/**
	 * 登录失败返回值
	 */
	LOGIN_FAILURE("LoginFailure", "登录失败"),
	/**
	 * 登出成功返回值
	 */
	LOGOUT_SUCCESS("LogoutSuccess", "登出成功"),
	/**
	 * 登出失败返回值
	 */
	LOGOUT_FAILURE("LogoutFailure", "登出失败"),
	/**
	 * 用户未登录返回值
	 */
	NO_LOGIN("NoLogin", "用户未登录"),
	/**
	 * 没有权限返回值
	 */
	NO_PERMISSION("NoPermission", "没有权限"),
	/**
	 * token认证失败
	 */
	TOKEN_AUTHENTICATION_FAILURE("tokenAuthenticationFailure", "token认证失败");

	private String code;
	private String message;

	CodeEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
