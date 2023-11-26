package com.pavila.TaskProyect.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ProblemDetail handlerUnknownClientesException(ObjectNotFoundException e, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Backend-Message");
        problemDetail.setType(URI.create("ObjectNotFoundException"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("Method", request.getMethod());

        return problemDetail;
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ProblemDetail handlerInvalidPasswordExceptionException(InvalidPasswordException e, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Backend-Message");
        problemDetail.setType(URI.create("InvalidPasswordException"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("Method", request.getMethod());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest w, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, w.getDescription(false));

        String mensaje = "Error en el argumento '" + e.getName() + "': Valor incorrecto '" + e.getValue() + "'. Debe ser de tipo " + Objects.requireNonNull(e.getRequiredType()).getSimpleName() + " (número).";
        problemDetail.setDetail(mensaje);
        problemDetail.setType(URI.create("MethodArgumentTypeMismatchException"));
        problemDetail.setTitle("Backend-Message");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("Method", request.getMethod());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Backend-Message");
        problemDetail.setDetail(errorMap.toString());
        problemDetail.setType(URI.create("MethodArgumentNotValidException"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("Method", request.getMethod());

        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class) //esta se utiliza para el manejo de errores de métodos seguros
    public ProblemDetail handlerAccessDeniedException(HttpServletRequest request,
                                                          AccessDeniedException exception){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        problemDetail.setTitle("backend-Message");
        problemDetail.setType(URI.create("AccessDeniedException"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("method", request.getMethod());
        problemDetail.setProperty("message","Acceso denegado. No tienes los permisos necesarios para acceder a esta función. " +
                "Por favor, contacta al administrador si crees que esto es un error.");


        return problemDetail;
    }
    @ExceptionHandler(Exception.class)
    public ProblemDetail handlerGenericException(HttpServletRequest request, Exception exception) {


        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        problemDetail.setTitle("Backend-Message");
        problemDetail.setType(URI.create("Exception"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("Method", request.getMethod());
        problemDetail.setProperty("message","Error interno en el servidor, vuelva a intentarlo");

        return problemDetail;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handlerMethodNotAllowedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Backend-Message");
        problemDetail.setType(URI.create("MethodNotAllowedException"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("Method", request.getMethod());

        return problemDetail;
    }
}
