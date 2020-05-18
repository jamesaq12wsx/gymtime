alter table fitness_club
    alter column club_brand type int
        using club_brand::integer,
    add constraint fitness_club_brand_pkey foreign key (club_brand) references fitness_brand (id);