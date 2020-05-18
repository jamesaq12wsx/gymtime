create table if not exists exercise_post_exercise (
    post_uuid uuid primary key references exercise_post(post_uuid),
    name varchar(200) not null,
    description varchar(500) default ''
);