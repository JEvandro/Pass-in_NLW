package br.com.evandro.Passin_NLW.services;

import br.com.evandro.Passin_NLW.domain.attendee.Attendee;
import br.com.evandro.Passin_NLW.domain.attendee.Exceptions.AttendeeAlreadyExistsException;
import br.com.evandro.Passin_NLW.domain.attendee.Exceptions.AttendeeNotFoundException;
import br.com.evandro.Passin_NLW.domain.checkin.Checkin;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeDetails;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeListResponseDTO;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeBadgeDTO;
import br.com.evandro.Passin_NLW.repositories.AttendeeRepository;
import br.com.evandro.Passin_NLW.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String event_id){
        return this.attendeeRepository.findByEventId(event_id);
    }

    public AttendeeListResponseDTO getEventsAttendees(String event_id){
        var attendeeList = this.getAllAttendeesFromEvent(event_id);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            var checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(Checkin::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
        var attendee = this.attendeeRepository.findByEmailAndEventId(email, eventId);
        if(attendee.isPresent()) throw new AttendeeAlreadyExistsException("Attendee is already registered!");
    }

    public Attendee registerAttendee(Attendee attendee){
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        var attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        return new AttendeeBadgeResponseDTO(new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri ,attendee.getEvent().getId()));
    }

    private Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() ->
                new AttendeeNotFoundException("Attendee not found with id: " + attendeeId)
        );
    }

    public void checkInAttendee(String attendeeId){
        var attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

}
