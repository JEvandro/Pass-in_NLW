package br.com.evandro.Passin_NLW.dto.event;

import br.com.evandro.Passin_NLW.domain.event.Event;
import lombok.Getter;

@Getter
public class AllEventResponseDTO {

    AllEventDetailDTO event;

    public AllEventResponseDTO(Event event, boolean isOpen){
        this.event = new AllEventDetailDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(), event.getMaximumAttendees(), isOpen);
    }

}
