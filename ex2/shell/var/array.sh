
# File Name: array.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 03:22:40 PM CST
#########################################################################
#!/bin/zsh

# 定义方式一
names1=(n0 n1 n2 n3)
# 定义方式二
names2=(
	n0
	n1
	n2
	n3
)
# 定义方式三
names3=()
names3[0]=n0
names3[1]=n1
names3[2]=n2

# 通过下标取出元素
echo "通过下标取出元素"
echo ${names1[0]}
echo ${names2[1]}
echo ${names3[2]}

echo "通过 @ 符号取出全部元素"
# 通过 @ 符号取出全部元素
echo ${names1[@]}
# 通过 * 符号取出全部元素
echo ${names1[*]}
