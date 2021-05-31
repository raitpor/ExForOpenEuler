
# File Name: while.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 05:05:12 PM CST
#########################################################################
#!/bin/zsh

a=1

while(( $a<=5 ))
do
    echo $a
    let "a++"
done
