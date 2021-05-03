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
	msg.my_msg_type=3;
    key_t key = ftok("/temp",1);
	msgid=msgget(key,0660|IPC_CREAT);
    //循环获取消息队列中信息
	while(1)
	{
		msgrcv(msgid,&msg,BUFSIZ,msg.my_msg_type,0);
		printf("Receive:%s",msg.some_text);
		if(strncmp(msg.some_text,"end",3)==0)
			break;
	}
	msgctl(msgid,IPC_RMID,0);
	exit(0);
}