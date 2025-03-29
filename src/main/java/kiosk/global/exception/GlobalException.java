package kiosk.global.exception;

import kiosk.api.ApiResponse;
import kiosk.api.menu.exception.MenuNotFoundException;
import kiosk.global.exception.handleException.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    // 없는 메뉴 예외
    @ExceptionHandler(MenuNotFoundException.class)
    public ApiResponse<Object> handleMenuNotFoundException(MenuNotFoundException e) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    // OrderEntity 업데이터 검증
    @ExceptionHandler(InvalidEntityException.class)
    public ApiResponse<Object> handleInvalidOrderException(InvalidEntityException e) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    // Valid 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationExceptions(MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(fieldError -> fieldError.getCode().equals("NotNull") ? 0 : 1))
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("잘못된 요청입니다.");

        return ApiResponse.of(HttpStatus.BAD_REQUEST, errorMessage, null);
    }
}
