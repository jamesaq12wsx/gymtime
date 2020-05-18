alter table exercise_category
    add primary key (category_id);

create table exercise_category_exercise(
    category_id int references exercise_category(category_id),
    exercise_id int references exercise(id),
    constraint exercise_category_exercise_pkey primary key (category_id, exercise_id)
)