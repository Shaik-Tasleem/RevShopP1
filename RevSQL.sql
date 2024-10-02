create database RevShopP1;
use RevShopP1;
CREATE TABLE SPRING_SESSION (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BLOB NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) 
    REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);
create table Category(
categoryId int not null primary key auto_increment,
categoryName varchar(200) not null
);
select * from buyer;
select * from SPRING_SESSION_ATTRIBUTES;
insert into Category(categoryId,categoryName) values(1,'Tshirts');
insert into Category(categoryId,categoryName) values(2,'Toys');
insert into Category(categoryId,categoryName) values(3,'Shirts');
insert into Category(categoryId,categoryName) values(4,'Toys');
insert into Category(categoryId,categoryName) values(5,'WashingMachine');
insert into Category(categoryId,categoryName) values(6,'Hoodies');
insert into Category(categoryId,categoryName) values(7,'Printers');
insert into Category(categoryId,categoryName) values(8,'Books');
insert into Category(categoryId,categoryName) values(9,'VideosGames');
insert into Category(categoryId,categoryName) values(10,'Furniture');
insert into Category(categoryId,categoryName) values(11,'Laptops');
insert into Category(categoryId,categoryName) values(12,'EarBuds');

select * from buyer;

