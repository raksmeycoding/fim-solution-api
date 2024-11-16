-- V2__seed_users.sql
INSERT INTO user_credentials (id, user_id, firstname, lastname, phone, username, password, enabled, account_non_expired,
                              credentials_non_expired, account_non_locked)
VALUES ('b11a1c40-42e3-4eaf-93d0-9b77c9aaff6d','user1_id',   'John',  'Doe',  '123-456-7890',  'johndoe@gmail.com',
        '$2a$10$sjfDP1QVBSRNUazE3.Du/OBZYc4U/bWhHtXgpv3r9gpPazrDdGMBy',    TRUE,   TRUE,   TRUE,   TRUE),
       ('c29a1c50-52e3-4fef-83d0-0b67c9bfbf6e', 'user2_id', 'Jane', 'Smith', '098-765-4321', 'janesmith@gmail.com',
        '$2a$10$XZeV.dN65JjX4ckSO8hj/ugrNpvZ3amdbMqaCKnhD2WlzIK9W6PnC', TRUE, TRUE, TRUE, TRUE),
       ('d38b1c60-62e3-5fef-93d0-8c78c9cfcf7f', 'user3_id', 'Emily', 'Johnson', '555-555-5555', 'emilyjohnson@gmail.com',
        '$2a$10$6mPHZ5ROPXnZ/PcIIQML4eiALafl/M6EArVD47Ez8mVCfREl5l7.O', TRUE, TRUE, TRUE, TRUE);


-- Inserting data into roles table
INSERT INTO roles (id, name)
VALUES ('ad7b15f5-79ca-4174-aa81-66320def660d', 'ROLE_ADMIN'),
       ('c3f91958-c745-43ad-862c-6b9f9d28ba2e', 'ROLE_USER'),
       ('8c2ab3f3-f5b4-4bfb-9f8d-6b29910ea71a', 'ROLE_COUNTER');



INSERT INTO user_roles (id, role_id, user_credentials_id)
VALUES ('b1ac70f4-721f-4c30-89d4-e2ecb6959bbb','ad7b15f5-79ca-4174-aa81-66320def660d', 'b11a1c40-42e3-4eaf-93d0-9b77c9aaff6d'),
       ('1c3f0a9e-5bf2-4273-97bd-f97aef5d8b46','c3f91958-c745-43ad-862c-6b9f9d28ba2e','c29a1c50-52e3-4fef-83d0-0b67c9bfbf6e'),
       ('10a4f99b-dd26-46c8-8940-f7e5291122da','8c2ab3f3-f5b4-4bfb-9f8d-6b29910ea71a', 'd38b1c60-62e3-5fef-93d0-8c78c9cfcf7f');