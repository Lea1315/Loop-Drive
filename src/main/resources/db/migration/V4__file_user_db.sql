-- Table: public.file_users

-- DROP TABLE IF EXISTS public.file_users;

CREATE TABLE IF NOT EXISTS public.file_users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    file_id integer NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT file_users_pkey PRIMARY KEY (id),
    CONSTRAINT file_users_file_id_fkey FOREIGN KEY (file_id)
        REFERENCES public.file (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT file_users_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.file_users
    OWNER to postgres;