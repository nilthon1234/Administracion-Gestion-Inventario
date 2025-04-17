package com.gestion.today.service.exceptiones;

import org.aspectj.bridge.IMessage;

public class AmortizationExceededException extends RuntimeException{

    public AmortizationExceededException(String message) {
        super(message);
    }

}
