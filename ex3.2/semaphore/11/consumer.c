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
static int sem_id_consumer = 0;
static int sem_id_procduer = 0;

static int init_semaphore(int sem_id, int count);
static int del_semaphore(int sem_id);

static int p_semaphore(int sem_id);
static int v_semaphore(int sem_id);


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
	// 1. 初始化信号量
	sem_id_consumer = semget((key_t) 1, 1, 0666 | IPC_CREAT);
	sem_id_procduer = semget((key_t) 2, 1, 0666 | IPC_CREAT);

	// 2. 获取程序运行时传入的字符
	if (!init_semaphore(sem_id_consumer, 0))
	{
		printf("程序出错！！！\n");
		exit(EXIT_FAILURE);
	}
	if (!init_semaphore(sem_id_procduer, 1))
	{
		printf("程序出错！！！\n");
		exit(EXIT_FAILURE);
	}

	for (int i = 0; i < 10; i++)
	{

		// 进入临界区
        if (!p_semaphore(sem_id_consumer))
        {
            exit(EXIT_FAILURE);
        }

        printf("[%d] - consumer\n", getpid());
		fflush(stdout);

		sleep(1);

		if (!v_semaphore(sem_id_procduer)) 
		{
			exit(EXIT_FAILURE);
		}
	}

	sleep(2);

	printf("\n%d - 结束运行！！！\n", getpid());

	del_semaphore(sem_id_procduer);
	del_semaphore(sem_id_consumer);

	exit(EXIT_SUCCESS);
}

/**
 *
 * 初始化信号量
 *
 *
 */ 
static int init_semaphore(int sem_id, int count) 
{
	union semun sem_union;
	sem_union.val = count;

	return (semctl(sem_id, 0, SETVAL, sem_union) == -1) ? 0 : 1;
}

/**
 *
 * 删除信号量
 *
 *
 */ 
static int del_semaphore(int sem_id)
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
static int p_semaphore(int sem_id)
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
static int v_semaphore(int sem_id)
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
