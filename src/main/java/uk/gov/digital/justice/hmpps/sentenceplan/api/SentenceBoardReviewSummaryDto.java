package uk.gov.digital.justice.hmpps.sentenceplan.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.justice.hmpps.sentenceplan.jpa.entity.SentenceBoardReviewEntity;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel(description = "Sentence Board Review Summary")
public class SentenceBoardReviewSummaryDto {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("dateOfBoard")
    private LocalDate dateOfBoard;

    public static SentenceBoardReviewSummaryDto from(SentenceBoardReviewEntity review) {
        return new SentenceBoardReviewSummaryDto(review.getUuid(), review.getDateOfBoard());
    }

    public static List<SentenceBoardReviewSummaryDto> from(Collection<SentenceBoardReviewEntity> reviews) {
        return reviews.stream().map(SentenceBoardReviewSummaryDto::from).collect(Collectors.toList());
    }
}
