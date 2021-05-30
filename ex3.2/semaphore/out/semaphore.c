/*************************************************************************
	> File Name: semaphore.c
	> Author: snowflake
	> Mail: 278121951@qq.com 
	> Created Time: Sat 29 May 2021 02:00:01 PM CST
 ************************************************************************/

#include <stdio.h>
#include <sys/sem.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>


union semun
{
	int 			val;
	struct semid_ds *buf;
	unsigned short *array;
	struct seminfo *__buf;
};

// 信号量标识符，默认初始化为 0
static int sem_id = 0;

static int init_semaphore();
static int del_semaphore();

static int p_semaphore();
static int v_semaphore();


/**
 *
 * 使用信号量
 *
 * 信号量初始值：1（表示向控制台输出进程的个数）
 *
 * 同时运行多个进程，每个进程启动时传递一符字符，表示这个程序向控制台
 * 输出的字符
 *
 *
 */ 
void main(int argc, char *argv[]) 
{
	char message = 'C';
	
	// 1. 初始化信号量
	sem_id = semget((key_t) 1, 1, 0666 | IPC_CREAT);

	// 2. 获取程序运行时传入的字符
	if (argc > 1)
	{
		if (!init_semaphore())
		{
			printf("程序出错！！！\n");
			exit(EXIT_FAILURE);
		}
		// 获取字符
		message = argv[1][0];
		sleep(2);
	}

	for (int i = 0; i < 10; i++)
	{

		// 进入临界区
        if (!p_semaphore())
        {
            exit(EXIT_FAILURE);
        }
 
        // 向屏幕中输出数据
        printf("%c", message);
 
        // 清理缓冲区，然后休眠随机时间
        fflush(stdout);
        sleep(rand() % 3);
 
        // 离开临界区前再一次向屏幕输出数据
        printf("%c", message);
        fflush(stdout);
 
        // 离开临界区，休眠随机时间后继续循环
        if (!v_semaphore())
        {
            exit(EXIT_FAILURE);
        }
        sleep(rand() % 2);
	}

	sleep(10);

	printf("\n%d - 结束运行！！！\n", getpid());

	if (argc > 1)
	{
		del_semaphore();
	}

	exit(EXIT_SUCCESS);
}

/**
 *
 * 初始化信号量
 *
 *
 */ 
static int init_semaphore() 
{
	union semun sem_union;
	sem_union.val = 1;

	return (semctl(sem_id, 0, SETVAL, sem_union) == -1) ? 0 : 1;
}

/**
 *
 * 删除信号量
 *
 *
 */ 
static int del_semaphore()
{
	union semun sem_union;
	if (semctl(sem_id, 0, IPC_RMID, sem_union) == -1)
	{
		printf("删除信号量失败！！！\n");
	}

}

/**
 *
 * 信号量：P操作
 *
 */ 
static int p_semaphore()
{
	struct sembuf sem_b = {
		0, -1, SEM_UNDO
	};

	if (semop(sem_id, &sem_b, 1) == -1)
	{
		printf("信号量P操作失败！！！\n");
		return 0;
	}
	return 1;
}

/**
 *
 *
 * 信号量：V操作
 *
 *
 */ 
static int v_semaphore()
{
	struct sembuf sem_b = {
		0, 1, SEM_UNDO
	};

	if (semop(sem_id, &sem_b, 1) == -1)
	{
		printf("信号量P操作失败！！！\n");
		return 0;
	}
	return 1;
}
