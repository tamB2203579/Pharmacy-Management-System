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

create table invoice_detail(
    id int auto_increment primary key,
    history_id int not null,
    medicine_id varchar(100) not null,
    productName varchar(100) not null,
    category varchar(100) not null,
    quantity int not null,
    unit_price int not null,
    total_price int not null,
    foreign key (history_id) references history(id) on delete cascade
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


INSERT INTO Customer (fullName, phoneNum, registrationDate, loyaltyPoints) VALUES
('Nguyen Van A', '0901234567', '2024-01-15 09:30:00', 150),
('Tran Thi B', '0902345678', '2024-02-20 14:15:00', 200),
('Le Van C', '0903456789', '2024-03-10 11:45:00', 100),
('Pham Thi D', '0904567890', '2024-04-05 16:20:00', 250),
('Hoang Van E', '0905678901', '2024-05-12 08:10:00', 80),
('Vo Thi F', '0906789012', '2024-06-18 13:30:00', 180),
('Dang Van G', '0907890123', '2024-07-22 10:00:00', 120),
('Bui Thi H', '0908901234', '2024-08-01 15:45:00', 50);


-- Recent transactions (within last 7 days - for Weekly stats)
INSERT INTO history (customer_id, customerName, staffName, total, createdDate) VALUES
(4, 'Pham Thi D', 'Admin', 105000, '2025-07-27'),
(5, 'Hoang Van E', 'Admin', 115000, '2025-07-28'),
(6, 'Vo Thi F', 'Admin', 130000, '2025-07-30'),
(7, 'Dang Van G', 'Admin', 100000, '2025-08-01'),
(8, 'Bui Thi H', 'Admin', 150000, '2025-08-02');

-- Monthly transactions (within last 30 days, before current week)
INSERT INTO history (customer_id, customerName, staffName, total, createdDate) VALUES
(1, 'Nguyen Van A', 'Admin', 98000, '2025-07-05'),
(2, 'Tran Thi B', 'Admin', 108000, '2025-07-10'),
(3, 'Le Van C', 'Admin', 92000, '2025-07-15'),
(4, 'Pham Thi D', 'Admin', 110000, '2025-07-20'),
(5, 'Hoang Van E', 'Admin', 102000, '2025-07-22');

-- Older transactions (beyond 30 days but within 365 days - for Yearly stats)
INSERT INTO history (customer_id, customerName, staffName, total, createdDate) VALUES
(6, 'Vo Thi F', 'Admin', 95000, '2025-06-10'),
(7, 'Dang Van G', 'Admin', 88000, '2025-04-25'),
(8, 'Bui Thi H', 'Admin', 99000, '2025-03-12'),
(1, 'Nguyen Van A', 'Admin', 100000, '2025-01-28'),
(2, 'Tran Thi B', 'Admin', 97000, '2024-12-15'),
(3, 'Le Van C', 'Admin', 105000, '2024-11-02');

-- January 2025
INSERT INTO history (customer_id, customerName, staffName, total, createdDate) VALUES
(1, 'Nguyen Van A', 'Admin', 100000, '2025-01-03'),
(2, 'Tran Thi B', 'Admin', 95000, '2025-01-06'),
(3, 'Le Van C', 'Admin', 110000, '2025-01-09'),
(4, 'Pham Thi D', 'Admin', 120000, '2025-01-15'),
(5, 'Hoang Van E', 'Admin', 90000, '2025-01-21'),
(6, 'Vo Thi F', 'Admin', 105000, '2025-01-26'),

-- February 2025
(1, 'Nguyen Van A', 'Admin', 85000, '2025-02-01'),
(2, 'Tran Thi B', 'Admin', 98000, '2025-02-04'),
(3, 'Le Van C', 'Admin', 112000, '2025-02-08'),
(4, 'Pham Thi D', 'Admin', 130000, '2025-02-11'),
(5, 'Hoang Van E', 'Admin', 95000, '2025-02-19'),
(6, 'Vo Thi F', 'Admin', 107000, '2025-02-25'),

-- March 2025
(1, 'Nguyen Van A', 'Admin', 97000, '2025-03-02'),
(2, 'Tran Thi B', 'Admin', 100000, '2025-03-07'),
(3, 'Le Van C', 'Admin', 110000, '2025-03-13'),
(4, 'Pham Thi D', 'Admin', 108000, '2025-03-20'),
(5, 'Hoang Van E', 'Admin', 115000, '2025-03-26'),

-- April 2025
(6, 'Vo Thi F', 'Admin', 123000, '2025-04-03'),
(1, 'Nguyen Van A', 'Admin', 99000, '2025-04-09'),
(2, 'Tran Thi B', 'Admin', 88000, '2025-04-15'),
(3, 'Le Van C', 'Admin', 119000, '2025-04-20'),
(4, 'Pham Thi D', 'Admin', 125000, '2025-04-27'),

-- May 2025
(5, 'Hoang Van E', 'Admin', 117000, '2025-05-05'),
(6, 'Vo Thi F', 'Admin', 94000, '2025-05-12'),
(1, 'Nguyen Van A', 'Admin', 99000, '2025-05-18'),
(2, 'Tran Thi B', 'Admin', 102000, '2025-05-25'),

-- June 2025
(3, 'Le Van C', 'Admin', 111000, '2025-06-01'),
(4, 'Pham Thi D', 'Admin', 93000, '2025-06-10'),
(5, 'Hoang Van E', 'Admin', 118000, '2025-06-17'),
(6, 'Vo Thi F', 'Admin', 97000, '2025-06-24'),

-- July 2025
(1, 'Nguyen Van A', 'Admin', 105000, '2025-07-02'),
(2, 'Tran Thi B', 'Admin', 99000, '2025-07-08'),
(3, 'Le Van C', 'Admin', 102000, '2025-07-16'),
(4, 'Pham Thi D', 'Admin', 120000, '2025-07-22'),
(5, 'Hoang Van E', 'Admin', 91000, '2025-07-29'),

-- August 2025
(6, 'Vo Thi F', 'Admin', 113000, '2025-08-01');

SET SQL_SAFE_UPDATES = 0; /*Run this command in MySQL before build and run the application*/
