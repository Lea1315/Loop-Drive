-- Table: public.file_log

-- DROP TABLE IF EXISTS public.file_log;

CREATE TABLE IF NOT EXISTS public.file_log
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    file_id integer,
    upload_date date,
    upload_user integer,
    download_date date,
    download_user integer,
    CONSTRAINT file_log_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.file_log
    OWNER to postgres;