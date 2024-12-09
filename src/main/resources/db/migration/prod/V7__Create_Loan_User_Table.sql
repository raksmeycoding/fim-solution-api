create table loan_user
(
    id         varchar(36) not null,
    loan_no    varchar(36),
    user_id    varchar(36),
    email      varchar(255),
    loan_name  varchar(255),
    memo       varchar(255),
    prioritize varchar(255) check (prioritize in ('DEFAULT', 'NONE_DEFAULT')),
    role       varchar(255) check (role in ('LENDER', 'BORROWER', 'PROXY')),
    type       varchar(255) check (type in ('INFO', 'ACCESS', 'DECIDE', 'TEAM')),
    primary key (id)
)