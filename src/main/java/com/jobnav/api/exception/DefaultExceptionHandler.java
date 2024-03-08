package com.jobnav.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Lists;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import static com.jobnav.api.exception.dto.ErrorCodeEnum.DUPLICATE_RECORD;

@Slf4j
@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DefaultExceptionHandler {

    private static final int SERVICE_CODE = 1000;
    private static final String EXCEPTION_OCCURRED_MSG = "An exception occurred: ";

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleException(final Throwable ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), Collections.emptyList()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WebExchangeBindException.class})
    public ResponseEntity<Object> handleException(final WebExchangeBindException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final List<ErrorResponse.ErrorInfo> errorInfos = Lists.newArrayList();
        for (FieldError error : ex.getFieldErrors()) {
            errorInfos.add(ErrorResponse.ErrorInfo.builder().domain(error.getField())
                    .message(error.getDefaultMessage()).reason(ErrorCodeEnum.INVALID_PARAM.name()).build());
        }
        return new ResponseEntity<>(getErrorResponse(ErrorCodeEnum.INVALID_PARAM.name(), errorInfos), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BusinessServiceException.class})
    public ResponseEntity<Object> handleException(final BusinessServiceException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final List<ErrorResponse.ErrorInfo> errorInfos = Lists.newArrayList();
        errorInfos.add(ErrorResponse.ErrorInfo.builder().reason(ex.getErrorEnum().toString()).message(ex.getMessage()).domain("").build());
        return new ResponseEntity<>(getErrorResponse(ex.getErrorEnum().toString(), errorInfos), ex.getHttpStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleException(final IllegalArgumentException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), Collections.emptyList()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleException(final ConstraintViolationException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final ErrorResponse.ErrorInfo errorInfo = ErrorResponse.ErrorInfo.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), Collections.singletonList(errorInfo)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleException(final DataIntegrityViolationException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final ErrorResponse.ErrorInfo errorInfo = ErrorResponse.ErrorInfo.builder().domain(DUPLICATE_RECORD.name()).build();
        return new ResponseEntity<>(getErrorResponse(ExceptionUtils.getRootCauseMessage(ex), Collections.singletonList(errorInfo)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleException(final ValidationException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final List<ErrorResponse.ErrorInfo> errorInfos = Lists.newArrayList();
        final Throwable throwable = ExceptionUtils.getRootCause(ex);
        if (throwable instanceof BusinessServiceException) {
            return new ResponseEntity<>(getErrorResponse(((BusinessServiceException) throwable).getErrorEnum().toString(), errorInfos), ((BusinessServiceException) throwable).getHttpStatus());
        }
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), errorInfos), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(final MethodArgumentNotValidException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final List<ErrorResponse.ErrorInfo> errorsInfo = Lists.newArrayList();
        ex.getBindingResult().getAllErrors().forEach(objectError -> errorsInfo.add(ErrorResponse.ErrorInfo.builder().domain(objectError.getObjectName())
                .message(objectError.getDefaultMessage()).reason(ErrorCodeEnum.INVALID_PARAM.name()).build()));
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), errorsInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleException(final HttpMessageNotReadableException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final Throwable throwable = ExceptionUtils.getRootCause(ex);
        if (throwable instanceof DateTimeParseException) {
            return new ResponseEntity<>(getErrorResponse(ex.getMessage(), Collections.emptyList()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (throwable instanceof InvalidFormatException) {
            return new ResponseEntity<>(getErrorResponse(ex.getMessage(), Collections.emptyList()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), Collections.emptyList()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidFormatException.class})
    public ResponseEntity<Object> handleException(final InvalidFormatException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), Collections.emptyList()), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(final String message, final List<ErrorResponse.ErrorInfo> errorInfos) {
        return ErrorResponse.builder().message(message).code(SERVICE_CODE).errors(errorInfos).build();
    }
}
