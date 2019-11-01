package com.happy.springboot.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.happy.springboot.common.enums.CodeEnums;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 统一返回值类
 *
 * @author Happy
 * @date 2019/5/17
 */
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = 572243743638605852L;
	private T data;
	private String code;
	private String message;

	public BaseResult(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public BaseResult(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public BaseResult(CodeEnums codeEnums) {
		this.code = codeEnums.getCode();
		this.message = codeEnums.getMessage();
	}

	public BaseResult(CodeEnums codeEnums, T data) {
		this.code = codeEnums.getCode();
		this.message = codeEnums.getMessage();
		this.data = data;
	}
}
