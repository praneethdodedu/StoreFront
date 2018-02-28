create table asm_store.users(user_id Integer(20) AUTO_INCREMENT,
user_name varchar(100),password varchar(100),
primary key (user_id),
unique (user_id),
unique (user_name));

create table asm_store.catalogue(catalogue_id Integer(20) AUTO_INCREMENT,
name varchar(100),description varchar(1000),
primary key (catalogue_id),
unique (catalogue_id))

create table asm_store.Courses(course_id Integer(20) AUTO_INCREMENT,
catalogue_id Integer(20),
course_name varchar(20),course_description varchar(1000),course_overview  varchar(1000),
primary key (course_id),
unique (course_id),
CONSTRAINT FK_catId FOREIGN KEY (catalogue_id) REFERENCES asm_store.catalogue(catalogue_id))


create table asm_store.modules(module_id Integer(20) AUTO_INCREMENT,
course_id Integer(20),
module_name varchar(100),module_type varchar(100),sequence integer(100),module_description  varchar(1000),
primary key (module_id),
unique (module_id),
CONSTRAINT FK_courseId FOREIGN KEY (course_id) REFERENCES asm_store.courses(course_id))


create table asm_store.usercatalogue(ucat_id Integer(20) AUTO_INCREMENT,
catalogue_id Integer(20),
course_id Integer(20),
user_Id Integer(20),
status varchar(20),start_date DATE,end_date DATE,
primary key (ucat_id),
unique (ucat_id), 
CONSTRAINT FK_ucatalogNcourseId FOREIGN KEY (catalogue_id,course_id) REFERENCES  asm_store.courses(catalogue_id,course_id),
CONSTRAINT FK_uuserId FOREIGN KEY (user_Id) REFERENCES asm_store.users(user_Id),
CONSTRAINT FK_ucourseId FOREIGN KEY (course_id) REFERENCES asm_store.courses(course_id));




insert into asm_store.catalogue values (1,'programming','Courses related to programming will be listed here');
insert into asm_store.catalogue values (2,'gamming','Courses related to gaming will be listed here');

insert into asm_store.courses values (1,1,'J2EE','concepts of java','java concepts');
insert into asm_store.courses values (2,1,'ORACLE 11G','concepts of 11g','11g concepts');
insert into asm_store.courses values (3,2,'C Gaming','concepts of c gaming','gaming concepts');
insert into asm_store.courses values (4,2,'Java Gaming api','concepts of java gaming','gaming concepts');


insert into asm_store.modules(course_id,module_name,module_type,sequence,module_description) values (1,'OverView','primary',1,'over view of the course');
insert into asm_store.modules(course_id,module_name,module_type,sequence,module_description) values (1,'Chapter 1','self-paced',2,'Chapter one details');
insert into asm_store.modules(course_id,module_name,module_type,sequence,module_description) values (2,'OverView','self-paced',1,'over view of the course');
insert into asm_store.modules(course_id,module_name,module_type,sequence,module_description) values (2,'Chapter 1','self-paced',2,'Chapter one details');


insert into asm_store.usercatalogue values (1,1,1,'PENDING',CURDATE(),CURDATE());
insert into asm_store.usercatalogue values (3,2,1,3,'PENDING',CURDATE(),CURDATE());
insert into asm_store.usercatalogue values (4,2,1,4,'PENDING',CURDATE(),CURDATE());


insert into asm_store.usercatalogue values (,1,1,'PENDING',CURDATE(),CURDATE());
