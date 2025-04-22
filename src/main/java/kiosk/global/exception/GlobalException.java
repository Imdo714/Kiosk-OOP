package kiosk.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.servlet.http.HttpServletResponse;
import kiosk.api.ApiResponse;
import kiosk.global.exception.handleException.MenuNotFoundException;
import kiosk.global.exception.handleException.InvalidEntityException;
import kiosk.global.exception.handleException.validEnumTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ApiResponse<Object> handleMenuNotFoundException(MenuNotFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    // OrderEntity 업데이터 검증
    @ExceptionHandler(InvalidEntityException.class)
    public ApiResponse<Object> handleInvalidOrderException(InvalidEntityException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    // Valid 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletResponse response) {

        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(fieldError -> fieldError.getCode().equals("NotNull") ? 0 : 1))
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("잘못된 요청입니다.");

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return ApiResponse.of(HttpStatus.BAD_REQUEST, errorMessage, null);
    }

    // Enum 타입 검증 예외
    @ExceptionHandler(validEnumTypeException.class)
    public ApiResponse<Object> handleValidEnumTypeException(validEnumTypeException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    // 요청 본문(JSON) 객체 바인딩 시 실패하면 발생하는 예외 ex) 존재하지 않는 Enum 값 들어올 때
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Object> handleInvalidEnum(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof MismatchedInputException mismatchedInputException) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST,  mismatchedInputException.getPath().get(0).getFieldName() + " 필드의 값이 잘못되었습니다.", null);
        }
        return ApiResponse.of(HttpStatus.BAD_REQUEST, "확인 할 수 없는 값입니다.", null);
    }

}
