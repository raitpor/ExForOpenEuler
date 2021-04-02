#include <stdio.h>
struct p{
	int time;
	char name;
};

void fifo(struct p ps[]){
	int now = 0;
	printf("here");
	for(int i = 0;i<sizeof(*ps);i++){
		printf("time spent:%d",now);
		printf("acting process:%s\n",ps[i].name);
	}
};

int main(void){
	struct p ps[5]={
		{5,'a'},{19,'b'},{3,'c'},{9,'d'},{2,'e'}
	};
	fifo(ps);
	return 0;
}
