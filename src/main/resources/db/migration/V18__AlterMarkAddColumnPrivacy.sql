alter table exercise_mark
    add column privacy post_privacy not null default 'PRIVATE'::post_privacy;