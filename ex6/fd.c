#include<iostream>
#include<ctime>
#include<cstdlib>
#include<vector>
#include<string>
using namespace std;
#define LEN 500
 
/**************************************************
	基于长度可变区域的索引分配 + 位表
**************************************************/
 
 
/*
	定义文件结构
*/
typedef struct File
{
	string name;//文件名
	float size;//文件大小
}File;
 
/*
	定义文件分配表结构
*/
typedef struct Fat //file allocation table文件分配表
{
	string fileName; //文件名
	int indexBlock; //索引块号
}Fat;
 
 
/*
	随机产生文件大小
*/
float randFileSize()
{
	//srand(time(seed));
	float fileSize = (rand() % 81 + 20)/10.0f;//随机产生大小为2k—10k的文件
	return fileSize;
}
 
/*
	存储文件
*/
bool storageFile(int bitTable[],vector<Fat> &FAT, File file, int disk[][LEN])
{
	int n = ceil(file.size/2);//向上取整，获得文件存储需要的块数（不包括索引块）
	
	//检查是否有足够的空块来存放该文件
	int i = 0;
	int k = 0;
	int index = -1;//索引块
	for (; i < LEN; i++) {
		if (bitTable[i] == 0) {
			k++;
			if (k == 1) {
				index = i;//记下索引块
			}
			if (k == n + 1) {
				break;
			}
		}
	}
	//没有足够空间来存放该文件
	if (k <= n) {
		return false;
	}
	//cout << "索引块：" << index <<endl;
	///
	//  
	//	若有足够的空白块来存放该文件，则将索引块存放在第一个空白块上
	//
	///
 
	//更新文件分配表
	Fat fat;
	fat.fileName = file.name;
	fat.indexBlock = index;
	FAT.push_back(fat);
	
	//更新位表
	bitTable[index] = 1;
	i = index + 1;//从索引块的下一个位置继续寻找空白块
	k = 0;//记录每段分区的长度
	int t = 0;
	//根据位表找到存放的位置，更新索引块，更新位表
	for (int j = 0; j < n; j++) {
		
		if (bitTable[i] == 0) {
			if (k == 0) {//这是一个新的分区，记录下起始块
				disk[index][t++] = i;
			}
			k++;
			bitTable[i] = 1;//更新位表
		}
		else{
			if (k != 0) {//如果 k!=0 说明，该分区结束，记录下长度
				disk[index][t++] = k;
				//cout << "\t起始块：" << disk[index][t - 2] << "\t长度: " << disk[index][t - 1] << endl;
				disk[index][t] = -1;//设置一个标志
				k = 0;
			}
			j--;
		}
		i++;
	}
	disk[index][t++] = k;
	disk[index][t] = -1;
	//cout << "\t起始块：" << disk[index][t - 2] << "\t长度: " << disk[index][t - 1] << endl << endl;
	return true;
}
 
int indexBlockOfFile(vector<Fat> FAT, string fileName)
{
	for (int i = 0; i < FAT.size(); i++) {
		if (FAT[i].fileName == fileName) {
			return FAT[i].indexBlock;
		}
	}
	return -1;//返回 -1 说明没有找到
}
 
/*
	删除文件更新位表
*/
bool deleteFile(int bitTable[], vector<Fat> &FAT, string fileName, int disk[][LEN])
{
	//从文件分配表中找到索引块
	for (int i = 0; i < FAT.size(); i++) {
		if (FAT[i].fileName == fileName) {
			int index = FAT[i].indexBlock;
			int t = 0;
			while (disk[index][t] != -1) {
				int begin = disk[index][t];
				int len = disk[index][t + 1];
				for (int j = 0; j < len; j++) {
					bitTable[begin + j] = 0;
				}
				t += 2;
			}
			bitTable[index] = 0;
			vector<Fat>::iterator itr = FAT.begin()+i;
			FAT.erase(itr);
			return true;
		}
	}
	return false;
}
 
/*
	输出索引块和存储的位置
*/
void outputIndexBlock(int disk[][LEN], vector<Fat> FAT, File file)
{
	string fileName = file.name;
	int size = file.size;
	cout << fileName << "\t大小：" << size << "k" << endl;
	for (int i = 0; i < FAT.size(); i++) {
		if (FAT[i].fileName == fileName) {
			int index = FAT[i].indexBlock;
			cout << "索引块：" << index << endl;
			int t = 0;
			while (disk[index][t] != -1) {
				int begin = disk[index][t];
				int len = disk[index][t + 1];
				cout << "\t起始块：" << begin << "\t长度：" << len << endl;
				t += 2;
			}
			break;
		}
	}
}
 
