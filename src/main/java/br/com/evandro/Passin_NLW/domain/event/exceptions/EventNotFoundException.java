package br.com.evandro.Passin_NLW.domain.event.exceptions;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String message){
        super("Event not found with id: " + message);
    }

}
