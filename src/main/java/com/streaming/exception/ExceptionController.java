package com.streaming.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiException> handleRuntimeException(Exception exc) {
        log.error("Handling exception: {}", exc.getMessage(), exc);
        return buildResponseEntity(
                new ApiException("An unexpected error occurred. Please try again later.", INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiException> handleTypeMismatchEXCEPTION(MethodArgumentTypeMismatchException exc) {
        String error = exc.getName() + " should be of type " + exc.getRequiredType();
        return buildResponseEntity(new ApiException(error, BAD_REQUEST));
    }

    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<ApiException> handleVideoNotFoundException(VideoNotFoundException exc) {
        log.error("Handling video not found exception: {}", exc.getMessage(), exc);
        return buildResponseEntity(new ApiException(exc.getMessage(), NOT_FOUND));
    }

    private ResponseEntity<ApiException> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

}
