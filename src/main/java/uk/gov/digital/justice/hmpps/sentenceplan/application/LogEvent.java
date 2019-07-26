package uk.gov.digital.justice.hmpps.sentenceplan.application;

public enum LogEvent {

    SENTENCE_PLAN_RETRIEVED,
    SENTENCE_PLAN_CREATED, OASYS_ASSESSMENT_CLIENT_FAILURE, OASYS_ASSESSMENT_NOT_FOUND, SENTENCE_PLAN_STEPS_RETRIEVED, SENTENCE_PLAN_NEEDS_RETRIEVED, SENTENCE_PLAN_STEP_CREATED, SENTENCE_PLAN_STARTED, SENTENCE_PLAN_STEP_RETRIEVED;

    public static final String EVENT = "event_id";
}
