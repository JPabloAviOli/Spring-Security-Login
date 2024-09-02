package com.pavila.SecureLogin.exception;

import com.pavila.SecureLogin.model.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ObjectNotFoundException.class,
            MethodArgumentTypeMismatchException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            AccessDeniedException.class,
            InvalidPasswordException.class,
            BadCredentialsException.class,
            Exception.class
    })
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception exception,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {

        ZoneId zoneId = ZoneId.of("America/Mexico_City");
        LocalDateTime timestamp = LocalDateTime.now(zoneId);

        if (exception instanceof ObjectNotFoundException objectNotFoundException) {
            return this.handleObjectNotFoundException(objectNotFoundException, request, timestamp);

        } else if (exception instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return this.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, request, timestamp);
        } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            return this.handleMethodArgumentNotValidException(methodArgumentNotValidException, request, timestamp);
        } else if (exception instanceof HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
            return this.handleHttpRequestMethodNotSupportedException(httpRequestMethodNotSupportedException, request, timestamp);
        } else if (exception instanceof HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
            return this.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, request, timestamp);
        } else if (exception instanceof HttpMessageNotReadableException httpMessageNotReadableException) {
            return this.handleHttpMessageNotReadableException(httpMessageNotReadableException, request, timestamp);
        } else if (exception instanceof AccessDeniedException accessDeniedException) {
            return this.handleAccessDeniedException(accessDeniedException, request, timestamp);
        } else if (exception instanceof InvalidPasswordException invalidPasswordException) {
            return this.handleInvalidPasswordException(invalidPasswordException, request, timestamp);
        } else if (exception instanceof BadCredentialsException badCredentialsException) {
            return this.handleBadCredentialsException(badCredentialsException, request, timestamp);
        }else {
            return this.handleException(exception, request, timestamp);

        }

    }


    private ResponseEntity<ApiErrorResponse> handleObjectNotFoundException(ObjectNotFoundException e,
                                                                           HttpServletRequest request,
                                                                           LocalDateTime timestamp) {
        int httpStatus = HttpStatus.NOT_FOUND.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The requested resource could not be found. Please check the URL or try again.",
                e.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);

    }


    private ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e,
            HttpServletRequest request,
            LocalDateTime timestamp) {

        int httpStatus = HttpStatus.BAD_REQUEST.value();
        Object valueRejected = e.getValue();
        String propertyName = e.getName();

        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid Request: The provided value " + valueRejected + " does not have the expected type for the " + propertyName,
                e.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e,
            HttpServletRequest request,
            LocalDateTime timestamp) {

        int httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Method Not Allowed. Please verify the HTTP method used in your request and ensure it is correct for the requested resource.",
                e.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);

    }
        private ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException (
                MethodArgumentNotValidException e,
                HttpServletRequest request,
                LocalDateTime timestamp){

            int httpStatus = HttpStatus.BAD_REQUEST.value();

            List<ObjectError> errors = e.getAllErrors();
            List<String> details = errors.stream()
                    .map(error -> {
                        if (error instanceof FieldError fieldError) {
                            return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
                        }
                        return error.getDefaultMessage();
                    })
                    .collect(toList());

            String errorMessage = "The request contains invalid or incomplete parameters. " +
                    "Please verify and provide the required information before trying again.";

            ApiErrorResponse apiError = new ApiErrorResponse(
                    httpStatus,
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    errorMessage,
                    "Invalid input detected. Please review the details and correct any errors before resubmitting.",
                    timestamp,
                    details
            );

            return ResponseEntity.status(httpStatus).body(apiError);

        }

    private ResponseEntity<ApiErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, HttpServletRequest request, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Unsupported Media Type: The server is unable to process the requested entity in the format provided in the request. " +
                        "Supported media types are: " + httpMediaTypeNotSupportedException.getSupportedMediaTypes() + " and you send: " + httpMediaTypeNotSupportedException.getContentType() ,
                httpMediaTypeNotSupportedException.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);

    }

    private ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request, LocalDateTime timestamp) {

        int httpStatus = HttpStatus.BAD_REQUEST.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops! Error reading the HTTP message body. " +
                         "Make sure the request is correctly formatted and contains valid data.",
                httpMessageNotReadableException.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);

    }

    //esta se utiliza para el manejo de errores de métodos seguros
    private ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, LocalDateTime timestamp){
        int httpStatus = HttpStatus.FORBIDDEN.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Access denied. You do not have the necessary permissions to access this function. " +
                        "Please contact the administrator if you believe this is an error.",
                e.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiErrorResponse> handleInvalidPasswordException(InvalidPasswordException e, HttpServletRequest request, LocalDateTime timestamp) {

        int httpStatus = HttpStatus.BAD_REQUEST.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid password. Please ensure the password meets the required criteria.",
                e.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);

    }

    private ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request, LocalDateTime timestamp) {

        int httpStatus = HttpStatus.UNAUTHORIZED.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid credentials. Please check your email and password.",
                e.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiErrorResponse> handleException(Exception exception, HttpServletRequest request,LocalDateTime timestamp) {
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ApiErrorResponse apiError = new ApiErrorResponse(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Something went wrong on our server. Please try again later.",
                exception.getMessage(),
                timestamp,
                new ArrayList<>()
        );

        return ResponseEntity.status(httpStatus).body(apiError);

    }


}
