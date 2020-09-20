package com.jeffdev.prueba.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Integer NOT_FOUND = -1;
    private final Integer BAD_REQUEST = 0;
    private final Integer UNAUTHORIZED = 401;
    private final Integer OK = 1;


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(HttpServletRequest req, ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(NOT_FOUND);
        response.setMessage("No se encontro");
        response.setBackendMessage(ex.getMessage());
        response.setHttpStatus(HttpStatus.NOT_FOUND.value());
        Logger logger = LogManager.getLogger(req.getRequestURI());
        logger.error(construirMensaje(response));
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customException(HttpServletRequest req, CustomException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(BAD_REQUEST);
        response.setMessage("Error al realizar la acción");
        response.setBackendMessage(ex.getMessage());
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        Logger logger = LogManager.getLogger(req.getRequestURI());
        logger.error(construirMensaje(response));
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }

    private String construirMensaje(ExceptionResponse response) {
        return "Code App: " + response.getCode() + " -- Message:" + response.getMessage() + " -- BackendMessage:" + response.getBackendMessage() + " -- HttpStatus:" + response.getHttpStatus();
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ExceptionResponse> deleteException(HttpServletRequest req, DeleteException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(OK);
        response.setMessage("Error al realizar la acción");
        response.setBackendMessage(ex.getMessage());
        response.setHttpStatus(HttpStatus.OK.value());
        Logger logger = LogManager.getLogger(req.getRequestURI());
        logger.error(construirMensaje(response));
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(UNAUTHORIZED);
        response.setMessage("Body no valido");
        response.setBackendMessage(ex.getMessage());
        response.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.UNAUTHORIZED);
    }

}
