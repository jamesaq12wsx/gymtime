alter table fitness_brand
    add column icon varchar(500) default '',
    add column created_by varchar(500) references gym_time_user(username) default 'system',
    add column created_at timestamp default now(),
    add column updated_at timestamp default now();