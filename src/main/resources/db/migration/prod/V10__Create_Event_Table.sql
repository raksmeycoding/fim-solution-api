create table event
(
    created_event_at timestamp(6),
    id               varchar(36) not null,
    loan_no          varchar(36),
    user_id          varchar(36),
    medium           varchar(255) check (medium in ('CONTRACT', 'REVISION', 'REPAYMENT', 'OTHER')),
    memo             varchar(255),
    type             varchar(255) check (type in ('PROPOSE', 'COUNTER', 'SING', 'PREPAY')),
    primary key (id)
)