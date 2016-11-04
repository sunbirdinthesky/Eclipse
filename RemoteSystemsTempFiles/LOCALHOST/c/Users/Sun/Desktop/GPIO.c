/*	Includes	*/    //8.21 from WCQ  changed at 8.24 night by HUBIN

#include"wiringPi.h"
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"wiringSerial.h"
#include"gpio_GPIOinterface.h"


/*	Definitions	*/
#define STEP_NUM1 53000	//Push The First low
#define STEP_NUM2 38000	//Push The Second low
#define STEP_NUM3 40000	//Push The Third low

#define DETEC_TIME_OUT 100 //10s for detection   

#define BUZZ_OFF 0	//CONTROL THM3060 BUZZER AND LED
#define BUZZ_ON 1

#define PIN_PWM 8//2		//Step motor 
#define PIN_DIR 9//3
#define PIN_SLEEP 7//4

#define PIN_LED1 21//5	//LED 
#define PIN_LED2 22//6
#define PIN_LED3 11//7
#define PIN_LED4 10//8

#define PIN_LIGHT 13//9	//black light and beep 
#define PIN_BEEP  12//10
#define PIN_TOUCH 14//11	//input
#define PIN_DSW1 26//12
#define PIN_DSW2 23//13
#define PIN_DSW3 27//16
#define PIN_DSW4 0//17
#define PIN_DSW5 1//18

#define PIN_INF_L 28
#define PIN_INF_R 24

/*	Variables	*/
int fd;
signed char User_flag = 0;
char ID_Card[25];
int sign_num=0;
int Card_return=0;
int Step_Push_num=0;
char delay_flag=0;
int delay_count=0;
char detec_flag = 0;
int detec_count = 0;


/*	Prototypes	 */
void Step_Control(unsigned int Step_num, char dir);
void GPIO_Init(void);
int RFID_Init(char RFID_MODE);
int GetCardID(char * ID_Card_Temp);

void Step_Pulse(void);

void Step_Motor_Reset_Left(void);
void Step_Motor_Reset_Right(void);

int main () {
	return 0;
}

/*	Code  */
int gpio()
{
	GPIO_Init();	//GPIO Init
	RFID_Init(BUZZ_ON);	//THM3060 And UART Init  BD115200
	Step_Motor_Reset_Left();
	User_flag = 0;
	for (;;)
	{

		Card_return = GetCardID(ID_Card);
		if (Card_return != 0)		//Get The Card ID
		{
			if(Card_return == 1) 
				printf("Card_Type: A \n");
			else if(Card_return == 2) 
				printf("Card_Type: B \n");
			printf("Card_ID=%s\n",ID_Card);
			if (User_flag >= 0)  //add by sunbird, keep error message
				User_flag = 1;
				printf("find an ID_card");   //-----------------------------------------
		}

		if(User_flag >= 0 && digitalRead(PIN_TOUCH) == 0)	//Touch The Swtich
		{	 //user_flag >= 0 is add by sunbird, keep error message
			delay(15);						//Elimination Buffeting Of Keystroke
			if(digitalRead(PIN_TOUCH) == 0)	
			{
				printf("had been touched\n"); //----------------------------------------
				User_flag=2;
			}	
		}	
		
		if(digitalRead(PIN_DSW1)==1||digitalRead(PIN_DSW2)==1||digitalRead(PIN_DSW3)==1)
		{
			printf("----Out Of Paper----\n");
			printf("----Out Of Service----\n");
			digitalWrite(PIN_LED2,HIGH);
			User_flag = -1; //add by sunbird at 2016:8:11, for send signal to java
		}
		
		if(delay_flag==1) 
		{
			delay_count++;
			if(delay_count>=50)
			{
				delay_count=0;
				digitalWrite(PIN_LIGHT,LOW);
				delay_flag = 0;
			}
		}

	/*	if (detec_flag == 1)
		{
			detec_count++;
			if (digitalRead(PIN_INF_L) == 0 || digitalRead(PIN_INF_R) == 0)
			{
				detec_count = 0;
				detec_flag = 0;
			}
			if (detec_count >= DETEC_TIME_OUT)
			{
				detec_count = 0;
				digitalWrite(PIN_LED1, HIGH);
				detec_flag = 0;
				User_flag = -2; //changed by sunbird, from -1 to -2, marks error
			}
		}
	*/
	
		delay(100);
	}
return 0;
}

/* function:
* void Step_Motor_Reset(void)
*/
void Step_Motor_Reset_Left(void)	
{
		digitalWrite(PIN_DIR,1);
		digitalWrite(PIN_SLEEP,1);
		
		while(digitalRead(PIN_DSW5)==1)
		{
			Step_Pulse();
			
		}
		digitalWrite(PIN_SLEEP,0);
}

