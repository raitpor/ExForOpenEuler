/*************************************************************************
	> File Name: print_pcb.c
	> Author: snowflake
	> Mail: 278121951@qq.com 
	> Created Time: Sat 29 May 2021 08:15:25 PM CST
 ************************************************************************/

#include <stdio.h>
#include <linux/sched.h>
#include <sys/pid.h>

void main()
{

    struct task_struct * task = pid_task(getpid(), PIDTYPE_PID);    //获取进程的任务描述符信息

	printf("%d", task->state);

}



