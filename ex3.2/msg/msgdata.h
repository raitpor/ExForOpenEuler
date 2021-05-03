#ifndef _MSG_DATA
#define _MSG_DATA

#define MAXMSG 512
struct my_msg
{
	long int my_msg_type;
	char some_text[MAXMSG];
};

#endif