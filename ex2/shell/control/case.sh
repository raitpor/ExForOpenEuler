
# File Name: case.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 05:09:38 PM CST
#########################################################################
#!/bin/zsh

a=1

echo "a = $a"

case $a in
	1) echo "1"
	;;
	*) echo "2"
	;;
esac

