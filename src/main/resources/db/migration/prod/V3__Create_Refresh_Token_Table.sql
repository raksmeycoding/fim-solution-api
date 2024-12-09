create table refresh_token
(
    version            bigint,
    id                 varchar(36)  not null,
    user_credential_id varchar(36)  not null unique,
    token              varchar(255) not null unique,
    primary key (id)
)