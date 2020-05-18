alter table gym_time_user
    alter column user_uuid
    set default uuid_generate_v4();