#include <unistd.h>
#include <stdio.h>

int main(void)
{
	printf("进程号为:%d\n",getpid());
	sleep(60);
}
