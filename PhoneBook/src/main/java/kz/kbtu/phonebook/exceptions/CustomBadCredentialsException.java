package kz.kbtu.phonebook.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class CustomBadCredentialsException extends BadCredentialsException {
    public CustomBadCredentialsException(String message){
        super(message);
    }
}
