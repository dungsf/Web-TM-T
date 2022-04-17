package com.rikkeisoft.canifashop.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(RuntimeException.class)
    public String checkException(RuntimeException e)
    {
        if(e instanceof BadRequestException)
        {
            return "/index";
        }
        else if(e instanceof UnauthorizedException)
        {
            return null;
        }

        return null;
    }
}
