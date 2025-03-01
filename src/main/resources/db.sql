create table users
(
    username varchar(15) primary key,
    role     varchar(6) check ( role in ('ADMIN', 'USER') )
);


create table drugs
(
    id          serial primary key,
    name        varchar(250) not null,
    description text,
    created_by  varchar(20),
    status varchar(11) check ( status in ('AVAILABLE', 'DISCARDED', 'POSTPONED') ),
    created_at  timestamp default CURRENT_TIMESTAMP,
    updated_at  timestamp default CURRENT_TIMESTAMP,
    constraint fk_created_by foreign key (created_by) references users (username) on delete cascade
);

create table image
(
    id      serial primary key,
    url     text not null,
    drug_id int  not null,
    created_by varchar(20),
    foreign key (drug_id) references drugs (id) on delete cascade,
    foreign key (created_by) references users(username) on delete cascade
);

