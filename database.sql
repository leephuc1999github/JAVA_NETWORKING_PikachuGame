-- use fashion;
-- SELECT product.idpro, product.idcate as id_Cate,
--                                product.name as product_name,
--                                 product.price, product.image,
--                                 category.idcate,
--                                 category.name as category_name,
--                                 category.description
-- from product inner join category
-- where product.idcate =  category.idcate;

-- use database1;

-- select * from tbltoy where name= 'banh';
-- use dimamon_shop;
-- SELECT * FROM products

-- drop  database btlWeb;
-- create  database btlWeb;

-- use btlWeb;


-- CREATE TABLE `User` (Id int(10) NOT NULL AUTO_INCREMENT, Name varchar(255), Email varchar(255), Address varchar(255), Position varchar(255), Account varchar(255), Password varchar(255), PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE ProCart (Id int(10) NOT NULL AUTO_INCREMENT, CartId int(10) NOT NULL, ProductId int(10) NOT NULL, Acount int(10) NOT NULL, PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE Product (Id int(10) NOT NULL AUTO_INCREMENT, CategoryId int(10) NOT NULL, Name varchar(255), Amount int(10) NOT NULL, PriceInit double NOT NULL, Price double NOT NULL, DesShort varchar(255), DesLong varchar(255), DateAdd date, PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE Cart (Id int(10) NOT NULL AUTO_INCREMENT, UserId int(10) NOT NULL, Total float NOT NULL, PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- -- CREATE TABLE `Order` (Id int(10) NOT NULL AUTO_INCREMENT, UserId int(10) NOT NULL, CartId int(10) NOT NULL, DateOrder date, PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE `Order` (Id int(10) NOT NULL AUTO_INCREMENT, CartId int(10) NOT NULL, DateOrder date, PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE Contact (Id int(10) NOT NULL AUTO_INCREMENT, address varchar(255), email varchar(255), sdt varchar(255), hottline varchar(255), PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE Logo (Id int(10) NOT NULL AUTO_INCREMENT, image varchar(255), name varchar(255), PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE Article (Id int(10) NOT NULL AUTO_INCREMENT, Name varchar(255), Image varchar(255), Des varchar(255), PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE Bill (Id int(10) NOT NULL AUTO_INCREMENT, UserId int(10) NOT NULL, OrderId int(10) NOT NULL, DatePay date, Status varchar(255), PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- CREATE TABLE Category (Id int(10) NOT NULL AUTO_INCREMENT, Name varchar(255), PRIMARY KEY (Id)) engine=InnoDB CHARACTER SET UTF8;
-- ALTER TABLE ProCart ADD CONSTRAINT FKProCart471704 FOREIGN KEY (ProductId) REFERENCES Product (Id);
-- ALTER TABLE ProCart ADD CONSTRAINT FKProCart450972 FOREIGN KEY (CartId) REFERENCES Cart (Id);
-- -- ALTER TABLE `Order` ADD CONSTRAINT FKOrder63375 FOREIGN KEY (UserId) REFERENCES `User` (Id);
-- ALTER TABLE Product ADD CONSTRAINT FKProduct365970 FOREIGN KEY (CategoryId) REFERENCES Category (Id);
-- ALTER TABLE Bill ADD CONSTRAINT FKBill70033 FOREIGN KEY (OrderId) REFERENCES `Order` (Id);
-- ALTER TABLE Bill ADD CONSTRAINT FKBill446560 FOREIGN KEY (UserId) REFERENCES `User` (Id);
-- ALTER TABLE Cart ADD CONSTRAINT FKCart424263 FOREIGN KEY (UserId) REFERENCES `User` (Id);
-- ALTER TABLE `Order` ADD CONSTRAINT FKOrder795661 FOREIGN KEY (CartId) REFERENCES Cart (Id);

drop database btlltm;
create database btlltm;
use  btlltm;
CREATE TABLE `user` (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255), username varchar(255), password varchar(255), address varchar(255), sdt varchar(255), soccer float NOT NULL, level int, onlines bit(1) NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB CHARACTER SET UTF8;
CREATE TABLE game (id int(10) NOT NULL AUTO_INCREMENT, roomId int(10) NOT NULL, timeStart int(10) NOT NULL, point float NOT NULL, note varchar(255), PRIMARY KEY (id)) ENGINE=InnoDB CHARACTER SET UTF8;
CREATE TABLE room (id int(10) NOT NULL AUTO_INCREMENT, timeInit date, name int(10), description varchar(255), PRIMARY KEY (id)) ENGINE=InnoDB CHARACTER SET UTF8;
CREATE TABLE createdRoom (id int(10) NOT NULL AUTO_INCREMENT, roomId int(10) NOT NULL, userId int(10) NOT NULL, hoster bit(1) NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB CHARACTER SET UTF8;
CREATE TABLE result (id int(10) NOT NULL AUTO_INCREMENT, gameId int(10) NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB CHARACTER SET UTF8;

ALTER TABLE createdRoom ADD CONSTRAINT FKCreatedRoo610860 FOREIGN KEY (userId) REFERENCES `user` (id);
ALTER TABLE createdRoom ADD CONSTRAINT FKCreatedRoo992293 FOREIGN KEY (roomId) REFERENCES room (id);
ALTER TABLE result ADD CONSTRAINT FKResult252972 FOREIGN KEY (gameId) REFERENCES game (id);
ALTER TABLE game ADD CONSTRAINT FKGame686702 FOREIGN KEY (roomId) REFERENCES room (id);

