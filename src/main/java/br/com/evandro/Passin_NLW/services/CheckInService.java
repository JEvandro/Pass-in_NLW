package br.com.evandro.Passin_NLW.services;

import br.com.evandro.Passin_NLW.domain.attendee.Attendee;
import br.com.evandro.Passin_NLW.domain.checkin.Checkin;
import br.com.evandro.Passin_NLW.domain.checkin.exceptions.CheckInAlreadyExistsException;
import br.com.evandro.Passin_NLW.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee){
        this.verifyCheckInExists(attendee.getId());
        this.checkinRepository.save(new Checkin(attendee, LocalDateTime.now()));
    }

    public Optional<Checkin> getCheckIn(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }

    private void verifyCheckInExists(String attendeeId){
        var checkin =  this.checkinRepository.findByAttendeeId(attendeeId);
        if(checkin.isPresent()) throw new CheckInAlreadyExistsException("Attendee is already checked");
    }

}
