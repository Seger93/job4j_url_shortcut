create table if not exists Website (
    id       serial primary key not null,
    site varchar(2000),
    login    varchar(2000) unique,
    password varchar(2000)
);