void Step_Motor_Reset_Right(void)
{
	digitalWrite(PIN_DIR,0);
		digitalWrite(PIN_SLEEP,1);
		
		while(digitalRead(PIN_DSW4)==1)
		{
			Step_Pulse();
			
		}
		digitalWrite(PIN_SLEEP,0);
}

void Step_Pulse(void)
{
		digitalWrite(PIN_PWM, HIGH); delayMicroseconds(50);
		digitalWrite(PIN_PWM, LOW); delayMicroseconds(50);
}

int stepCtrl() {

	detec_flag = 1;

	if(Step_Push_num==0)
	Step_Control(STEP_NUM1, 0);
	else if(Step_Push_num==1)
	Step_Control(STEP_NUM2, 0);
	else if(Step_Push_num==2)
	Step_Control(STEP_NUM3, 0);
	else if(Step_Push_num==3)
	Step_Control(STEP_NUM1, 1);
	else if(Step_Push_num==4)
	Step_Control(STEP_NUM2, 1);
	else if(Step_Push_num==5)
	Step_Control(STEP_NUM3, 1);
	
	Step_Push_num++;
	if(Step_Push_num>=6) Step_Push_num=0;
	

	digitalWrite(PIN_LIGHT,HIGH);
	delay_flag=1;

	User_flag = 0;//changed by sunbird at 2016:8:11 ,from 1 to 0; 
	//reason:in java, 0 means not function,1 and 2 means button pressed and card read
	return 0;
}



/* function:
* void Step_Control(unsigned int Step_num,char dir)
* dir -> direction : 0 or 1
*
*/
void Step_Control(unsigned int Step_num, char dir)
{
	unsigned int i = 0;
	digitalWrite(PIN_DIR, dir);
	digitalWrite(PIN_SLEEP, 1);
	for (i = 0; i<Step_num; i++)
	{
		if((dir==0&&digitalRead(PIN_DSW4)==0)||(dir==1&&digitalRead(PIN_DSW5)==0));
		else 
		{
			digitalWrite(PIN_PWM, HIGH); delayMicroseconds(50);
			digitalWrite(PIN_PWM, LOW); delayMicroseconds(50);
		}
	}
	digitalWrite(PIN_SLEEP, 0);
	//User_flag = 0;                
}


/* function:
* void GPIO_Init(void)
*/
void GPIO_Init(void)
{
	wiringPiSetup();
	pinMode(PIN_PWM, OUTPUT);
	pinMode(PIN_DIR, OUTPUT);
	pinMode(PIN_SLEEP, OUTPUT);
	pinMode(PIN_LED1, OUTPUT);
	pinMode(PIN_LED2, OUTPUT);
	pinMode(PIN_LED3, OUTPUT);
	pinMode(PIN_LED4, OUTPUT);
	digitalWrite(PIN_LED4,HIGH);

	pinMode(PIN_LIGHT, OUTPUT);
	pinMode(PIN_BEEP, OUTPUT);

	pinMode(PIN_TOUCH,INPUT);
	pullUpDnControl(PIN_TOUCH,PUD_UP);

	pinMode(PIN_DSW1, INPUT);
	pinMode(PIN_DSW2, INPUT);
	pinMode(PIN_DSW3, INPUT);
	pinMode(PIN_DSW4, INPUT);
	pinMode(PIN_DSW5, INPUT);
	pullUpDnControl(PIN_DSW1,PUD_UP);
	pullUpDnControl(PIN_DSW2,PUD_UP);
	pullUpDnControl(PIN_DSW3,PUD_UP);
	pullUpDnControl(PIN_DSW4,PUD_UP);
	pullUpDnControl(PIN_DSW5,PUD_UP);
	
}


/* function:
* int RFID_Init(char RFID_MODE)
* 	return:
* 	-1 :Init Err
* 	 1 :Init Success
*
* RFID_MODE:
* BUZZ_ON of BUZZ_OFF
*/
int RFID_Init(char RFID_MODE)
{
	if ((fd = serialOpen("/dev/ttyAMA0", 115200))<0)
	{
		printf("Serial err\n");
		return -1;
	}
	else
		printf("Serial Init Success!# fd=%d\n", fd);
	delay(100);
	if (RFID_MODE == BUZZ_ON)
	{
		serialPutchar(fd, 'B');	//BUZZER ABD LED ON
		serialPutchar(fd, '\r');
		delay(1000);
	}
	
	serialPutchar(fd, 'B');	
	serialPutchar(fd, '\r');
	delay(500);
	
	serialPutchar(fd, 'B');	
	serialPutchar(fd, '\r');
	delay(500);
	
	serialPutchar(fd, '\r');
	delay(1000);
	serialPutchar(fd, 'B');	
	serialPutchar(fd, '\r');
	delay(1000);
	
	serialPutchar(fd, 'A');	//THM3060 Working At Mode 1.
	serialPutchar(fd, '\r');

	return 1;
}


