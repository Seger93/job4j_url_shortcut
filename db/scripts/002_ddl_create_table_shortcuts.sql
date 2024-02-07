CREATE TABLE shortcuts (
                       id serial primary key not null,
                       url_Name VARCHAR(255) NOT NULL,
                       count INT default 0,
                       unique_Code VARCHAR(255) unique
);