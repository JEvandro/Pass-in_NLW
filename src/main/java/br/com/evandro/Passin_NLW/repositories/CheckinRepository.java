package br.com.evandro.Passin_NLW.repositories;

import br.com.evandro.Passin_NLW.domain.checkin.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

    Optional<Checkin> findByAttendeeId(String AttendeeId);

}
