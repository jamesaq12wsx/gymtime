alter table exercise
    drop column category;

drop type exercise_category;

create extension if not exists citext;

create table exercise_category(
    category_id serial not null,
    name citext not null unique
)