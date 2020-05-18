Create Type user_role As ENUM ('USER', 'ADMIN');

CREATE TABLE gym_time_user
(
    user_uuid   uuid PRIMARY KEY,
    username    varchar(500) unique not null,
    email       varchar(500)        not null,
    password    varchar(500)        not null,
    facebook_id varchar(500),
    is_enable   bool                not null default true,
    role        user_role           not null,
    created_at  TIMESTAMP           not null DEFAULT NOW(),
    updated_at  TIMESTAMP           not null default now()
);

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    new.created_at = old.created_at;
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

create trigger before_update_user_table
    before update
    on gym_time_user
    for each row
execute procedure trigger_set_timestamp();