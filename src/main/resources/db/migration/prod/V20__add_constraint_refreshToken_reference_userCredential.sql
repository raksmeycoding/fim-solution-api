alter table if exists refresh_token
    add constraint FK62lsx5khav7a42vvjjti4y325
    foreign key (user_credential_id)
    references user_credentials