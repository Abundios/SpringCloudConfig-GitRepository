
-- DROP SEQUENCE IF EXISTS public.sequence_group;

CREATE SEQUENCE IF NOT EXISTS public.sequence_group
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.sequence_group
    OWNER TO root;
	

-- SEQUENCE: public.sequence_product

-- DROP SEQUENCE IF EXISTS public.sequence_product;

CREATE SEQUENCE IF NOT EXISTS public.sequence_product
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.sequence_product
    OWNER TO root;
	

-- SEQUENCE: public.sequence_user

-- DROP SEQUENCE IF EXISTS public.sequence_user;

CREATE SEQUENCE IF NOT EXISTS public.sequence_user
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.sequence_user
    OWNER TO root;
	

-- SEQUENCE: public.sequence_user_product

-- DROP SEQUENCE IF EXISTS public.sequence_user_product;

CREATE SEQUENCE IF NOT EXISTS public.sequence_user_product
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.sequence_user_product
    OWNER TO root;
	
	
--------------------------------------------

-- Table: public.group

-- DROP TABLE IF EXISTS public."group";

CREATE TABLE IF NOT EXISTS public."group"
(
    "Id" integer NOT NULL,
    "Desription" character(100) COLLATE pg_catalog."default",
    CONSTRAINT "Group_pkey" PRIMARY KEY ("Id")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."group"
    OWNER to root;
	


-- Table: public.product

-- DROP TABLE IF EXISTS public.product;

CREATE TABLE IF NOT EXISTS public.product
(
    id integer NOT NULL,
    currentvalue numeric(15,4),
    description character(120) COLLATE pg_catalog."default",
    isin character(12) COLLATE pg_catalog."default",
    link character(400) COLLATE pg_catalog."default",
    ter numeric(6,4),
    CONSTRAINT "Product_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to root;
	


-- Table: public.user

-- DROP TABLE IF EXISTS public."user";

CREATE TABLE IF NOT EXISTS public."user"
(
    id integer NOT NULL,
    groupid integer,
    forename character(100) COLLATE pg_catalog."default",
    surename character(100) COLLATE pg_catalog."default",
    uid character(100) COLLATE pg_catalog."default",
    CONSTRAINT "User_pkey" PRIMARY KEY (id),
    CONSTRAINT "FK_USER_GROUPID" FOREIGN KEY (groupid)
        REFERENCES public."group" ("Id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to root;
	
	

-- Table: public.userproduct

-- DROP TABLE IF EXISTS public.userproduct;

CREATE TABLE IF NOT EXISTS public.userproduct
(
    id integer NOT NULL,
    userid integer NOT NULL,
    productid integer NOT NULL,
    investedmount numeric(9,4),
    productvalue numeric(15,4),
    participations numeric(15,6),
    CONSTRAINT "UserProduct_pkey" PRIMARY KEY (id, userid, productid),
    CONSTRAINT "FK_USERPRODUCT_PRODUCTID" FOREIGN KEY (productid)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_USERPRODUCT_USERID" FOREIGN KEY (userid)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.userproduct
    OWNER to root;
-- Index: fki_FK_USERPRODUCT_PRODUCTID

-- DROP INDEX IF EXISTS public."fki_FK_USERPRODUCT_PRODUCTID";

CREATE INDEX IF NOT EXISTS "fki_FK_USERPRODUCT_PRODUCTID"
    ON public.userproduct USING btree
    (productid ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_FK_USERPRODUCT_USERID

-- DROP INDEX IF EXISTS public."fki_FK_USERPRODUCT_USERID";

CREATE INDEX IF NOT EXISTS "fki_FK_USERPRODUCT_USERID"
    ON public.userproduct USING btree
    (userid ASC NULLS LAST)
    TABLESPACE pg_default;
	