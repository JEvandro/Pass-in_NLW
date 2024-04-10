package br.com.evandro.Passin_NLW.domain.attendee.Exceptions;

public class AttendeeAlreadyExistsException extends RuntimeException{

    public AttendeeAlreadyExistsException(String message){
        super(message);
    }

}
