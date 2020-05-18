alter table exercise_mark
    drop user_uuid;

alter table exercise_mark
    add column username varchar(100) references gym_time_user(username);