//gcc threadid.c -o a.out -pthread
//pthread是linux下的线程库，用了多线程就要链接这个库，这时候要在编译选项上增加-pthread
// #include "apue.h"
// #include "apueerror.h"
#include <pthread.h>
#include <unistd.h>

pthread_t ntid;

void
printids(const char *s)
{
	//声明进程id
	pid_t		pid;
	//声明线程id
	pthread_t	tid;
	//获取进程id
	pid = getpid();
	//用pthread_self()获取自己线程id
	tid = pthread_self();
	printf("%s pid %lu tid %lu (0x%lx)\n", s, (unsigned long)pid,
	  (unsigned long)tid, (unsigned long)tid);
}

void *
thr_fn(void *arg)
{
	//调用上面的打印id函数
	printids("new thread: ");
	return((void *)0);
}

int main(void)
{
	int	err;
	//创建线程，主线程把新线程ID存放在ntid中，新线程去执行thr_fn函数
	err = pthread_create(&ntid, NULL, thr_fn, NULL);
	if (err != 0)
		err_exit(err, "can't create thread");
	printids("main thread:");
	sleep(60);
	exit(0);
}
