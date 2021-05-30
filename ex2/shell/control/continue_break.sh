
# File Name: continue_break.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 05:15:34 PM CST
#########################################################################
#!/bin/zsh


a=1

while :
do

	echo "a = $a"
	if [ $a -eq 3 ] 
	then
		echo "break"
		break;
	else
		let "a++"
		echo "continue"
		continue
	fi
done
