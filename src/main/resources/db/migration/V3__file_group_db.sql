
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

-- Table: public.file_groups

-- DROP TABLE IF EXISTS public.file_groups;

CREATE TABLE IF NOT EXISTS public.file_groups
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    file_id integer NOT NULL,
    group_id integer NOT NULL,
    CONSTRAINT file_groups_pkey PRIMARY KEY (id),
    CONSTRAINT file_groups_file_id_fkey FOREIGN KEY (file_id)
        REFERENCES public.file (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT file_groups_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES public.groups (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.file_groups
    OWNER to postgres;