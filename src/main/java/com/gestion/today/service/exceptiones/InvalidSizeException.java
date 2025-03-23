package com.gestion.today.service.exceptiones;

public class InvalidSizeException extends RuntimeException{

    public InvalidSizeException(String message){
        super(message);
    }
}
