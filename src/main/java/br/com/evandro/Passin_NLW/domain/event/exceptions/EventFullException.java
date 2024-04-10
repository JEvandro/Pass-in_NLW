package br.com.evandro.Passin_NLW.domain.event.exceptions;

public class EventFullException extends RuntimeException{

    public EventFullException(String message){
        super(message);
    }

}
