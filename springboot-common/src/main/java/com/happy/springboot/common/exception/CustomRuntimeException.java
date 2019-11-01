package com.happy.springboot.common.exception;

import com.happy.springboot.common.enums.CodeEnums;
import lombok.Getter;
import lombok.Setter;

/**
 * 异常类
 *
 * @author Happy
 * @date 2019/6/4
 */
@Getter
@Setter
public class CustomRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 86636793127524309L;

	/** 自定义异常码 */
	private String code;

	/** 自定义异常反馈信息 */
	private String message;

	/** 打印出的日志异常信息 */
	private String errorMsg;


	public CustomRuntimeException() {
		super();
	}

	public CustomRuntimeException(String errorMsg) {
		super(errorMsg);
	}

	public CustomRuntimeException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public CustomRuntimeException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
	}

	public CustomRuntimeException(CodeEnums codeEnums) {
		super();
		this.code = codeEnums.getCode();
		this.message = codeEnums.getMessage();
	}

	public CustomRuntimeException(CodeEnums codeEnums, String errorMsg) {
		super();
		this.code = codeEnums.getCode();
		this.message = codeEnums.getMessage();
		this.errorMsg = errorMsg;
	}
}