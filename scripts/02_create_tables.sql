DROP TABLE public.account;

CREATE TABLE public.account
(
    account_no integer NOT NULL,
    customer_id integer,
    ifsc_code character varying(30) COLLATE "default".pg_catalog NOT NULL,
    account_type character varying(20) COLLATE "default".pg_catalog NOT NULL,
    account_balance double precision NOT NULL,
    account_creation_date date,
    account_status boolean NOT NULL,
    CONSTRAINT pk_accno PRIMARY KEY (account_no),
    CONSTRAINT account_customer_id_fkey FOREIGN KEY (customer_id)
        REFERENCES public.customer (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.account
    OWNER to postgres;
    
DROP TABLE public.customer;

CREATE TABLE public.customer
(
    customer_id integer NOT NULL,
    customer_name character varying(20) COLLATE "default".pg_catalog NOT NULL,
    password character varying(20) COLLATE "default".pg_catalog,
    last_login date,
    mobile_no integer,
    email_id character varying(50) COLLATE "default".pg_catalog,
    CONSTRAINT pk_custid PRIMARY KEY (customer_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.customer
    OWNER to postgres;
    
-- Table: public.account_tansaction

-- DROP TABLE public.account_tansaction;

CREATE TABLE public.account_tansaction
(
    transaction_id numeric NOT NULL,
    tx_date timestamp with time zone,
    tx_details character varying(200) COLLATE pg_catalog."default",
    tx_type character varying(300) COLLATE pg_catalog."default",
    account_no numeric(16),
    tx_value numeric(10, 2),
    tx_description character varying(250) COLLATE pg_catalog."default",
    CONSTRAINT account_tansaction_pkey PRIMARY KEY (transaction_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.account_tansaction
    OWNER to postgres;
    