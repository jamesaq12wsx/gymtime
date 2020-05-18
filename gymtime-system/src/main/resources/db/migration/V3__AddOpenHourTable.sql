alter table fitness_club
    drop column open_hour restrict;

create table if not exists open_hour(
    club_uid UUID not null references fitness_club(club_uid),
    monday varchar(100) default '',
    tuesday varchar(100) default '',
    wednesday varchar(100) default '',
    thursday varchar(100) default '',
    friday varchar(100) default '',
    saturday varchar(100) default '',
    sunday varchar(100) default ''
)