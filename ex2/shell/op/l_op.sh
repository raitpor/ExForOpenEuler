
# File Name: l_op.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 04:55:19 PM CST
#########################################################################
#!/bin/zsh

a=1
b=2

# &&
if [[ $a -eq 1 && $b -eq 1 ]]
then
	echo "$a == 1 && $b == 1 FALSE"
else
	echo "$a != 1 || $b != 1 TRUE"
fi

# ||
if [[ $a -eq 1 || $b -eq 1 ]]
then
	echo "$a == 1 || $b == 1 TRUE"
else
	echo "$a != 1 && $b != 1 FALSE"
fi

