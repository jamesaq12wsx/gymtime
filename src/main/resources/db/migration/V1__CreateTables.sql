create extension if not exists "uuid-ossp";

Create Type week_day As ENUM ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY');

Create Type fitness_brand as enum ('LA_FITNESS', 'TWENTY_FOUR_HR_FITNESS');

Create Type us_state as enum (
    'AL', 'AK','AZ', 'AR','CA', 'CO','CT', 'DE','DC', 'FL','GA', 'HI','ID', 'IL','IN', 'IA',
    'KS','KY', 'ME','MD', 'MA','MI', 'MN','MS', 'MO','MT', 'NE','NV', 'NH','NJ', 'NM',
    'NY', 'NC','ND', 'OH','OK', 'OR','PA', 'RI','SC', 'SD','TN', 'TX','UT', 'VT','VA', 'WA',
    'WV', 'WI', 'WY'
    );

CREATE TYPE open_hour as
(
    day   week_day,
    hours varchar(50)
);


CREATE TABLE fitness_club
(
    club_uid      uuid PRIMARY KEY,
    club_brand    fitness_brand,
    club_id       integer,
    club_name     varchar(500),
    latitude      double precision,
    longitude     double precision,
    club_status   integer,
    brandId       integer,
    club_home_url varchar(500),
    zip_code      varchar(500),
    address       varchar(500),
    city          varchar(500),
    state         us_state,
    open_hour     open_hour[]
);