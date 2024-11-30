alter table if exists refresh_token
    add constraint FK62lsx5khav7a42vvjjti4y325
    foreign key (user_credential_id)
    references user_credentials;


alter table if exists user_roles
    add constraint FKh8ciramu9cc9q3qcqiv4ue8a6
    foreign key (role_id)
    references roles;

alter table if exists user_roles
    add constraint FKqrdkkq7sq23ic2hm467vcf8oy
    foreign key (user_credentials_id)
    references user_credentials