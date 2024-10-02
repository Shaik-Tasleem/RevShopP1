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
    ATTRIBUTE_BYTE BLOB NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) 
    REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);
create table Category(
CategoryId int not null primary key auto_increment,
CategoryName varchar(200) not null
);
insert into categories(CategoryId,CategoryName) values(1,'Tshirts');
insert into categories(CategoryId,CategoryName) values(2,'Toys');
insert into categories(CategoryId,CategoryName) values(3,'Shirts');
insert into categories(CategoryId,CategoryName) values(4,'Toys');
insert into categories(CategoryId,CategoryName) values(5,'WashingMachine');
insert into categories(CategoryId,CategoryName) values(6,'Hoodies');
insert into categories(CategoryId,CategoryName) values(7,'Printers');
insert into categories(CategoryId,CategoryName) values(8,'Books');
insert into categories(CategoryId,CategoryName) values(9,'VideosGames');
insert into categories(CategoryId,CategoryName) values(10,'Furniture');
insert into categories(CategoryId,CategoryName) values(11,'Laptops');
insert into categories(CategoryId,CategoryName) values(12,'EarBuds');

select * from buyer;
select * from SPRING_SESSION_ATTRIBUTES;
