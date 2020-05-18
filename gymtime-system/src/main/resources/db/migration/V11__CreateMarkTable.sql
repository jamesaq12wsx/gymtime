create table if not exists exercise_mark
(
    mark_uuid uuid                                     not null default uuid_generate_v4() primary key ,
    user_uuid  uuid references gym_time_user (user_uuid) not null,
    location  uuid references fitness_club (club_uid),
    mark_time timestamp                                not null,
    exercises hstore
)