drop type if exists open_hour;

create extension if not exists hstore;

alter table fitness_club
    alter column city set default '',
    alter column zip_code set default '',
    add column open_hours hstore;
