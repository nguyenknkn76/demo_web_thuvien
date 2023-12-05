-- USER 
create table user (
    id int primary key auto_increment, 
    name varchar(255),
    email varchar(255),
    username varchar(255) unique,
    password varchar(255), 
    role varchar(255)
)

insert into user(name, email, username, password, role) values ("nguyen","nguyen@gmail.com","nguyen","123456","admin");
insert into user(name, email, username, password, role) values ("khoi","nguyen@gmail.com","khoi","123456","user");

-- BOOK
-- id, tieu de, tac gia, the loai, mo ta, ngay phat hnah , so trang , so luong da ban, bia sach
create database book(
    id int primary key auto_increment,
    title varchar(255), 
    author varchar(255),
    category varchar(255),
    description varchar(255), 
    publishdate date, 
    numberofpage int,
    sold int,
    cover varchar(255)
)

-- RATING
create database rating(
    id int primary key auto_increment,
    userid int, 
    username varchar(255),
    bookid int,
    rating int, 
    comment varchar(255)
)

--ORDER
create table userorder(
    id int primary key auto_increment,
    userid int, 
    bookid int,
    bookname varchar(255),
    quantity int, 
    status int

)