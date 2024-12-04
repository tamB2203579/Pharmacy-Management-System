create database Pharmacy;
use Pharmacy;

create table Admin(
	id int auto_increment primary key,
    username varchar(100) not null,
    password varchar(50) not null
);

insert into Admin(username, password) VALUES ('admin', '123456');

create table Customer (
    id int auto_increment primary key,
    fullName varchar(100) not null,
    phoneNum varchar(20) not null,
    registrationDate timestamp default current_timestamp,
    -- total DOUBLE DEFAULT 0.00,
    loyaltyPoints INT DEFAULT 0
);

create table medicine(
	id int auto_increment primary key,
    medicine_id varchar(100) not null,
    productName varchar(100) not null,
    category varchar(100) not null,
    quantity int not null,
    price int not null,
    status varchar(100) not null
);

create table purchase(
    id int auto_increment primary key,
    customer_id int not null,
    medicine_id varchar(100) not null,
    productName varchar(100) not null,
    category varchar(100) not null,
    quantity int not null,
    price int not null
);

create table history(
    id int auto_increment primary key,
    customer_id int not null,
    customerName varchar(100) not null,
    staffName varchar(100) not null,
    total int not null,
    createdDate Date not null
);

select * from Admin;
select * from Customer;
select * from medicine;
select * from purchase;
select * from history;

INSERT INTO users (id, username, password) VALUES (1, 'admin', '123');

INSERT INTO medicine (medicine_id, productName, category, quantity, price, status)
VALUES
    ('A01', 'Azure', 'Hydrocodone', 500, 1500, 'Available'),
    ('A02', 'Panadol', 'Metformin', 600, 400, 'Available'),
    ('A03', 'Telinol', 'Antibiotics', 450, 400, 'Available'),
    ('A04', 'Aspirin', 'Losartan', 550, 270, 'Available'),
    ('A05', 'Ventolin', 'Albuterol', 0, 600, 'Not Available'),
    ('A06', 'Codeine', 'Hydrocodone', 450, 2000, 'Available'),
    ('A07', 'Glycomet', 'Metformin', 500, 450, 'Available'),
    ('A08', 'Ciprofloxacin', 'Antibiotics', 600, 350, 'Not Available'),
    ('A09', 'Cozaar', 'Losartan', 650, 320, 'Available'),
    ('A10', 'Salbutamol', 'Albuterol', 0, 650, 'Not Available');

SET SQL_SAFE_UPDATES = 0; /*Chay cai nay trong MySql truoc roi moi chay app*/
