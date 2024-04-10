package br.com.evandro.Passin_NLW.repositories;

import br.com.evandro.Passin_NLW.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,String> {
}
