create table payment
(
    adjust       float(53),
    amount       float(53),
    balance      float(53),
    fim_fee      float(53),
    principle    float(53),
    receipt      float(53),
    created_at   timestamp(6),
    payment_date timestamp(6),
    loan_no      varchar(36),
    schedule_id  varchar(36),
    id           varchar(255) not null,
    memo         varchar(255),
    source       varchar(255) check (source in ('BORROWER', 'LENDER')),
    status       varchar(255) check (status in ('ON_TIME', 'PREPAID', 'EXTRA')),
    type         varchar(255) check (type in ('REGULAR', 'PARTIAL', 'EXTRA')),
    primary key (id)
)