create table obligation
(
    free       float(53),
    use        boolean,
    created_at timestamp(6),
    event_id   varchar(36),
    change     varchar(255) check (change in
                                   ('NEW', 'DATE', 'DISBURSEMENT', 'REPAYMENT', 'RATE', 'PERIODS', 'CYCLE', 'FEE',
                                    'NONE')),
    id         varchar(255) not null,
    source     varchar(255) check (source in ('BORROWER', 'LENDER')),
    primary key (id)
)