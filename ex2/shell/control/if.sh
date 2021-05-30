
# File Name: if.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 05:00:03 PM CST
#########################################################################
#!/bin/zsh

a=1
b=2

if [ $a -eq $b ]
then
	echo "a=$a, b=$b => a == b"
else
	echo "a=$a, b=$b => a != b"
fi
