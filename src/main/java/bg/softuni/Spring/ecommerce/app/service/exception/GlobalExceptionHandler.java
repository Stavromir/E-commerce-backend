package bg.softuni.Spring.ecommerce.app.service.exception;

import bg.softuni.Spring.ecommerce.app.model.dto.errorDto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationException(ObjectNotFoundException exception) {
        return new ErrorResponseDto()
                .setMessage(exception.getMessage())
                .setSuccess(Boolean.FALSE);
    }

}
