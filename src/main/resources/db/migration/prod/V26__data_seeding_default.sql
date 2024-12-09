insert into roles (id, name) values ('6213920e-a2b9-466c-bc25-2b802aea8dd4', 'ROLE_USER');
insert into roles (id, name) values ('d60546d9-d41f-4822-ac62-23f65046b695', 'ROLE_ADMIN');
insert into roles (id, name) values ('76fad04b-92fb-44ce-a1ce-5d3cd18b4134', 'ROLE_PROXY');
insert into roles (id, name) values ('ae9244fc-7576-4488-b14c-e94e459f07b2', 'ROLE_LOAN_USER');

INSERT INTO public.user_credentials
(account_non_expired, account_non_locked, credentials_non_expired, enabled, id, firstname, lastname, "password", phone, username)
VALUES(true, true, true, true, 'b075725c-236b-4f1a-b229-147660bc99b7', 'Sheri', 'Weiss', '$2a$10$.X7GQOYGcYBSfU5xu9S3ZOzeNGhEDH8BpiI3VBjhpUEwoAUtwAKyC', '8557876874', 'admin@gmail.com');

INSERT INTO public.user_role
(id, role_id, user_credential_id)
VALUES('b1206f33-09e5-4b0c-9338-961562405b18', 'd60546d9-d41f-4822-ac62-23f65046b695', 'b075725c-236b-4f1a-b229-147660bc99b7');