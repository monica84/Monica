# BufferedMessageQueue
> Receives unknown number of sequenced marked messages out of order, buffers them and when a batch is ready in correct order, sends forward to the Consumer

## Table of Contents
* [General Info](#general-information)
* [Usage](#usage)
* [Configuration for testing](#configuration-for-testing)
* [Room for Improvement](#room-for-improvement)

## General Information
This project creates sequenced messages out of order, buffers them sends them out in ordered batches. This is achieved by using the following main objects:
- Producer Object that creates messages of type Message with unique Sequence number and sends these messages to the BufferedQueue
- BufferedQueue Object that takes in messages from Producer, reorders them and sends them in batches to all registered listeners through BufferedQueueListener
- Consumer Object that implements BufferedQueueListener and receives the ordered messages in batches from BufferedQueue. It then displays the messages

## Usage
- IntelliJ IDEA:
  - This project was written in IntelliJ
  - Import the project and run the main method in class Main
- Command Line
    - Compile the project and then run:
    - java com.sailpoint.assignment.Main

## Configuration for testing
The Main class has 2 parameters to configure the testing
 - maxMsgs - Total number of messages to be send out by the Producer to the BufferedQueue 
 - batchSize - the number of messages in a batch that are send together to the Consumer 
 - current values: 
   - int maxMsgs = 20; 
   - int batchSize = 2;
  
## Room for Improvement
- One:
  - The Producer and Consumer are very basic objects since the focus of the assignment was the BufferedQueue implementation
  - The sequencing of messages and batch numbers is done with incrementing integer values. The program will stop functioning when maximum value of integer is reached. This can be further improved by:
    - adding a limit to number of messages that can be stored in the BufferedQueue
    - adding constraints on what happens when the queue is full
