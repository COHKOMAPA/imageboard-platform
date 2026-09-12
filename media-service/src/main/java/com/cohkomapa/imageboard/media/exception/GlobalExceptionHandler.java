package com.cohkomapa.imageboard.media.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handle(ResourceNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Entity not found");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    @ExceptionHandler(ObjectStorageException.class)
    public ProblemDetail handle(ObjectStorageException ex) {

        log.error("Object storage error: {}", ex.getMessage(), ex);

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Object storage error");
        problem.setDetail("An error occurred while processing the request");

        return problem;
    }

    @ExceptionHandler(InvalidMediaException.class)
    public ProblemDetail handle(InvalidMediaException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid media");
        problem.setDetail(ex.getMessage());

        return problem;
    }
}