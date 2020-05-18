alter table fitness_club
    alter column country set data type int
        USING country::integer;
