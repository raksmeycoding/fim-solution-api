create table loan
(
    amount               float(53)    not null,
    fee                  float(53),
    interest             float(53)    not null,
    is_link_to_loan_user boolean default false,
    created_date         timestamp(6) not null,
    end_date             timestamp(6),
    id                   varchar(36)  not null,
    nick_name            varchar(255),
    note                 varchar(255),
    type                 varchar(255) check (type in ('FIXED', 'REVOLVING', 'COLLATERAL')),
    primary key (id)
)