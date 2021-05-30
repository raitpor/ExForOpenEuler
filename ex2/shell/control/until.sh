
# File Name: until.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 05:07:05 PM CST
#########################################################################
#!/bin/zsh

a=1

until [ $a -eq 5 ]
do
	echo $a
	let "a++"
done

