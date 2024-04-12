package br.com.evandro.Passin_NLW.config;

import br.com.evandro.Passin_NLW.domain.attendee.Exceptions.AttendeeAlreadyExistsException;
import br.com.evandro.Passin_NLW.domain.attendee.Exceptions.AttendeeNotFoundException;
import br.com.evandro.Passin_NLW.domain.checkin.exceptions.CheckInAlreadyExistsException;
import br.com.evandro.Passin_NLW.domain.event.exceptions.EventFullException;
import br.com.evandro.Passin_NLW.domain.event.exceptions.EventNotFoundException;
import br.com.evandro.Passin_NLW.dto.general.ErrorResponseDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    private MessageSource messageSource;

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity HandlerEventNotFound(EventNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity HandlerAttendeeNotFound(AttendeeNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(AttendeeAlreadyExistsException.class)
    public ResponseEntity HandlerAttendeeAlreadyExists(AttendeeAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity HandlerCheckInAlreadyExists(CheckInAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> HandlerEventFull(EventFullException e){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handlerEventAlreadyExists(DataIntegrityViolationException e){
        String message = e.getMessage();
        System.out.println(e.getMessage().replaceAll(message, "Jose"));
        return null;
    }

}
