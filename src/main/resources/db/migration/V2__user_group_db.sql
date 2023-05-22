-- Table: public.groups

DROP TABLE IF EXISTS public.groups;

CREATE TABLE IF NOT EXISTS public.groups
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT group_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.groups
    OWNER to postgres;

-- Table: public.user_group

-- DROP TABLE IF EXISTS public.user_group;

CREATE TABLE IF NOT EXISTS public.user_group
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    user_id integer NOT NULL,
    group_id integer NOT NULL,
    CONSTRAINT user_group_pkey PRIMARY KEY (id),
    CONSTRAINT user_group_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES public.groups (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT user_group_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_group
    OWNER to postgres;