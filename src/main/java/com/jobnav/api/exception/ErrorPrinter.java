package com.jobnav.api.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public interface ErrorPrinter extends Serializable {

    HttpStatus getHttpStatus();

}
