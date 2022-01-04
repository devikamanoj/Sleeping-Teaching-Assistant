#include <stdio.h>  
#include <unistd.h>  
#include <stdlib.h>  
#include <pthread.h>  
#include <semaphore.h> // The maximum number of student threads.  
#define MAX_STUDENTS 25 

void randwait(int seconds);  
void *teachingAssistant(void *);  
void *student(void *studentId);  

sem_t waitingRoom;  
sem_t teachingAssistantRoom;  
sem_t teachingAssistantPillow;  
sem_t studentBelt; 

// Flag to stop the teaching assistant thread when all students have been serviced. 
int allDone = 0;  

int main(int argc, char *argv[])  
{ 
    pthread_t TAid;  
    pthread_t tid[MAX_STUDENTS];  
    int i, count, numStudents, numChairs; int Number[MAX_STUDENTS];  
    printf("Maximum number of students can only be 25. Enter number of students and chairs \n"); 
    scanf("%d",&count); 
    numStudents = count;  
    scanf("%d",&count); 
    numChairs = count; 
    if (numStudents > MAX_STUDENTS) {  
       printf("The maximum number of Students is %d \n", MAX_STUDENTS);

       return 0; 
    } 

    for (i = 0; i < MAX_STUDENTS; i++) {  
        Number[i] = i;  
    }  

    // Initialize the semaphores with initial values...  
    sem_init(&waitingRoom, 0, numChairs);  
    sem_init(&teachingAssistantRoom, 0, 1); 
    sem_init(&teachingAssistantPillow, 0, 0);  
    sem_init(&studentBelt, 0, 0);  
     
    // Create the teachingAssistant.  
    pthread_create(&TAid, NULL, teachingAssistant, NULL);  
     
    // Create the students.  
    for (i = 0; i < numStudents; i++) { 
        pthread_create(&tid[i], NULL,student, (void *)&Number[i]);  
    }  

    // Join each of the threads to wait for them to finish.  
    for (i = 0; i < numStudents; i++) {  
        pthread_join(tid[i],NULL);  
    }  

    // When all of the sudents are finished, kill the teaching assistant thread.  
    allDone = 1; 
 
    sem_post(&teachingAssistantPillow);// wake the teaching assistant.
    pthread_join(TAid,NULL);  
    return 0; 
}  

void *teachingAssistant(void *userInput)  
{  
// While there are still students to be serviced... Our teaching assistant is busy and can tell if there are students still to serve.
    
  while (!allDone) { // Sleep until someone arrives and wakes you..  
    printf("The teaching assistant is sleeping \n");  
    sem_wait(&teachingAssistantPillow);
    if (!allDone)  
    { 
     printf("The teaching Assistant is teaching to his student. \n");  
     randwait(2); 
     printf("The teaching Assistant finished teaching his student. \n");
     sem_post(&studentBelt);  
    }  
    else {  
         printf("The Teaching Assistant is Leaving. \n");  
    }  
   } 
} 

void *student(void *studentId) {  

 int num = *(int *)studentId;

     printf("Student %d going to teaching assistant room \n", num);  
     randwait(5);  
     printf("Student %d arrived at teaching assistant waiting room \n", num); // Wait for space to open up in the waiting room...  
     sem_wait(&waitingRoom);  
     printf("Student %d entering teaching assistant waiting room \n", num); // Wait for the teaching assistant room student chair to become free.  
     sem_wait(&teachingAssistantRoom); // The chair is free so give up your spot in the  waiting room.  
     sem_post(&waitingRoom);
     printf("Student %d waking the  teachingAssistant \n", num);  
     sem_post(&teachingAssistantPillow);
     sem_wait(&studentBelt); // Give up the chair.  
     sem_post(&teachingAssistantRoom);  
     printf("Student %d leaving from teaching assistant room \n", num);  
}  


     
void randwait(int seconds) {  
     int len = 1;
     sleep(len);  
} 