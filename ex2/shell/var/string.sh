
# File Name: string.sh
# Author: amoscykl
# mail: amoscykl980629@163.com
# Created Time: Sun 30 May 2021 03:44:56 PM CST
#########################################################################
#!/bin/zsh

# 单引号形式
name1='name1'
echo ${name1}

# 双引号形式
name2="name2"
echo ${name2}

# 单引号字符串无法引用变量
# name3='new name: ${name1}'
# echo ${name3}

# 单引号字符串中不能出现单个单引号
# name4='''
# echo ${name4}

# 单引号字符串可以用来拼接字符串
name5='first: '${name1}', second: '${name2}
echo ${name5}

# 双引号字符串可以引用变量
name6="new name: ${name1}"
echo ${name6}

# 双引号字符串可以出现转义后的双引号
name7="\""
echo ${name7}

# 双引号无法用来拼接字符串
name8="first: "${name1}", second: "${name2}
echo ${name8}

