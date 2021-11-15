drop table nypd;
drop table codes;
create table nypd(
    street string, code string, pi int, pk int, ci int, ck int, mi int, mk int, ai int, ak int
    ) 
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
    STORED AS TEXTFILE
    location 'output_mr3';

create table codes(code string, borough string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE;
load data inpath "input/datasource1" into table codes;
select * from nypd limit 10;
select * from codes limit 10;

select * from nypd where (select codes.borough from codes where codes.code == nypd.code) == "MANHATTAN";

DROP TEMPORARY MACRO IF EXISTS isNumeric;

CREATE TEMPORARY MACRO isNumeric (input INT)
 select 'asd';
END
;

CREATE TEMPORARY MACRO fn_maskNull(input decimal(25,3))
    CASE
        WHEN input IS NULL THEN 0 else input
    END;

select *, nypd.ai+nypd.ak as injured_killed from nypd sort by nypd.ak desc limit 10;

select nypd.street as street, nypd.code as code,  nypd.pk+nypd.pi as pedenstials, nypd.ci+nypd.ck as cyclists, nypd.mi+nypd.mk as motorists from nypd where (select codes.borough from codes where codes.street == nypd.code) == "MANHATTAN";