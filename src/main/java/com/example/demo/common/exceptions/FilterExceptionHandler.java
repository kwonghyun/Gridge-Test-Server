package com.example.demo.common.exceptions;


import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

@Slf4j

public class FilterExceptionHandler {
    public static void jwtExceptionHandler(HttpServletResponse response, BaseResponseStatus status) {
        response.setStatus(status.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), new BaseResponse(status));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
