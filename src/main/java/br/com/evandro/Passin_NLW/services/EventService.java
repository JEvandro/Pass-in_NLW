package br.com.evandro.Passin_NLW.services;

import br.com.evandro.Passin_NLW.domain.attendee.Attendee;
import br.com.evandro.Passin_NLW.domain.event.Event;
import br.com.evandro.Passin_NLW.domain.event.exceptions.EventFullException;
import br.com.evandro.Passin_NLW.domain.event.exceptions.EventNotFoundException;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeIdDTO;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeRequestDTO;
import br.com.evandro.Passin_NLW.dto.event.EventIdDTO;
import br.com.evandro.Passin_NLW.dto.event.EventRequestDTO;
import br.com.evandro.Passin_NLW.dto.event.EventResponseDTO;
import br.com.evandro.Passin_NLW.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        var event = getEventById(eventId);
        return new EventResponseDTO(event, attendeeService.getAllAttendeesFromEvent(eventId).size());
    }

    public EventIdDTO eventCreate(EventRequestDTO dto){
        Event newEvent = new Event(dto.title(), dto.details(), dto.maximumAttendees());
        newEvent.setSlug(this.createSlug(dto.title()));
        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendedOnEvent(String eventId, AttendeeRequestDTO dto){
        this.attendeeService.verifyAttendeeSubscription(dto.email(), eventId);

        var event = this.getEventById(eventId);
        if(event.getMaximumAttendees() <= attendeeService.getAllAttendeesFromEvent(eventId).size()) throw new EventFullException("Event is Full");

        Attendee newAttendee = new Attendee(dto.name(),dto.email(),event,LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow( () ->
                new EventNotFoundException(eventId)
        );
    }

}
