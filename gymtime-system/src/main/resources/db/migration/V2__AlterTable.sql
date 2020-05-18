ALTER TABLE fitness_club
    ALTER COLUMN club_uid SET DATA TYPE UUID USING (uuid_generate_v4()),
    alter column club_brand set not null,
    alter column club_id set not null,
    alter column club_name set not null,
    alter column latitude set not null,
    alter column longitude set not null;

ALTER TABLE fitness_club
    ADD CONSTRAINT brand_id_constrain UNIQUE (club_brand, club_id);

alter table fitness_club
    drop column brandid restrict;