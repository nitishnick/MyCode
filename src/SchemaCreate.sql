 CREATE DATABASE tradingdb;

 
use tradingdb;

 drop table if exists `grouped_trade_mapping` ; 
CREATE TABLE IF NOT EXISTS `grouped_trade_mapping` (

  `group_id` int(30) NOT NULL auto_increment,   
  `sum_quantity` int(30) NOT NULL default '0',       
  `avg_limit_price` varchar(250)  NOT NULL,     
  `order_instructions_joined` varchar(1000) NOT NULL,    
   PRIMARY KEY  (`group_id`)

);

drop table if exists `trade` ; 
CREATE TABLE IF NOT EXISTS `trade` (

  `order_id` int(30) NOT NULL auto_increment,   
   `group_id` int(30) NOT NULL ,  
  `security_id` varchar(250) NOT NULL default '0',       
  `transaction_code` varchar(50)  NOT NULL ,     
  `symbol`  varchar(50) NOT NULL,     
  `quantity` int(30)  NOT NULL,    
  `limit_price`  int(30) NOT NULL ,
  `order_instructions` varchar(250) NOT NULL ,    
   PRIMARY KEY  (`order_id`)

);

drop table if exists `grouped_order_warning`;
create table if not exists  `grouped_order_warning`(
`id` int(30) NOT NULL auto_increment,   
`message` varchar(2000) not null,
code int (30) null,
primary key(`id`));


select * from grouped_order_warning
/*
select this_.group_id as group1_1_1_, this_.avg_limit_price as avg2_1_1_, this_.order_instructions_joined as order3_1_1_,
 this_.sum_quantity as sum4_1_1_, trades2_.group_id as group8_3_, trades2_.order_id as order1_3_,
 trades2_.order_id as order1_0_0_, trades2_.group_id as group8_0_0_, trades2_.limit_price as limit2_0_0_,
 trades2_.order_instructions as order3_0_0_, trades2_.quantity as quantity0_0_, trades2_.security_id as security5_0_0_,
 trades2_.symbol as symbol0_0_, trades2_.transaction_code as transact7_0_0_ from
 tradingdb.grouped_trade_mapping this_ left outer join trade trades2_ on this_.group_id=trades2_.group_id 
 
 */









