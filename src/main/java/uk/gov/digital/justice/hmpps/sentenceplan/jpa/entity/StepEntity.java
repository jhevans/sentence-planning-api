package uk.gov.digital.justice.hmpps.sentenceplan.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import uk.gov.digital.justice.hmpps.sentenceplan.api.StepOwner;
import uk.gov.digital.justice.hmpps.sentenceplan.api.StepStatus;
import uk.gov.digital.justice.hmpps.sentenceplan.application.ValidationException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
public class StepEntity implements Serializable {

    @Getter
    private UUID id;

    @Getter
    private StepOwner owner;

    @Getter
    private String ownerOther;

    @Getter
    private String description;

    @Getter
    private String strength;

    @Getter
    private StepStatus status;

    @Getter
    private List<UUID> needs;

    @Getter
    private String intervention;

    @Getter
    private int priority;

    @Getter
    private List<ProgressEntity> progress;

    @Getter
    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime updated = getCreated();


    public StepEntity() {
        progress = new ArrayList<>(0);
    }

    public StepEntity(StepOwner owner, String ownerOther, String description, String strength, StepStatus status, List<UUID> needs, String intervention) {

        updateStep(owner, ownerOther, description, strength, status, needs, intervention);
        this.id = UUID.randomUUID();
        this.progress = new ArrayList<>(0);
    }

    public void updateStep(StepOwner owner, String ownerOther, String description, String strength, StepStatus status, List<UUID> needs, String intervention) {

        validateNeeds(needs);
        validateOwner(owner, ownerOther);
        validateDescription(description, intervention);

        this.owner = owner;
        this.ownerOther = ownerOther;

        // Overwrite the description if there is an intervention.
        this.description = StringUtils.isEmpty(intervention) ? description : intervention;

        this.intervention = intervention;

        this.strength = strength;
        this.status = status;

        // When we update a step we just overwrite whatever needs there are, we don't try to merge/deduplicate the list
        this.needs = needs;

        this.updated = LocalDateTime.now();

    }

    @JsonIgnore
    public LocalDateTime getLastUpdated() {
        Optional<LocalDateTime> lastProgressed = this.progress.stream().map(ProgressEntity::getCreated).max(Comparator.naturalOrder());

        if(lastProgressed.isPresent()) {
            return this.updated.isBefore(lastProgressed.get()) ? lastProgressed.get() : this.updated;
        } else {
            return this.updated;
        }

    }

    public void addProgress(ProgressEntity progressEntity) {
        this.progress.add(progressEntity);
        this.status = progressEntity.getStatus();
    }

    public static StepEntity updatePriority(StepEntity stepEntity, int priority) {
        stepEntity.setPriority(priority);
        return stepEntity;
    }

    private void validateNeeds(List<UUID> needs) {
        if(needs == null || needs.size() < 1) {
            throw new ValidationException("Step must address one or more needs");
        }
    }

    private void validateOwner(StepOwner owner, String ownerOther) {
        if(owner == null) {
            throw new ValidationException("Owner must be specified");
        }
        if(owner.equals(StepOwner.OTHER) && StringUtils.isEmpty(ownerOther)) {
            throw new ValidationException("OwnerOther must be specified if StepOwner is OTHER");
        }
    }

    private void validateDescription(String description, String intervention) {
        if(StringUtils.isEmpty(intervention) && StringUtils.isEmpty(description)){
            throw new ValidationException("Description must be specified if intervention is not specified");
        }
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
