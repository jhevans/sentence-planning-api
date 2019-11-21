CREATE SEQUENCE hibernate_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP TABLE IF EXISTS revinfo;

CREATE TABLE revinfo
(
    rev integer NOT NULL,
    revtstmp bigint,
    CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
);

DROP TABLE IF EXISTS intervention_aud;

CREATE TABLE intervention_aud
(
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    active boolean,
    description character varying(255),
    short_description character varying(255),
    uuid uuid,
    sentence_plan_uuid bigint,
    CONSTRAINT intervention_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT fkb3d3togcuj7s066tg5evxmw8t FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

DROP TABLE IF EXISTS motivation_aud;

CREATE TABLE motivation_aud
(
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    end_date timestamp,
    start_date timestamp,
    motivation_ref_uuid bigint,
    need_uuid bigint,
    CONSTRAINT motivation_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT fk9ydt16ubp542b0xicsbu0mib7 FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

DROP TABLE IF EXISTS motivation_ref_data_aud;

CREATE TABLE motivation_ref_data_aud
(
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    created timestamp,
    deleted timestamp,
    friendly_text character varying(255),
    motivation_text character varying(255),
    uuid uuid,
    CONSTRAINT motivation_ref_data_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT fk3uiqmavxtqf6shku7fiod0nwe FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

DROP TABLE IF EXISTS need_aud;

CREATE TABLE need_aud
(
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    active boolean,
    created_on timestamp,
    description character varying(255),
    harm_risk boolean,
    low_score_risk boolean,
    over_threshold boolean,
    reoffending_risk boolean,
    uuid uuid,
    CONSTRAINT need_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT fkj2ha64qyxnbc0fjaxs2hq0ek6 FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

DROP TABLE IF EXISTS offender_aud;

CREATE TABLE offender_aud
(
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    delius_offender_id character varying(255),
    nomis_booking_number bigint,
    nomis_offender_id character varying(255),
    oasys_offender_id bigint,
    oasys_offender_last_imported_on timestamp,
    uuid uuid,
    CONSTRAINT offender_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT fkc73r63g7mkosct8c0icmsufsh FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

DROP TABLE IF EXISTS sentence_plan_aud;

CREATE TABLE sentence_plan_aud
(
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    assessment_needs_last_imported_on timestamp,
    created_by character varying(255),
    created_on timestamp without time zone,
    data jsonb,
    end_date timestamp without time zone,
    event_type integer,
    modified_on timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    start_date timestamp without time zone,
    status character varying(255) COLLATE pg_catalog."default",
    uuid uuid,
    offender_uuid bigint,
    CONSTRAINT sentence_plan_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT fkrkkawcrbdlvj7316wrytv3i22 FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);