package com.DiscussionCommunity.exception;

import com.DiscussionCommunity.domain.dto.ExceptionResponse;
import com.DiscussionCommunity.exception.custom.InternalServerException;
import com.DiscussionCommunity.exception.custom.NotAcceptableException;
import com.DiscussionCommunity.exception.custom.NotFoundException;
import com.DiscussionCommunity.exception.custom.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e, HttpServletRequest request){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionResponse response = new ExceptionResponse(false, request.getMethod(), request.getRequestURI(), notFound, e.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(response, notFound);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerException(InternalServerException e, HttpServletRequest request){
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse response = new ExceptionResponse(false, request.getMethod(), request.getRequestURI(), internalServerError, e.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(response, internalServerError);
    }

    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<ExceptionResponse> handleNotAcceptableException(NotAcceptableException e, HttpServletRequest request){
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ExceptionResponse response = new ExceptionResponse(false, request.getMethod(), request.getRequestURI(), notAcceptable, e.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(response, notAcceptable);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ExceptionResponse response = new ExceptionResponse(false, request.getMethod(), request.getRequestURI(), unauthorized, e.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(response, unauthorized);
    }
}
