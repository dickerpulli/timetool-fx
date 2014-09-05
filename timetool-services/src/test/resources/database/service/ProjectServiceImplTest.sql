insert into customer (id,name) values (1,'customer1');
insert into project (id,name,customer_id,active) values (1,'project1',1,true);
insert into project (id,name,customer_id,active) values (2,'project2',1,false);
insert into project (id,name,customer_id,active) values (3,'project3',1,false);
insert into timeslot (id,starttime,endtime,project_id,active) values (1,'2009-08-20 12:00:00','2009-08-20 12:00:00',3,true);