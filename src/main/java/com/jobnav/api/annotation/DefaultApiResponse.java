package com.jobnav.api.annotation;


import com.jobnav.api.constant.SwaggerConstant;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = SwaggerConstant.SUCCESS_PHRASE),
        @ApiResponse(code = 400, message = SwaggerConstant.INVALID_REQUEST),
        @ApiResponse(code = 401, message = SwaggerConstant.UNAUTHORIZED_REQUEST_PHRASE),
        @ApiResponse(code = 404, message = SwaggerConstant.UN_PROCESSABLE_REQUEST_PHRASE),
        @ApiResponse(code = 403, message = SwaggerConstant.FORBIDDEN_REQUEST_PHRASE),
        @ApiResponse(code = 502, message = SwaggerConstant.GATEWAY_TIMEOUT_REQUEST_PHRASE),
})
public @interface DefaultApiResponse {
}
