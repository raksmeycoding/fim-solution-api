create table users
(
    id                 varchar(36)  not null,
    user_credential_id varchar(36) unique,
    email              varchar(255) not null unique,
    family_name        varchar(255),
    given_name         varchar(255),
    middle_name        varchar(255),
    nick_name          varchar(255),
    primary key (id)
)