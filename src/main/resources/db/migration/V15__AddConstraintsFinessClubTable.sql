alter table fitness_club
    add constraint fk_clubs_countries foreign key (country) references country(id);