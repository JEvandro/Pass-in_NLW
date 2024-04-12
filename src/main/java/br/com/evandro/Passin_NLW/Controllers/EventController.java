package br.com.evandro.Passin_NLW.Controllers;

import br.com.evandro.Passin_NLW.dto.attendee.AttendeeIdDTO;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeListResponseDTO;
import br.com.evandro.Passin_NLW.dto.attendee.AttendeeRequestDTO;
import br.com.evandro.Passin_NLW.dto.event.EventIdDTO;
import br.com.evandro.Passin_NLW.dto.event.EventRequestDTO;
import br.com.evandro.Passin_NLW.services.AttendeeService;
import br.com.evandro.Passin_NLW.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable String id){
            var result = eventService.getEventDetail(id);
            return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity getAllEvents(){
        var result = eventService.getAllEvents();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("")
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        var resultId = this.eventService.eventCreate(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(resultId.id()).toUri();
        return ResponseEntity.created(uri).body(resultId);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String id){
        var result = attendeeService.getEventsAttendees(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO dto, UriComponentsBuilder uriComponentsBuilder){
        var resultId = this.eventService.registerAttendedOnEvent(eventId, dto);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(resultId.attendeeId()).toUri();
        return ResponseEntity.created(uri).body(resultId);
    }

}
