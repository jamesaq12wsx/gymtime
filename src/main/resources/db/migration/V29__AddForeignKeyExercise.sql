alter table exercise
    add constraint fk_created_by foreign key (created_by) references gym_time_user(username);