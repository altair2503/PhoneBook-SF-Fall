insert into roles (name)
values
    ('ROLE_USER_READER'), ('ROLE_ADMIN');

insert into user (username, password, email, phone)
values
    ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com','87475475212'),
    ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com','87756209138');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);