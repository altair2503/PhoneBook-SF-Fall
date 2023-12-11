create table if not exists users (
    id bigserial,
    username varchar(30) not null unique,
    password varchar(80) not null,
    email varchar(50) unique,
    phone varchar(50) unique,
    primary key (id)
);

create table if not exists roles (
    id serial,
    name varchar(50) not null,
    primary key (id)
);

CREATE TABLE if not exists users_roles (
    user_id bigint not null,
    role_id int not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id) ON DELETE CASCADE,
    foreign key (role_id) references roles (id) ON DELETE CASCADE
);