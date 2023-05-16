-- Table: public.role

CREATE TABLE IF NOT EXISTS public.role
(
    id integer NOT NULL,
    name character varying COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id)
)


TABLESPACE pg_default;

INSERT INTO public.role(
	id,	name)
	VALUES (1, 'admin');

INSERT INTO public.role(
	id, name)
	VALUES (2, 'interni korisnik');

INSERT INTO public.role(
	id, name)
	VALUES (3, 'eksterni korisnik');

ALTER TABLE IF EXISTS public.role
OWNER to postgres;



-- Table: public.users

DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    username character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    role integer NOT NULL,
    active boolean NOT NULL DEFAULT true,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email),
    CONSTRAINT username UNIQUE (username),
    CONSTRAINT user_role_fkey FOREIGN KEY (role)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

INSERT INTO public.users(
	username, password, email, role, active)
	VALUES ('admin5', 'MainUser!', 'lea.jesenkovic@loop.ba', 1, true);

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;