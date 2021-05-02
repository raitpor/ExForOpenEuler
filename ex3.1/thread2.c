//gcc threadid.c -o a.out -pthread
//pthread是linux下的线程库，用了多线程就要链接这个库，这时候要在编译选项上增加-pthread
#include "apue.h"
#include "apueerror.h"
#include <pthread.h>
#include <unistd.h>

pthread_t ntid;

void *
thr_fn(void *arg)
{
	//调用上面的打印id函数
	printf("new thread: %lu\n",ntid);
	return((void *)0);
}

int main(void)
{
	int	err;
    pthread_t mainthread;
    mainthread  = pthread_self();
	//创建线程，主线程把新线程ID存放在ntid中，新线程去执行thr_fn函数
	err = pthread_create(&ntid, NULL, thr_fn, NULL);
	if (err != 0)
		err_exit(err, "can't create thread");
    printf("main thread:%lu Created thread:%lu\n",pthread_self,ntid);
	sleep(60);
	exit(0);
}
