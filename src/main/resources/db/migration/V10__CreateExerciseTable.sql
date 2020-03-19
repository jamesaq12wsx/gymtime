create type exercise_category as enum (
    'ABS','BACK','BICEPS','CALF','CHEST','FOREARMS','LEGS','SHOULDERS','TRICEPS','CARDIO'
    );

create table if not exists exercise
(
    id          SERIAL PRIMARY KEY,
    name        varchar(100),
    description varchar(100),
    category    exercise_category             not null,
    created_by  varchar(100) default 'System' not null,
    created_at  timestamp    default now()    not null,
    updated_at  timestamp    default now()    not null,
    unique (name, category)
);

create trigger before_update_exercise_table
    before update
    on exercise
    for each row
execute procedure trigger_set_timestamp();