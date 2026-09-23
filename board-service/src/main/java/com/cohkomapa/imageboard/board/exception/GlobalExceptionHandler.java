package com.cohkomapa.imageboard.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handle(ResourceNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Entity not found");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ProblemDetail handle(InvalidRequestException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid request");
        problemDetail.setDetail(ex.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(InvalidMediaException.class)
    public ProblemDetail handle(InvalidMediaException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid media");
        problemDetail.setDetail("Some media cannot be attached to the post");
        problemDetail.setProperty(
                "invalidMediaIds",
                ex.getInvalidMediaIds()
        );

        return problemDetail;
    }
}