/*
	打印空白块
*/
void outputFreeBlock(int bitTable[])
{
	cout << "空闲块" << endl;
	for (int i = 0; i < LEN; i++) {
		if (bitTable[i] == 0) {
			cout << i << "\t";
		}
	}
	cout << endl;
}
int main()
{
	int bitTable[LEN] = { 0 };//位表
	int disk[LEN][LEN] = { -1 };//磁盘
	vector<Fat> FAT; //文件分配表
 
	//随机生成2k-10k的文件50个,并将其存储到磁盘中
	File file[50];
	string fileName;
	float fileSize;
	for (int i = 0; i < 50; i++) {
		fileName = to_string(i+1) + ".txt"; //文件名
		fileSize = randFileSize(); //随机产生文件大小
		file[i].name = fileName;
		file[i].size = fileSize;
		//cout << fileName << " " << fileSize << "\t";
		//存储该文件	
		if (storageFile(bitTable, FAT, file[i], disk)) {
			//cout << "成功创建文件：" << file[i].name << "   大小：" << file[i].size << "k" << endl;
			outputIndexBlock(disk, FAT, file[i]); cout << endl;
		}
	}
 
	//outputFreeBlock(bitTable);
 
	cout << "\n-------------------" << endl;
	//删除 奇数.txt
	for (int i = 1; i < 50; i += 2) {
		string fileName = to_string(i) + ".txt";
		if(!deleteFile(bitTable, FAT, fileName, disk))break;
		cout << "成功删除文件：" << fileName << endl;
	}
 
	cout << "\n-------------------" << endl;
	//outputFreeBlock(bitTable);
	//新创建 5 个文件
	File fileA, fileB, fileC, fileD, fileE;
	fileA.name = "A.txt";	fileA.size = 7;
	fileB.name = "B.txt";	fileB.size = 5;
	fileC.name = "C.txt"; 	fileC.size = 2;
	fileD.name = "D.txt"; 	fileD.size = 9;
	fileE.name = "E.txt"; 	fileE.size = 3.5;
	/*
	storageFile(bitTable, FAT, fileA, disk);
	storageFile(bitTable, FAT, fileB, disk);
	storageFile(bitTable, FAT, fileC, disk);
	storageFile(bitTable, FAT, fileD, disk);
	storageFile(bitTable, FAT, fileE, disk);
	*/
	if (storageFile(bitTable, FAT, fileA, disk)) {
		cout << "成功创建文件：" << fileA.name << "   大小：" << fileA.size << "k" << endl;
	}
	if (storageFile(bitTable, FAT, fileB, disk)) {
		cout << "成功创建文件：" << fileB.name << "   大小：" << fileB.size << "k" << endl;
	}
	if (storageFile(bitTable, FAT, fileC, disk)) {
		cout << "成功创建文件：" << fileC.name << "   大小：" << fileC.size << "k" << endl;
	}
	if (storageFile(bitTable, FAT, fileD, disk)) {
		cout << "成功创建文件：" << fileD.name << "   大小：" << fileD.size << "k" << endl;
	}
	if (storageFile(bitTable, FAT, fileE, disk)) {
		cout << "成功创建文件：" << fileE.name << "   大小：" << fileE.size << "k" << endl;
	}
	
	cout << "\n-------------------" << endl;
	//给出这 5 个文件的文件分配表
	cout << "文件分配表中:" << endl;
	/*
	cout << fileA.name << " 的索引块为: " << indexBlockOfFile(FAT, fileA.name) << endl;
	cout << fileB.name << " 的索引块为: " << indexBlockOfFile(FAT, fileB.name) << endl;
	cout << fileC.name << " 的索引块为: " << indexBlockOfFile(FAT, fileC.name) << endl;
	cout << fileD.name << " 的索引块为: " << indexBlockOfFile(FAT, fileD.name) << endl;
	cout << fileE.name << " 的索引块为: " << indexBlockOfFile(FAT, fileE.name) << endl;
	*/
	outputIndexBlock(disk, FAT, fileA); cout << endl;
	outputIndexBlock(disk, FAT, fileB); cout << endl;
	outputIndexBlock(disk, FAT, fileC); cout << endl;
	outputIndexBlock(disk, FAT, fileD); cout << endl;
	outputIndexBlock(disk, FAT, fileE);
 
	cout << "\n-------------------" << endl;
	//输出空闲块
	outputFreeBlock(bitTable);
 
	system("pause");
	return 0;
}