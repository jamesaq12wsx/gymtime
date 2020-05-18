alter table fitness_club
    alter column club_uid set default uuid_generate_v4();