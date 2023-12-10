package kz.kbtu.phonebook.exceptions;

public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String ex){
        super(ex);
    }
}
