package com.sailpoint.assignment;

/**
 * This class contains the main method that is invoked to create:
 *  - Producer Object that creates messages of type Message with unique
 *          Sequence number and sends these messages to the BufferedQueue
 *  - BufferedQueue Object that takes in messages from Producer, reorders
 *       them and sends them in batches to all registered listeners
 *       through BufferedQueueListener
 *  - Consumer Object that implements BufferedQueueListener and receives
 *       the ordered messages in batches from BufferedQueue. It then
 *       displays the messages
 */
public class Main {

    /* Use these configurable parameters to change the testing parameters
     * maxMsgs = Total number of messages send out by the Producer
     * batchSize = the number of messages in a batch that are send to Consumer together
     */
    public static void main(String[] args) {
        int maxMsgs = 20;
        int batchSize = 2;
        int maxBufferSize = Integer.MAX_VALUE;
        BufferedQueue bufferedQueue = new BufferedQueue(batchSize, maxBufferSize);
        Producer producer = new Producer(bufferedQueue);
        Consumer consumer = new Consumer();
        bufferedQueue.registerListener(consumer);

        // This is a basic implementation of am environment where
        // multiple Producer threads will be writing to the BufferedQueue
        // here each Producer thread will put out 2 msgs
        for (int i=0; i<maxMsgs/2 && i<maxBufferSize/2; i++) {
            Thread t = new Thread(producer);
            t.start();
        }
    }
}
