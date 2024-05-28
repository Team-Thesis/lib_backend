create schema if not exists sru_library;

Use sru_library;
Set names 'UTF8MB4';
#1
#========================================================
# crate table degree level
#Drop table if exists degree_level;
create table if not exists degree_level(
    degree_level_id varchar(10) primary key auto_increment,
    degree_level varchar(100) not null
);

#2
#========================================================
# crate table collages
#Drop table if exists collages;
create table if not exists colleges(
    collage_id varchar(10) primary key,
    collage_name varchar(100)
);

#3
#========================================================
# crate table major
#Drop table if exists majors;
create table if not exists majors(
    major_id varchar(10) primary key,
    major_name varchar(100) not null,
    collage_id int,
    foreign key (collage_id) references colleges(collage_id) on delete cascade on update cascade
);

#4
#========================================================
# crate table students
#Drop table if exists students;
create table if not exists students(
    student_id bigint primary key,
    student_name varchar(60) not null,
    gender varchar(10) not null ,
    date_of_birth date not null ,
    degree_level_id varchar(10) not null ,
    major_id int not null ,
    generation int,
    foreign key (degree_level_id)
        references degree_level(degree_level_id)
        on delete cascade
        on update cascade ,
    foreign key (major_id)
        references majors(major_id)
        on delete cascade
        on update cascade
);


#5
#========================================================
# crate table language
#Drop table if exists language;
create table if not exists language(
    language_id varchar(5) primary key,
    language_name varchar(20)
);


#========================================================
# crate table books
#Drop table if exists books;
create table if not exists books(
    book_id varchar(10) primary key,
    book_title varchar(100),
    number int not null,
    language_id varchar(5),
    college_id varchar(10),
    book_type varchar(100),
    foreign key (college_id) references colleges(collage_id)
                 on update cascade
                 on delete cascade ,
    foreign key (language_id) references language(language_id)
                 on delete cascade
                 on update cascade
);

#========================================================
# crate table borrow books
#Drop table if exists borrow_books;
Create table if not exists borrow_books(
    borrow_id int primary key auto_increment,
    book_id varchar(10) not null ,
    student_id bigint not null ,
    borrow_date date not null ,
    give_back_date date,
    is_bring_back boolean,
    foreign key (book_id) references books(book_id) on update cascade ,
    foreign key (student_id) references students(student_id) on update cascade
);

#========================================================
#crate table guest
create table if not exists book_sponsors(
    sponsor_id int primary key auto_increment,
    sponsor_name varchar(50) not null
);

#========================================================

create table if not exists book_sponsorships(
    PRIMARY KEY (book_id, sponsor_id),
    book_id varchar(10),
    sponsor_id int,
    number_of_books INT NOT NULL,
    book_type VARCHAR(50) NOT NULL,
    sponsor_date DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (sponsor_id) REFERENCES book_sponsors(sponsor_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

#========================================================
# crate table attend and exit
Create table if not exists attend(
    attend_id bigint primary key auto_increment,
    student_id bigint not null ,
    entry_times timestamp null ,
    exiting_times timestamp null ,
    date date not null ,
    purpose varchar(50) not null ,
    foreign key (student_id) references students(student_id)
);


#9
#========================================================
# crate table guest
#Drop table if exists guests;
create table if not exists guests(
    guest_id int primary key auto_increment,
    guest_name varchar(60) not null,
    gender varchar(8),
    position varchar(30)
);

#11
#========================================================
#crate table user
create table if not exists users(
    user_id bigint auto_increment primary key not null ,
    username varchar(50) unique not null ,
    password varchar(255) not null ,
    roles enum('USER', 'ADMIN') not null
);

SELECT COUNT(*) from attend where date = '2024-05-28'
