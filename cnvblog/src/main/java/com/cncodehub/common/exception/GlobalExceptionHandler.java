package com.cncodehub.common.exception;

import com.cncodehub.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice //@RestControllerAdvice都是对Controller进行增强的，可以全局捕获spring mvc抛的异常
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401 没有权限
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e){
        log.error("没有权限");
        return Result.succ(401,e.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.error("运行时异常");
        return Result.succ400(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        log.error("实体校验异常");
        BindingResult bindingResult = e.getBindingResult();
        //这里只让它返回第一个错误
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.succ(400,objectError.getDefaultMessage(),null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("断言异常");
        return Result.succ(400,e.getMessage(),null);
    }

}
