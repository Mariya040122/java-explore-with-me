DROP TABLE IF EXISTS public.stats;

CREATE TABLE public.stats(
    id        int8                        NOT NULL GENERATED ALWAYS AS IDENTITY,
    app       varchar                     NOT NULL,
    uri       varchar                     NOT NULL,
    ip        varchar                     NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    CONSTRAINT stats_pk PRIMARY KEY (id)
);