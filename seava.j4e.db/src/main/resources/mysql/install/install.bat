
@echo off
call ../../../_lib/liquibase/liquibase --defaultsFile=../../../_lib/mysql.properties --classpath=../../../_lib/jdbc-mysql.jar --changeLogFile=install.xml update
pause...
