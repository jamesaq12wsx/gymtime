drop type fitness_brand;

create table if not exists fitness_brand(
    id uuid not null default uuid_generate_v4(),
    name varchar(500) not null,
    country int not null references country(id),
    unique (name, country)
)