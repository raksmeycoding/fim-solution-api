create table schedule
(
    adjustment    float(53),
    amount        float(53),
    balance       float(53),
    due           float(53),
    fim_fee       float(53),
    interest      float(53),
    paid          float(53),
    principle     float(53),
    create_at     timestamp(6),
    id            varchar(36) not null,
    loan_id       varchar(36),
    delinquency   varchar(255) check (delinquency in ('ZERO', 'THIRTY', 'SIXTY', 'NINETY', 'DEFAULT')),
    memo          varchar(255),
    obligation_id varchar(255),
    source        varchar(255) check (source in ('BORROWER', 'LENDER')),
    status        varchar(255) check (status in
                                      ('FUTURE', 'ON_TIME', 'PREPAID', 'PAST', 'LATE_5', 'LATE_30', 'PAST_DUE_5',
                                       'PAST_DUE_30', 'PAST_DUE_60', 'PAST_DUE_90')),
    primary key (id)
)