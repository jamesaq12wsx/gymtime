ALTER TABLE exercise_mark
    RENAME TO exercise_post;

alter table exercise_post
    rename column mark_uuid to post_uuid;

alter table exercise_post
    rename column mark_time to post_time;