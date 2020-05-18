alter table exercise_post
    alter column exercises type jsonb
        USING exercises::jsonb;