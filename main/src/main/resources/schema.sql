DROP TABLE IF EXISTS public.requests;
DROP TABLE IF EXISTS public.events;
DROP TABLE IF EXISTS public.compilations;
DROP TABLE IF EXISTS public.categories;
DROP TABLE IF EXISTS public.users;

CREATE TABLE public.users
    (id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar NOT NULL,
    email varchar NOT NULL,
CONSTRAINT users_pk PRIMARY KEY (id), CONSTRAINT users_un UNIQUE (email));

CREATE TABLE public.categories
    (id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar NOT NULL,
CONSTRAINT categories_pk PRIMARY KEY (id), CONSTRAINT categories_un UNIQUE (name));

CREATE TABLE public.compilations
    (id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    pinned bool NOT NULL,
    title varchar NOT NULL,
CONSTRAINT compilations_pk PRIMARY KEY (id));

CREATE TABLE public.events
    (id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    annotation varchar NOT NULL,
    category int8 NOT NULL,
    compilation int8 NULL,
    createdon TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description varchar NOT NULL,
    eventdate TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator int8 NOT NULL,
    latitude float4 NOT NULL,
    longitude float4 NOT NULL,
    paid bool NOT NULL,
    participantlimit int4 NOT NULL,
    publishedon TIMESTAMP WITHOUT TIME ZONE NULL,
    requestmoderation bool NOT NULL,
    state varchar NOT NULL,
    title varchar NOT NULL,
CONSTRAINT events_pk PRIMARY KEY (id));

CREATE TABLE public.requests
    (id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event int8 NOT NULL,
    userid int8 NOT NULL,
    status varchar NOT NULL,
CONSTRAINT requests_pk PRIMARY KEY (id));

ALTER TABLE public.events ADD CONSTRAINT events_fk FOREIGN KEY (initiator) REFERENCES users(id);
ALTER TABLE public.events ADD CONSTRAINT events_fk_1 FOREIGN KEY (compilation) REFERENCES compilations(id) ON DELETE SET NULL;
ALTER TABLE public.events ADD CONSTRAINT events_fk_2 FOREIGN KEY (category) REFERENCES categories(id);

ALTER TABLE public.requests ADD CONSTRAINT requests_fk FOREIGN KEY (userid) REFERENCES users(id);
ALTER TABLE public.requests ADD CONSTRAINT requests_fk_1 FOREIGN KEY (event) REFERENCES events(id);