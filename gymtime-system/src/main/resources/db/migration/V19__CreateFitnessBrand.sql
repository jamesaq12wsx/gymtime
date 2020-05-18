alter table fitness_club
    alter column club_brand type varchar(100);

drop type fitness_brand;

create table if not exists fitness_brand(
    id serial not null,
    name varchar(500) not null,
    country int not null references country(id),
    unique (name, country)
);
