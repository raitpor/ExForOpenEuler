#include<stdio.h>
#include<stdlib.h>
typedef struct PCB {
	int id;//任务序号
	int arrivetime;//任务到达时间
	int runtime;//任务需要执行的时间
	struct PCB* next;
}*task, pcb;
pcb* creattask(int x, int y, int z) {
	task newtask = (task)malloc(sizeof(pcb));
	newtask->id = x;
	newtask->arrivetime = y;
	newtask->runtime = z;
	newtask->next = NULL;
	return newtask;
}
void deltask(pcb* n, int x) {
	task d;
	if (n != NULL) {
		while (n->next->id != x) {
			n = n->next;
		}
		d = n->next;
		n->next = d->next;
		free(d);
	}
}
void count(pcb* n, int t) {  //q用于记录最先到达的任务位置，p用于遍历链表
	pcb* q, * p;
	int temp = t; float time = 0; //time记录当前时间，temp记录任务个数,便于后续操作
	float zt = 0, dt = 0;  //zt用于记录总周转时间,dt记录总带权周转时间
	while (t != 0) {
		p = n->next;
		q = p;
		while (p != NULL) { //找到最先到达的任务
			if (p->arrivetime < q->arrivetime) {
				q = p;
			}
			p = p->next;
		}
		p = n->next;
		while (p != NULL) { //找到处于就绪的多个任务里面任务最短的任务
			if ((p->arrivetime <= time || p->arrivetime == q->arrivetime) && p->runtime < q->runtime)
				q = p;
			p = p->next;
		}
		printf("当前执行的任务序号为 %d \n", q->id);
		if (time <= q->arrivetime)
			time = q->runtime + q->arrivetime;
		else
			time = time + q->runtime;
		printf("该任务周转时间为 %.0f \n", time - q->arrivetime);
		zt = zt + time - q->arrivetime;
		printf("该任务带权周转时间为 %.2f \n\n", (time - q->arrivetime) / q->runtime);
		dt = dt + (time - q->arrivetime) / q->runtime;
		deltask(n, q->id);
		--t;
	}
	printf("\n");
	printf("平均周转时间为 %.2f \n", zt / temp);
	printf("平均带权周转时间为 %.2f \n", dt / temp);
}
int main() {
	int n, i, y, z;
	task tail = NULL;
	task head = NULL;
	printf("请输入任务数量:");
	scanf("%d",&n);
	tail = (task)malloc(sizeof(pcb));
	head = tail;
	for (i = 1; i <= n; i++) {
		printf("请输入%d号任务的到达时间、运行时间:", i);
		scanf("%d%d",&y,&z);
		tail->next = creattask(i, y, z);
		tail = tail->next;
	}
	count(head, n);
}