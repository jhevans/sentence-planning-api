package uk.gov.digital.justice.hmpps.sentenceplan.jpa.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "INTERVENTION")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class InterventionEntity  implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SHORT_DESCRIPTION")
    private String shortDescription;

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "SENTENCE_PLAN_UUID", referencedColumnName = "UUID")
    private SentencePlanEntity sentencePlan;
}
