#!/bin/bash
command="INSERT IGNORE INTO computer-database-db2.company (id,name) VALUES "
for i in {1..1000}
do
   command="$command, ($i,CONCAT('companyTest',$i))"
done
echo $command > insertCompany.sql