package kiosk.global.exception.handleException;

public class MenuNotFoundException extends RuntimeException{

    public MenuNotFoundException(String message) {
        super(message);
    }
}