/* function:
* 	int GetCardID(char * ID_Card_Temp)
*  return:
* 	 0 : no card
* 	 1 : TYPE A CARD
* 	 2 : TYPE B CARD
*/
int GetCardID(char * ID_Card_Temp)
{
	int i = 0, j = 0;
	int receive_num = 0;
	char UartBuff[100]={0};
	char receive_flag=0;
	
	//printf("\n");
	sign_num++;
	if(sign_num>=100) 
	{
		sign_num=0;
		printf("Lost THM3060!\n");
		printf("Try to reset THM3060!\n");
		
		serialPutchar(fd,'B');	//BUZZER ABD LED ON
		serialPutchar(fd,'\r');
		serialPutchar(fd,'A');	//THM3060 Working At Mode 1.
		serialPutchar(fd,'\r');
			
		return 0;
	}
	
	while (serialDataAvail(fd) > 0)
	{
		UartBuff[receive_num] = serialGetchar(fd);
		
		receive_num++;
		receive_flag=1;
		if(receive_num>=90)
		{
			receive_num=0;
			receive_flag=0;	
			serialFlush(fd);		
			break;
			//return 0;
		}
		
	}
	//if(UartBuff[0]!=0)printf("%s",UartBuff);
	//else return 0;//printf("-2-");
	//serialFlush(fd);
	if(receive_flag==1)
	{
		for(i=0;i<receive_num-8;i++)
		{
			if(UartBuff[i] == 'D')
			if(UartBuff[i+1] == 'R')
			if(UartBuff[i+2] == ' ')
			if(UartBuff[i+3] == 'V')
			if(UartBuff[i+4] == '1')
			{
				sign_num=0;
				break;
			}
		}

		
	for (i = 0; i<receive_num - 8; i++)
	{
		if (UartBuff[i] == 'I')
		{
			if (UartBuff[i + 1] == 'D')
				if (UartBuff[i + 2] == ':')
				{
					//printf("OK_1: ");
					for (j = 0; j<receive_num - 8; j++)
					{
						ID_Card_Temp[j] = UartBuff[i + j + 5];
						//printf("%c",ID_Card_Temp[j]);
						if (UartBuff[i + j + 6] == '\n')
						{
							ID_Card_Temp[j+1] = '\0';
							//printf(" END\n");
							//printf("j=%d\n",j);
							i = receive_num;
							receive_flag=2;
							break;
						}
					}
				}
		}
		
	}
	if(receive_flag==1)receive_flag=0;
	}
	
	if(receive_flag==2)
	{
		receive_flag=0;
		if (j == 9) return 1;
		else if (j == 19) return 2;
	}
	 return 0;
}



/*#########################################################
########DO NOT EDIT, unless you know what you are doing####
########Created by machine#################################
########Used by jni lib ###################################
###########################################################*/

jstring stoJstring(JNIEnv* env, const char* pat)
{
	jclass strClass = env->FindClass("Ljava/lang/String;");
	jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
	jbyteArray bytes = env->NewByteArray(strlen(pat));
	env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
	jstring encoding = env->NewStringUTF("utf-8");
	return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
}

/*
 * Class:     gpio_GPIOinterface
 * Method:    getUserFlag
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_getUserFlag
(JNIEnv *env, jclass c) {
	return User_flag;
} 

/*
 * Class:     gpio_GPIOinterface
 * Method:    gpio
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_gpio
(JNIEnv *env, jclass c){
	return gpio();
}

/*
 * Class:     gpio_GPIOinterface
 * Method:    stepCtrl
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_stepCtrl
(JNIEnv *env, jclass c) {
	return stepCtrl();

}

/*
 * Class:     gpio_GPIOinterface
 * Method:    resetFlag
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_resetFlag
(JNIEnv *env, jclass c) {
	User_flag = 0;
	return 0;
}

/*
 * Class:     gpio_GPIOinterface
 * Method:    getCardNumber
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_gpio_GPIOinterface_getCardNumber
(JNIEnv *env, jclass c) {
	jstring cardNumber = stoJstring(env, ID_Card);
	int i = 0;
	int len = strlen(ID_Card);
	for (;i < len; i++) {
		ID_Card[i] = 0;
	}
	return cardNumber;
}


/*
 * Class:     gpio_GPIOinterface
 * Method:    resetOutOfPaper
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_resetOutOfPaper
  (JNIEnv *env, jclass c) {
	if (User_flag < 0) {
		User_flag = 0;
		Step_Motor_Reset_Left();
		return 0;
	}
	return 1;
}



//########end warning#######################

