    -- V1__create_users_table.sql

    CREATE TABLE loans (
        id serial primary key,
        loan_no varchar(10) not null unique,
        borrower_id int not null ,
        amount decimal(10, 2),
        interest_rate decimal (5, 2)
    );