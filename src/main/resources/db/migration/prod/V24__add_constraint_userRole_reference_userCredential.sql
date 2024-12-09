alter table if exists user_role
    add constraint FKki3xsofxewe0nk3oirdqu2bru
    foreign key (user_credential_id)
    references user_credentials