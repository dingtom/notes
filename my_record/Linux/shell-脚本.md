找到./anaconda的pid并kill
```
sp_pid=`ps -ef | grep ./anaconda | grep -v anaconda3/ | awk '{print $2}'`
if [ -z "$sp_pid" ];
then
 echo "[ not find sp-tomcat pid ]"
else
 echo "find result: $sp_pid "
 kill -9 $sp_pid
fi
```
