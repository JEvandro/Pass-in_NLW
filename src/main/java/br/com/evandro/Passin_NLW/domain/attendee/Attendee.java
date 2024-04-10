package br.com.evandro.Passin_NLW.domain.attendee;

import br.com.evandro.Passin_NLW.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Attendee(String name, String email, Event event, LocalDateTime createdAt){
        this.name=name;
        this.email=email;
        this.event=event;
        this.createdAt=createdAt;
    }


}
