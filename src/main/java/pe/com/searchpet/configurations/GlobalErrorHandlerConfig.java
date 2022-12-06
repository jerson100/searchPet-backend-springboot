package pe.com.searchpet.configurations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pe.com.searchpet.exceptions.BadRequestException;
import pe.com.searchpet.exceptions.ResourceNotFoundException;
import pe.com.searchpet.models.ApiError;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalErrorHandlerConfig {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleBadRequestException(BadRequestException ex){
        return ApiError.builder()
                .message(ex.getMessage())
                .date(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ApiError.builder()
                .message(ex.getMessage())
                .date(LocalDateTime.now())
                .build();
    }

}
