package br.com.evandro.Passin_NLW.domain.attendee.Exceptions;

public class AttendeeNotFoundException extends RuntimeException{

    public AttendeeNotFoundException(String message){
        super(message);
    }

}
