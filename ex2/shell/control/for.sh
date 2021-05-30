
# File Name: for.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 05:02:15 PM CST
#########################################################################
#!/bin/zsh
as=(1 2 3 4 5)
for a in ${as[*]};
do
	echo $a
done
