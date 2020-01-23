package uk.gov.digital.justice.hmpps.sentenceplan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.history.Revision;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.digital.justice.hmpps.sentenceplan.api.*;
import uk.gov.digital.justice.hmpps.sentenceplan.client.dto.OasysSentencePlan;
import uk.gov.digital.justice.hmpps.sentenceplan.jpa.entity.SentencePlanEntity;
import uk.gov.digital.justice.hmpps.sentenceplan.security.AccessLevel;
import uk.gov.digital.justice.hmpps.sentenceplan.security.Authorised;
import uk.gov.digital.justice.hmpps.sentenceplan.service.SentencePlanService;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

@Api(tags = {"Sentence Planning API"})

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class SentencePlanResource {

    private final SentencePlanService sentencePlanService;

    public SentencePlanResource(SentencePlanService sentencePlanService) {
        this.sentencePlanService = sentencePlanService;
    }

    @PostMapping(value = "/offenders/{offenderId}/sentenceplans", produces = "application/json")
    @ApiOperation(value = "Create new sentence plan",
            notes = "Creates a new sentence plan")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity<SentencePlan> createSentencePlan(@ApiParam(value = "OASys Offender ID", required = true, example = "123456") @PathVariable("offenderId") Long oasysOffenderId) {
        var sentencePlan = sentencePlanService.createSentencePlan(oasysOffenderId);
        return ResponseEntity.status(201).body(SentencePlan.from(sentencePlan));
    }

    @GetMapping(value = "/sentenceplans/{sentencePlanUUID}", produces = "application/json")
    @ApiOperation(value = "Gets a Sentence Plan from its ID",
            notes = "Request sentence plan")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<SentencePlan> getSentencePlan(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID) {
        return ResponseEntity.ok(SentencePlan.from(sentencePlanService.getSentencePlanFromUuid(sentencePlanUUID)));
    }

    @GetMapping(value = "/sentenceplans/{sentencePlanUUID}/revisions", produces = "application/json")
    @ApiOperation(value = "Gets a previous revisions of a Sentence Plan",
            notes = "Request sentence plan history")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<List<Revision<Integer, SentencePlanEntity>>> getSentencePlanRevisions(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID) {
        return ResponseEntity.ok(sentencePlanService.getSentencePlanRevisions(sentencePlanUUID));
    }


    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/end", produces = "application/json")
    @ApiOperation(value = "End a sentence plan",
            notes = "Called when we want to 'complete' or 'delete' a plan")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity endSentencePlan(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID) {
        sentencePlanService.endSentencePlan(sentencePlanUUID);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/start", produces = "application/json")
    @ApiOperation(value = "Start a sentence plan",
            notes = "Called when we're happy we've added all the objectives we want")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity startSentencePlan(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID) {
        sentencePlanService.startSentencePlan(sentencePlanUUID);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/sentenceplans/{sentencePlanUUID}/comments", produces = "application/json")
    @ApiOperation(value = "Add comments",
            notes = "Add comments of various types to the Sentence Plan")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity addComments(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID, @ApiParam(value = "List of comments", required = true) @RequestBody List<AddCommentRequest> comments) {
        sentencePlanService.addSentencePlanComments(sentencePlanUUID, comments);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sentenceplans/{sentencePlanUUID}/comments", produces = "application/json")
    @ApiOperation(value = "Get comments",
            notes = "Get comments of various types")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<List<Comment>> getComments(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID) {
        return ResponseEntity.ok(Comment.from(sentencePlanService.getSentencePlanComments(sentencePlanUUID)));
    }

    @GetMapping(value = "/sentenceplans/{sentencePlanUUID}/needs", produces = "application/json")
    @ApiOperation(value = "Get Sentence Plan needs from ID",
            notes = "Request sentence plan needs")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<List<Need>> getSentencePlanNeeds(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID) {
        return ResponseEntity.ok(Need.from(sentencePlanService.getSentencePlanNeeds(sentencePlanUUID)));
    }

    @GetMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives", produces = "application/json")
    @ApiOperation(value = "Get objectives",
            notes = "Get all objectives")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<List<Objective>> getObjectives(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID) {
        return ResponseEntity.ok(Objective.from(sentencePlanService.getSentencePlanObjectives(sentencePlanUUID)));
    }

    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives", produces = "application/json")
    @ApiOperation(value = "Add an Objective to a Sentence Plan")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity<Objective> addObjective(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID, @ApiParam(value = "Objective details", required = true) @RequestBody @Valid AddSentencePlanObjective objective) {
        return ResponseEntity.ok(Objective.from(sentencePlanService.addObjective(
                sentencePlanUUID,
                objective.getDescription(),
                objective.getNeeds())));
    }

    @PutMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/{objectiveUUID}", produces = "application/json")
    @ApiOperation(value = "Update an Objective on a Sentence Plan")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity updateObjective(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID,
                                   @ApiParam(value = "Objective ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID objectiveUUID,
                                   @ApiParam(value = "Objective details", required = true) @RequestBody @Valid AddSentencePlanObjective objective) {
        sentencePlanService.updateObjective(
                sentencePlanUUID,
                objectiveUUID,
                objective.getDescription(),
                objective.getNeeds());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/{objectiveUUID}", produces = "application/json")
    @ApiOperation(value = "Get an Objective for a Sentence Plan")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<Objective> getObjective(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID,
                                           @ApiParam(value = "Objective ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID objectiveUUID) {
        return ResponseEntity.ok(Objective.from(sentencePlanService.getObjective(sentencePlanUUID, objectiveUUID)));
    }

    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/{objectiveUUID}/actions", produces = "application/json")
    @ApiOperation(value = "Add an Action to a Sentence Plan Objective")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity addAction(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID,
                             @ApiParam(value = "Objective ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID objectiveUUID,
                             @ApiParam(value = "Action details", required = true) @RequestBody @Valid AddSentencePlanAction action) {
         sentencePlanService.addAction(
                 sentencePlanUUID,
                 objectiveUUID,
                 action.getInterventionUUID(),
                 action.getDescription(),
                 action.getTargetDate(),
                 action.getMotivationUUID(),
                 action.getOwner(),
                 action.getOwnerOther(),
                 action.getStatus());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/{objectiveUUID}/actions/{actionUUID}", produces = "application/json")
    @ApiOperation(value = "Add an Action to a Sentence Plan Objective")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity updateAction(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID,
                                @ApiParam(value = "Objective ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID objectiveUUID,
                                @ApiParam(value = "Action ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID actionUUID,
                                @ApiParam(value = "Action details", required = true) @RequestBody @Valid AddSentencePlanAction action) {
        sentencePlanService.updateAction(
                sentencePlanUUID,
                objectiveUUID,
                actionUUID,
                action.getInterventionUUID(),
                action.getDescription(),
                action.getTargetDate(),
                action.getMotivationUUID(),
                action.getOwner(),
                action.getOwnerOther(),
                action.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/{objectiveUUID}/actions/{actionUUID}", produces = "application/json")
    @ApiOperation(value = "Get an Action from a Sentence Plan Objective")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<Action> getAction(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID,
                                     @ApiParam(value = "Objective ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID objectiveUUID,
                                     @ApiParam(value = "Action ID", required = true) @PathVariable UUID actionUUID) {
        return ResponseEntity.ok(Action.from(sentencePlanService.getAction(sentencePlanUUID, objectiveUUID, actionUUID)));

    }

    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/{objectiveUUID}/actions/{actionId}/progress", produces = "application/json")
    @ApiOperation(value = "Progress an Action")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity progressAction(@ApiParam(value = "Sentence Plan ID", required = true) @PathVariable UUID sentencePlanUUID, @ApiParam(value = "Objective ID", required = true) @PathVariable UUID objectiveUUID, @ApiParam(value = "Action ID") @PathVariable UUID actionId, @ApiParam(value = "A status and comment against the action", required = true) @RequestBody ProgressActionRequest request) {
        sentencePlanService.progressAction(sentencePlanUUID, objectiveUUID, actionId, request.getStatus(), request.getTargetDate(), request.getMotivationUUID(), request.getComment(), request.getOwner(), request.getOwnerOther());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/priority", produces = "application/json")
    @ApiOperation(value = "Set the priorities of objectives on a Sentence Plan")
    @Authorised(accessLevel = AccessLevel.WRITE_SENTENCE_PLAN)
    ResponseEntity updateObjectivePriority(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID,
                                           @ApiParam(value = "A list of Objective Ids and their order", required = true) @RequestBody List<UpdateObjectivePriorityRequest> request) {
        var objectivePriorities = request.stream().collect(Collectors.toMap(UpdateObjectivePriorityRequest::getObjectiveUUID, UpdateObjectivePriorityRequest::getPriority));
        sentencePlanService.updateObjectivePriorities(sentencePlanUUID, objectivePriorities);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/sentenceplans/{sentencePlanUUID}/objectives/{objectiveUUID}/actions/priority", produces = "application/json")
    @ApiOperation(value = "Set the priorities of actions on a Sentence Plan")
    ResponseEntity updateActionPriority(@ApiParam(value = "Sentence Plan ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID sentencePlanUUID,
                                        @ApiParam(value = "Objective ID", required = true, example = "11111111-1111-1111-1111-111111111111") @PathVariable UUID objectiveUUID,
                                        @ApiParam(value = "A list of Action Ids and their order", required = true) @RequestBody List<UpdateActionPriorityRequest> request) {
        var actionPriorities = request.stream().collect(Collectors.toMap(UpdateActionPriorityRequest::getActionUUID, UpdateActionPriorityRequest::getPriority));
        sentencePlanService.updateActionPriorities(sentencePlanUUID, objectiveUUID, actionPriorities);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/offenders/{offenderId}/sentenceplans", produces = "application/json")
    @ApiOperation(value = "Gets a list of Sentence Plans for an Offender",
            response = SentencePlan.class,
            notes = "Request sentence plans for offender. Includes both new and OASYs sentence plans")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<List<SentencePlanSummary>> getSentencePlansForOffender(@ApiParam(value = "OASys Offender ID", required = true, example = "123456") @PathVariable("offenderId") Long oasysOffenderId) {
        return ResponseEntity.ok(sentencePlanService.getSentencePlansForOffender(oasysOffenderId));
    }

    @GetMapping(value = "/offenders/{offenderId}/sentenceplans/current", produces = "application/json")
    @ApiOperation(value = "Gets an Oasys Sentence Plan by its ID",
            response = SentencePlan.class,
            notes = "Request legacy sentence plan")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<SentencePlan> getActiveSentencePlan(@ApiParam(value = "OASys Offender ID", required = true, example = "123456") @PathVariable("offenderId") Long oasysOffenderId) {
        return ResponseEntity.ok(SentencePlan.from(sentencePlanService.getCurrentSentencePlanForOffender(oasysOffenderId)));
    }

    @GetMapping(value = "/offenders/{offenderId}/sentenceplans/{sentencePlanId}", produces = "application/json")
    @ApiOperation(value = "Gets an Oasys Sentence Plan by its ID",
            response = SentencePlan.class,
            notes = "Request legacy sentence plan")
    @Authorised(accessLevel = AccessLevel.READ_SENTENCE_PLAN)
    ResponseEntity<OasysSentencePlan> getOASysSentencePlan(@ApiParam(value = "OASys Offender ID", required = true, example = "123456") @PathVariable("offenderId") Long oasysOffenderId, @ApiParam(value = "Sentence Plan ID", required = true) @PathVariable("sentencePlanId") String sentencePlanId) {
        return ResponseEntity.ok(sentencePlanService.getLegacySentencePlan(oasysOffenderId, sentencePlanId));
    }
}
