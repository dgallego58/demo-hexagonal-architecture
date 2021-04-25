create schema if not exists hexagon;

create table if not exists hexagon.accounts
(
    ID bigserial primary key not null
);

create table if not exists hexagon.activities
(
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    TIMESTAMP         TIMESTAMP with time zone,
    OWNER_ACCOUNT_ID  BIGINT,
    SOURCE_ACCOUNT_ID BIGINT,
    TARGET_ACCOUNT_ID BIGINT,
    AMOUNT            BIGINT
);