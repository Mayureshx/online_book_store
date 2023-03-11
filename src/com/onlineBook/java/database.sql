create database OnlineBookStore;
use OnlineBookStore;

CREATE TABLE buyer (
    buyerId int primary key auto_increment,
    userName varchar(40),
    passWord VARCHAR(20),
    address VARCHAR(225),
    phone long
);
select * from buyer;
insert into buyer values (777, "Mayuresh", "Mayur123","Pune", 7448100838);
CREATE TABLE seller (
    sellerId int primary key auto_increment,
    sell_userName varchar(40),
    passWord VARCHAR(20),
    store_name varchar(225),
    address VARCHAR(225),
    phone long
);

insert into seller values(55,"Tino",321,"Book Land","Pune",8899012345);


CREATE TABLE books (
    book_id int primary key ,
    book_name varchar(40),
    price int,
    quantity VARCHAR(225),
    book_type long,
    author varchar(225),
     shop_id int
);

insert into books values (190, "Java Fullstack", 2000, 17, "Course", "Tyler dowm",111);
drop table books;
select * from books;
SELECT * FROM books WHERE book_name="java fullstack";


SELECT * FROM buyer;
SELECT * FROM seller;


create table delivery (
 book_id int,
 delivery_id varchar(225),
 buyer_id int,
 quantity int,
 address varchar(225),
 shop_id int,
 order_date date,
 order_delievry_date date
 );


select * from delivery;
SELECT * FROM books WHERE author="Tyler dowm" and quantity>0;