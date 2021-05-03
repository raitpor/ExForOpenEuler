#include<stdlib.h>
#include<unistd.h>
#include<string.h>
#include<sys/types.h>
#include<linux/msg.h>
#include<stdio.h>
#include"msgdata.h"

main()
{
    struct my_msg msg;
	int msgid;
    //生成key
    key_t key = ftok("/temp",1);
    //创建消息队列,0660为权限控制
	msgid=msgget(key,0660|IPC_CREAT);  
    //通过循环获取用户输入
	while(1){
		puts("Enter some text:");
		fgets(msg.some_text,BUFSIZ,stdin);
		msg.my_msg_type=3;
        //发送数据到缓冲区
		msgsnd(msgid,&msg,MAXMSG,0);   
		if(strncmp(msg.some_text,"end",3)==0){   //比较输入，若为end则跳出循环
		    break;
        }
    }
	exit(0);
}