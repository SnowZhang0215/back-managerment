package io.snow.springcloud.userservice.controller.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class InternalServerErrorException extends HttpServerErrorException {

    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(String message) {
        super(HttpStatus.EXPECTATION_FAILED, message);
    }
}
