package br.com.evandro.Passin_NLW.domain.checkin;

import br.com.evandro.Passin_NLW.domain.attendee.Attendee;
import ch.qos.logback.core.model.INamedModel;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    public Checkin(Attendee attendee, LocalDateTime createdAt){
        this.attendee=attendee;
        this.createdAt=createdAt;
    }

}
