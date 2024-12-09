create table user_credentials
(
    account_non_expired     boolean default true  not null,
    account_non_locked      boolean default true  not null,
    credentials_non_expired boolean default true  not null,
    enabled                 boolean default false not null,
    id                      varchar(36)           not null,
    firstname               varchar(255),
    lastname                varchar(255),
    password                varchar(255)          not null,
    phone                   varchar(255),
    username                varchar(255)          not null unique,
    primary key (id)
)