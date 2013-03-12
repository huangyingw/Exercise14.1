#!/bin/sh
user="root"
pass="1qaz@WSX"
db="test"
sql="test.sql"
mysql -u "$user" -p"$pass" "$db" < ./sql/test.sql
