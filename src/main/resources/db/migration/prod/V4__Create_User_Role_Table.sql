create table user_role
(
    id                 varchar(36) not null,
    role_id            varchar(36) not null,
    user_credential_id varchar(36) not null,
    primary key (id)
)