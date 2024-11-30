-- V1__initial_schema.sql

-- Create user_credentials table
CREATE TABLE user_credential
(
    id                      VARCHAR(36),
    user_id                 VARCHAR(255),
    firstname               VARCHAR(255),
    lastname                VARCHAR(255),
    phone                   VARCHAR(255),
    username                VARCHAR(255)          NOT NULL UNIQUE,
    password                VARCHAR(255)          NOT NULL,
    enabled                 BOOLEAN DEFAULT FALSE NOT NULL,
    account_non_expired     BOOLEAN DEFAULT TRUE  NOT NULL,
    credentials_non_expired BOOLEAN DEFAULT TRUE  NOT NULL,
    account_non_locked      BOOLEAN DEFAULT TRUE  NOT NULL,
    primary key (id)
);

create table user_roles
(
    id                  varchar(36) not null,
    role_id             varchar(36) not null,
    user_credentials_id varchar(36) not null,
    primary key (id, role_id, user_credentials_id)
);

create table roles
(
    id   varchar(36) not null,
    name varchar(20) not null unique,
    primary key (id)
);

create table refresh_token
(
    expiry_date        timestamp(6) with time zone not null,
    id                 varchar(36)                 not null,
    user_credential_id varchar(36) unique,
    token              varchar(255)                not null unique,
    primary key (id)
);

create table borrower
(
    created_at   timestamp(6),
    id           varchar(36) not null,
    address      varchar(255),
    email        varchar(255),
    name         varchar(255),
    phone_number varchar(255),
    primary key (id)
);

create table user
(
    id          varchar(36) not null,
    middle_name varchar(255)
        primary key (id)
);

create table lender
(
    created_at   timestamp(6),
    id           varchar(36) not null,
    address      varchar(255),
    email        varchar(255),
    name         varchar(255),
    phone_number varchar(255),
    primary key (id)
);

create table countries
(
    id   varchar(36)  not null,
    name varchar(255) not null,
    primary key (id)
);

create table loan
(
    amount      float(53)    not null unique,
    interest    float(53)    not null unique,
    created_at  timestamp(6) not null,
    borrower_id varchar(36)  not null,
    id          varchar(36)  not null,
    lender_id   varchar(36)  not null,
    loan        varchar(255) not null unique,
    primary key (id)
);