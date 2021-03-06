package uk.gov.digital.justice.hmpps.sentenceplan.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.justice.hmpps.sentenceplan.jpa.entity.CommentEntity;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel(description = "A comment/text field on a Sentence Plan. A Comment is defined by it's commentType")
public class CommentDto {

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("commentType")
    private CommentType commentType;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("createdBy")
    private String createdBy;


    public static CommentDto from(CommentEntity commentEntity) {
        return new CommentDto(commentEntity.getComment(), commentEntity.getCommentType(), commentEntity.getCreated(), commentEntity.getCreatedBy());
    }

    public static List<CommentDto> from(Collection<CommentEntity> commentEntities) {
        return commentEntities.stream().map(CommentDto::from).collect(Collectors.toList());
    }
}
