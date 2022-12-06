package pe.com.searchpet.configurations;

import jakarta.validation.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pe.com.searchpet.models.ApiError;
import pe.com.searchpet.utils.UtilError;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {
    private final Logger LOG = LoggerFactory.getLogger(ErrorHandlerConfig.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        /*if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException iex = (InvalidFormatException) ex.getCause();
            List<Map<String, String>> errors = new ArrayList<>();
            iex.getPath().forEach(reference -> {
                Map<String, String> error = new HashMap<>();
                error.put(reference.getFieldName(), iex.getOriginalMessage());
                errors.add(error);
            });
            return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
        }*/
        ApiError error = ApiError.builder().message(ex.getMessage()).build();
        return super.handleExceptionInternal(ex, error,headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        Map<String, List<FieldError>> map = new TreeMap<>();
        Map<String, List<String>> map_response = new TreeMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if(map.containsKey(error.getField())){
                List<FieldError> list = map.get(error.getField());
                List<String> StringList =map_response.get(error.getField());
                list.add(error);
                StringList.add((error.getDefaultMessage()));
            }else{
                List<FieldError> newList = new ArrayList<>();
                List<String> newStringList = new ArrayList<>();
                newList.add(error);
                newStringList.add(error.getDefaultMessage());
                map.put(error.getField(), newList);
                map_response.put(error.getField(), newStringList);
            }
        }
        List<String> list = map.keySet().stream().toList();
        List<FieldError> fisrtErrors = map.get(list.get(list.size() - 1));
        fisrtErrors.sort(UtilError.FIELD_ORDER_COMPARATOR);
        StringBuilder message = new StringBuilder();
        for(FieldError error:fisrtErrors){
            message.append(error.getDefaultMessage());
            message.append(", ");
        }
        message.replace(message.length() - 2, message.length(), "");
        ApiError error = ApiError.builder().message(message.toString()).date(LocalDateTime.now()).errorStack(map_response).build();
        return super.handleExceptionInternal(ex,error, headers, status, request);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();
        return ApiError.builder().message(error).build();
    }

    @ExceptionHandler({ ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleConstraintViolationException(ConstraintViolationException ex){
        return ApiError.builder().message(ex.getMessage()).build();
    }

}
