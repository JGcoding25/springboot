package com.happy.springboot.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.happy.springboot.common.enums.CodeEnums;

import java.io.Serializable;

/**
 * 统一返回值类
 *
 * @author Happy
 * @date 2019/5/17
 */
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
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

	@Override
	public String toString() {
		return "BaseResult{" +
				", data=" + data +
				", code='" + code + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
