package ru.job4j.url.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class UrlHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlHandler.class.getSimpleName());
    private final ObjectMapper objectMapper;

    public UrlHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Нарушение уникальности ключа");
            put("details", e.getMessage());
        }}));
        LOGGER.error(e.getMessage());
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public void handleExceptionElement(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Значение не существует");
            put("details", e.getMessage());
        }}));
        LOGGER.error(e.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public void handleExceptionConstrains(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Значение не существует");
            put("details", e.getMessage());
        }}));
        LOGGER.error(e.getMessage());
    }
}