
CREATE TABLE IF NOT EXISTS public.file
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    title character varying COLLATE pg_catalog."default" NOT NULL,
    description character varying COLLATE pg_catalog."default",
    expiry timestamp without time zone NOT NULL,
    max_download integer NOT NULL,
    upload_file bytea NOT NULL,
    CONSTRAINT file_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.file
    OWNER to postgres;