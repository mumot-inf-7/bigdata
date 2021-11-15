drop table nypd;
create table if not exists nypd(
    street string, code string, pi int, pk int, ci int, ck int, mi int, mk int, ai int, ak int
    ) 
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
    STORED AS TEXTFILE;

load data inpath "output_mr3" into table nypd;


create table if not exists codes(code string, borough string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE location 'input/datasource4';
select * from nypd limit 10;
select * from codes limit 10;

drop view manhattan_1;
create view manhattan_1 as select nypd.street as street, nypd.code as code,  nypd.pk+nypd.pi as pedestrians, nypd.ci+nypd.ck as cyclists, nypd.mi+nypd.mk as motorists from nypd where (select codes.borough from codes where codes.code == nypd.code) == "MANHATTAN";

drop view manhattan;
create view manhattan as select m.street as street, sum(m.pedestrians) as pedestrians, sum(m.cyclists) as cyclists, sum(m.motorists) as motorists from manhattan_1 as m group by m.street;

drop view pedestrians;
create view pedestrians as select 'pedestrians' as type, m.street as street, m.pedestrians as sum from manhattan as m order by m.pedestrians desc limit 3;

drop view cyclists;
create view cyclists as select 'cyclists' as type, m.street as street, m.cyclists as sum from manhattan as m order by sum desc limit 3;

drop view motorists;
create view motorists as select 'motorists' as type, m.street as street, m.motorists as sum from manhattan as m order by sum desc limit 3;

select * from pedestrians union all select * from cyclists union all select * from motorists;

drop table results_ext;
create external table results_ext (type string, street string, sum int)
row format serde 'org.apache.hadoop.hive.serde2.JsonSerDe'
stored as textfile
location 'output6';

insert overwrite table results_ext select * from pedestrians union all select * from cyclists union all select * from motorists;