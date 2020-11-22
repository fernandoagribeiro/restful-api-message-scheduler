CREATE SCHEMA luiza_labs_exam
    AUTHORIZATION luiza_labs;

CREATE SEQUENCE luiza_labs_exam.message_type_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE luiza_labs_exam.message_type_id_seq
    OWNER TO luiza_labs;

CREATE TABLE luiza_labs_exam.message_type
(
    id integer NOT NULL DEFAULT nextval('luiza_labs_exam.message_type_id_seq'::regclass),
    message_type text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT message_type_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;

ALTER TABLE luiza_labs_exam.message_type
    OWNER to luiza_labs;

CREATE SEQUENCE luiza_labs_exam.scheduled_message_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE luiza_labs_exam.scheduled_message_id_seq
    OWNER TO luiza_labs;

CREATE TABLE luiza_labs_exam.scheduled_message
(
    id bigint NOT NULL DEFAULT nextval('luiza_labs_exam.scheduled_message_id_seq'::regclass),
    message_content text COLLATE pg_catalog."default" NOT NULL,
    recipient text COLLATE pg_catalog."default" NOT NULL,
    date_to_send timestamp without time zone NOT NULL,
    is_sent boolean NOT NULL DEFAULT false,
    message_type_id integer NOT NULL,
    CONSTRAINT scheduled_messages_pkey PRIMARY KEY (id),
    CONSTRAINT fk_message_type FOREIGN KEY (message_type_id)
        REFERENCES luiza_labs_exam.message_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
TABLESPACE pg_default;

ALTER TABLE luiza_labs_exam.scheduled_message
    OWNER to luiza_labs;

CREATE INDEX fki_fk_message_type
    ON luiza_labs_exam.scheduled_message USING btree
    (message_type_id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX idx_date_to_send_x_is_sent
    ON luiza_labs_exam.scheduled_message USING btree
    (date_to_send ASC NULLS LAST, is_sent ASC NULLS LAST)
    TABLESPACE pg_default;

insert into luiza_labs_exam.message_type (message_type) values('Email');
insert into luiza_labs_exam.message_type (message_type) values('SMS');
insert into luiza_labs_exam.message_type (message_type) values('WhatsApp');
insert into luiza_labs_exam.message_type (message_type) values('Push');
