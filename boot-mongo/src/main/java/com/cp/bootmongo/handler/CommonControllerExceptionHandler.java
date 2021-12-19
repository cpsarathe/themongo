package com.cp.bootmongo.handler;

import com.cp.bootmongo.constants.ResponseStatusEnum;
import com.cp.bootmongo.dto.common.APIResponse;
import com.cp.bootmongo.wrapper.ApiResponseWrapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
@CommonsLog
public class CommonControllerExceptionHandler {

    @Autowired
    private ApiResponseWrapper apiResponseWrapper;

    @ExceptionHandler(Exception.class)
    public final APIResponse<String> handleException(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        String msg = "Error while processing your request,ref#" + uuid;
        log.error(msg, ex);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SYSTEM_ERROR,
                msg, null);
    }
}
