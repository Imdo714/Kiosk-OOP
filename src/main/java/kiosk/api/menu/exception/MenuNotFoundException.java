package kiosk.api.menu.exception;

public class MenuNotFoundException extends RuntimeException{

    public MenuNotFoundException(String message) {
        super(message);
    }
}
