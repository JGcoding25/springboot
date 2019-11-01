package com.happy.springboot.common.handler;

import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.exception.CustomRuntimeException;
import com.happy.springboot.common.model.BaseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局的异常处理类
 *
 * @author Happy
 * @date 2019/6/14
*/
@RestControllerAdvice(annotations={RestController.class, Controller.class})
@Log4j2
public class SpringExceptionHandler {

	/**
	 * 请求参数错误异常捕获
	 *
	 * @param e
	 * @return com.happy.springboot.common.model.BaseResult<java.lang.String>
	 * @author Happy
	 * @date 2019/11/1
	 */
	@ExceptionHandler(value={BindException.class})
	@ResponseBody
	@ResponseStatus(value= HttpStatus.BAD_REQUEST)
	public BaseResult<String> badRequest(BindException e){
		log.error("occurs error when execute method ,message {}",e.getMessage());
		return new BaseResult<>(CodeEnums.BAD_REQUEST);
	}

	/**
	 * 404错误异常捕获
	 *
	 * @param e
	 * @return com.happy.springboot.common.model.BaseResult<java.lang.String>
	 * @author Happy
	 * @date 2019/11/1
	 */
	@ExceptionHandler(value={NoHandlerFoundException.class})
	@ResponseBody
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public BaseResult<String> badRequestNotFound(BindException e){
		log.error("occurs error when execute method ,message {}",e.getMessage());
		return new BaseResult<>(CodeEnums.PATH_NOT_FOUND);
	}

	/**
	 * 自定义异常捕获
	 *
	 * @param exception
	 * @param request
	 * @return com.happy.springboot.common.model.BaseResult<T>
	 * @author Happy
	 * @date 2019/11/1
	 */
	@ExceptionHandler(value={CustomRuntimeException.class})
	@ResponseBody
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public <T> BaseResult<T> sendError(CustomRuntimeException exception, HttpServletRequest request){
		String requestURI = request.getRequestURI();
		log.error("occurs error when execute url ={} ,message {}",requestURI,exception.getMessage());
		return new BaseResult<>(exception.getCode(),exception.getErrorMsg());
	}

}
