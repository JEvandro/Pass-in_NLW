package br.com.evandro.Passin_NLW.repositories;

import br.com.evandro.Passin_NLW.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,String> {

    Optional<Event> findByTitleIgnoringCaseAndDetailsIgnoringCase(String title, String details);

}
