create table fitness_club_open_hour
(
    club_uid  UUID not null unique references fitness_club (club_uid),
    monday    varchar(100) default '',
    tuesday   varchar(100) default '',
    wednesday varchar(100) default '',
    thursday  varchar(100) default '',
    friday    varchar(100) default '',
    saturday  varchar(100) default '',
    sunday    varchar(100) default ''
)