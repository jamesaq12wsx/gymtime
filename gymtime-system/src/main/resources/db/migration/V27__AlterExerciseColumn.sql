alter table exercise
    drop images;

alter table exercise
    add column images jsonb default '[]'::jsonb;
