
# File Name: function.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 05:24:30 PM CST
#########################################################################
#!/bin/zsh


# 无参无返回值
function print_shell()
{
	echo "shell"
}

# 有参无返回值
function print_()
{
	echo "第一个参数: $1"
	echo "第二个参数: $2"
}

# 无参有返回值
function return_shell()
{
	return 2
}

# 有参有返回值
function sum()
{
	return `expr $1 + $2`
}


echo "调用无参无返回值"
print_shell
echo "调用有参无返回值"
print_ hello shell
echo "调用无参有返回值"
return_shell
echo $?
echo "调用有参有返回值"
sum 1 2
echo $?

