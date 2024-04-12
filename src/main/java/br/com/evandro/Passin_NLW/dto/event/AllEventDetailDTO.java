package br.com.evandro.Passin_NLW.dto.event;

public record AllEventDetailDTO(
        String id,
        String title,
        String detail,
        String slug,
        Integer maximumAttendees,
        boolean isOpen
) {
}
