-- create a table for borrowers

CREATE TABLE borrowers (
    id serial PRIMARY KEY,           -- Unique identifier for each borrower
    name VARCHAR(100) NOT NULL,                  -- Borrower's name
    email VARCHAR(100) NOT NULL UNIQUE,          -- Unique email address for the borrower
    phone_number VARCHAR(15),                    -- Contact phone number
    address TEXT NOT NULL,                       -- Borrower's address
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Timestamp of record creation
);


create table lender(
	id serial primary key,
	name varcha
);