package uk.gov.digital.justice.hmpps.sentenceplan.application;

public enum LogEvent {

    SENTENCE_PLAN_RETRIEVED,
    SENTENCE_PLAN_CREATED,
    OASYS_ASSESSMENT_CLIENT_FAILURE,
    OASYS_ASSESSMENT_NOT_FOUND,
    SENTENCE_PLAN_NEEDS_RETRIEVED,
    SENTENCE_PLAN_COMMENTS_CREATED,
    SENTENCE_PLAN_COMMENTS_RETRIEVED,
    SENTENCE_PLAN_ACTION_RETRIEVED,
    SENTENCE_PLAN_ACTION_UPDATED,
    SENTENCE_PLAN_ACTION_CREATED,
    SENTENCE_PLAN_OBJECTIVE_RETRIEVED,
    SENTENCE_PLAN_OBJECTIVE_UPDATED,
    SENTENCE_PLAN_OBJECTIVE_CREATED,
    SENTENCE_PLAN_ACTION_PRIORITY_UPDATED,
    SENTENCE_PLAN_OBJECTIVE_PRIORITY_UPDATED,
    SENTENCE_PLAN_ACTION_PROGRESSED,
    SENTENCE_PLAN_STARTED,
    SENTENCE_PLAN_ENDED,
    SENTENCE_PLANS_RETRIEVED,
    SENTENCE_BOARD_REVIEW_CREATED,
    SENTENCE_BOARD_REVIEW_RETRIEVED,
    SENTENCE_BOARD_REVIEWS_RETRIEVED;

    public static final String EVENT = "event_id";
}
