# Sleeping Teaching Assistant


## PROBLEM STATEMENT
The teaching assistant has to provide assistance to the undergraduate students during office hours with their programming difficulties. The teaching assistant office is just a single room with a table and chair. When the teaching assistant is busy in assisting one student the other students have to wait in the waiting area which is outside the office. If the chairs available in the waiting area are empty the the student can occupy it and wait for their chance. Also, if all the chairs are occupied then the student decides to come later.

After assisting a student, teaching assistant checks if there is any student present in the waiting area. If the students are present, then the teaching assistant assists the next student. If no one is present, the teaching assistant takes a nap. If any student arrives when the teaching assistant is taking the nap then, student have to wake up the teaching assistant for assistance.

From a technical point of view, this problem can be viewed as a critical section problem where each student is a process and the assistant's room is the critical section.

## IMPLEMENTATION

We implemented the solution to the Sleeping Teaching Assistant problem in Java using semaphores and threads. Two semaphores are used to capture two events. One counting semaphore to keep track of the chairs and let the students in and another binary semaphore that says if the assistant is available.  While the teaching assistant assists a student, other students are blocked and the teaching assistant give preference to the student who is waiting for maximum time amongst all to assist next. If the chairs in the waiting area are occupied, the remaining students will be asked to start programming on their own till the teaching assistant becomes free. When there are no student to assist, the teaching assistant takes nap. If the assistant is sleeping, the first student to reach next will wake him/her up.

[PPT](https://docs.google.com/presentation/d/1W5T7woGTm90FPc6d1SHCizTfVI8l8shfM40FS6lrK8U/edit?usp=sharing)


[Report](Sleeping_Teaching_Assistant.pdf)
## OUTPUT
![](images/op.png)

## GROUP MEMBERS


| NAME  | ROLL NUMBER |
| ------------- | ------------- |
| GAYATHRI P  | AM.EN.U4AIE20126  |
| LAKSHMI WARRIER  | AM.EN.U4AIE20143   |
| M DEVIKA  | AM.EN.U4AIE20144  |
| NIVEDITA RAJESH  | AM.EN.U4AIE20153 |
