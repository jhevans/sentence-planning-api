package uk.gov.digital.justice.hmpps.sentenceplan.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel(description = "Add a new Comment to a Sentence Plan")
public class AddCommentRequest {

    @ApiModelProperty(required = true, value = "The Comment")
    @NotNull
    @JsonProperty("comment")
    private String comment;

    @ApiModelProperty(required = true, value = "The Comment Type")
    @NotNull
    @JsonProperty("commentType")
    private CommentType commentType;

}
