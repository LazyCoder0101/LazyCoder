#include "digital.h"

//判断整数的位数
//摘自 https://www.cnblogs.com/yejiaxing-01/p/12623831.html
int GetIntLength(int i)
{
   int len=1;
   if(i<0) i = -i;
   for(;i/10>0; i /= 10)
      ++len;
   return len;
}

//判断整数的位数
int getLenOfIntegerPart(double number)
{
	int t = (int)number;
	int temp=getLenOfIntegerPart(t);
	return temp;
}

//判断小数点后面的位数
int getLenBehindPoint(double number){
	int plen = 0,t,getlen=0;
    char * ptr;
    char strFloat[20];
	int flag = 0;
	sprintf(strFloat, "%f", number);
    ptr = strstr(strFloat, ".");
//	printf("得到的是%s\n",ptr);
    if(ptr!=NULL){
		plen=strlen(ptr)-1;
		for(t=plen;t>0;t--){
			if(ptr[t]!='0'){
				flag = 1;				
				getlen++;
			}else{
				if(flag == 1){
					getlen++;
				}
//				printf("第%d个是%c，是0\n",t,ptr[t]);	
			}
		}
		
//        printf("%d\n",getlen);
	}
	return getlen;
}

