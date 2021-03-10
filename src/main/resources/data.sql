create table users(
	username varchar(50) not null primary key,
	password varchar(500) not null,
	enabled boolean not null
);

create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

insert into Category(name) values ('Piłka nożna')
insert into Category(name) values ('Koszykówka')
insert into Category(name) values ('Tenis ziemny')
insert into Category(name) values ('Siatkówka')
insert into Category(name) values ('Tenis stołowy')

insert into spontan.appUser(email,name,passowrd) values ('admin','admin','admin')
insert into event(duration_of_the_event,event_start,name,place,quantity_of_players)
    values ('2020-12-12 15:00','2020-12-12 15:00','Pilka z rana jak smietana','orlik',5)