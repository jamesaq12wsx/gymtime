create table if not exists country
(
    id               serial       not null primary key,
    name             varchar(100) not null,
    alpha_two_code   varchar(100) not null,
    alpha_three_code varchar(100) not null,
    region           varchar(100) not null,
    numeric_code     varchar(100) not null,
    flag_url         varchar(300)
);

alter table fitness_club
    add column country varchar;