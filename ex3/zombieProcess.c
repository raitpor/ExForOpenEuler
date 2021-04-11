#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>

int main()
{
        pid_t pc, pr;
        pc = fork();
        int *p; 
        if (pc < 0)
                printf("error ocurred!\n");
        else if (pc == 0)
                printf("this is child process with pid of %d\n", getpid());
        else
        {
                sleep(20);
                printf("this is parent process with pid of %d\n", getpid());
                *p=5; 
        }
}