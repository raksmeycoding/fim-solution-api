create table roles
(
    name varchar(20) not null unique,
    id   varchar(36) not null,
    primary key (id)
)