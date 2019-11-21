package uk.gov.digital.justice.hmpps.sentenceplan.jpa.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "MOTIVATION")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class MotivationEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "NEED_UUID", referencedColumnName = "UUID")
    private NeedEntity need;

    @OneToOne
    @JoinColumn(name = "MOTIVATION_REF_UUID", referencedColumnName = "UUID")
    private MotivationRefEntity motivationRef;

    @Column(name = "START_DATE")
    private LocalDateTime start;

    @Column(name = "END_DATE")
    private LocalDateTime end;

    public MotivationEntity(NeedEntity needEntity, MotivationRefEntity motivationRef) {
        this.need = needEntity;
        this.motivationRef = motivationRef;
        this.start = LocalDateTime.now();
    }

    public boolean isEnded() {
        return this.end != null;
    }

    public void end() {
        this.end = LocalDateTime.now();
    }
}
