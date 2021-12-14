package com.cp.bootmongo.wrapper;

import com.cp.bootmongo.dto.common.APIResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;


@CommonsLog
@Component
public class ApiResponseWrapper {

    public <T> APIResponse<T> wrapResponse(int statusCode, Boolean success, String message, T responseObj) {
        APIResponse<T> response = new APIResponse<>();
        if (responseObj != null) {
            response.setBody(responseObj);
        }
        response.setSuccess(success);
        response.setStatus(statusCode);
        response.setMessage(message);
        return response;
    }


}
