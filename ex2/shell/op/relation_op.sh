
# File Name: relation_op.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 04:46:37 PM CST
#########################################################################
#!/bin/zsh

#!/bin/bash

a=1
b=2

# -eq
if [ $a -eq $b ] 
then
	echo "$a -eq $b : a 等于 b"
else
	echo "$a -eq $b : a 不等 b"
fi
# -ne
if [ $a -ne $b ] 
then
	echo "$a -ne $b : a 不等 b"
else
	echo "$a -ne $b : a 不等 b"
fi
# -gt
if [ $a -gt $b ] 
then
	echo "$a -gt $b : a 大于 b"
else
	echo "$a -gt $b : a 小于 b"
fi
# -lt
if [ $a -lt $b ] 
then
	echo "$a -lt $b : a 小于 b"
else
	echo "$a -lt $b : a 大于 b"
fi
# -ge
if [ $a -ge $b ] 
then
	echo "$a -ge $b : a 大于等于 b"
else
	echo "$a -ge $b : a 小于 b"
fi
# -le
if [ $a -le $b ] 
then
	echo "$a -le $b : a 小于等于 b"
else
	echo "$a -le $b : a 大于 b"
fi



