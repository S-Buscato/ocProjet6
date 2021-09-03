
CREATE SEQUENCE public.bank_account_id_seq;

CREATE TABLE public.bank_account (
                id INTEGER NOT NULL DEFAULT nextval('public.bank_account_id_seq'),
                description VARCHAR(100) NOT NULL,
                IBAN VARCHAR(100) NOT NULL,
                bank_name VARCHAR(100) NOT NULL,
                actif BOOLEAN NOT NULL,
                CONSTRAINT bank_account_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.bank_account_id_seq OWNED BY public.bank_account.id;

CREATE SEQUENCE public.users_id_seq;

CREATE TABLE public.users (
                id INTEGER NOT NULL DEFAULT nextval('public.users_id_seq'),
                first_name VARCHAR(100) NOT NULL,
                last_name VARCHAR(100) NOT NULL,
                password VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                total_amount DOUBLE PRECISION DEFAULT 0.0 NOT NULL,
                CONSTRAINT id_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

CREATE TABLE public.users_friends (
                users_friends_id INTEGER NOT NULL,
                users_id INTEGER NOT NULL,
                CONSTRAINT users_friends_pk PRIMARY KEY (users_friends_id, users_id)
);


CREATE SEQUENCE public.transfert_id_seq;

CREATE TABLE public.transfert (
                id INTEGER NOT NULL DEFAULT nextval('public.transfert_id_seq'),
                users INTEGER NOT NULL,
                bank_account INTEGER NOT NULL,
                date TIMESTAMP NOT NULL,
                amount DOUBLE PRECISION NOT NULL,
                transfert_type VARCHAR(100) NOT NULL,
                CONSTRAINT transfert_pk PRIMARY KEY (id, users, bank_account)
);


ALTER SEQUENCE public.transfert_id_seq OWNED BY public.transfert.id;

CREATE SEQUENCE public.transaction_id_seq;

CREATE TABLE public.transaction (
                id INTEGER NOT NULL DEFAULT nextval('public.transaction_id_seq'),
                emmeter INTEGER NOT NULL,
                receiver INTEGER NOT NULL,
                amount DOUBLE PRECISION NOT NULL,
                fee DOUBLE PRECISION NOT NULL,
                description VARCHAR(250) NOT NULL,
                CONSTRAINT transaction_pk PRIMARY KEY (id, emmeter, receiver)
);


ALTER SEQUENCE public.transaction_id_seq OWNED BY public.transaction.id;

ALTER TABLE public.transfert ADD CONSTRAINT bank_account_transfert_fk
FOREIGN KEY (bank_account)
REFERENCES public.bank_account (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.transaction ADD CONSTRAINT users_emmeter_transaction_fk
FOREIGN KEY (emmeter)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.transaction ADD CONSTRAINT users_receiver_transaction_fk
FOREIGN KEY (emmeter)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.transaction ADD CONSTRAINT users_transaction_fk
FOREIGN KEY (receiver)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.transfert ADD CONSTRAINT users_transfert_fk
FOREIGN KEY (users)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.users_friends ADD CONSTRAINT users_users_friends_fk
FOREIGN KEY (users_friends_id)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.users_friends ADD CONSTRAINT users_users_friends_fk1
FOREIGN KEY (users_id)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
