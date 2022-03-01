package com.sailpoint.assignment;

/* This class produces messages that are numbered from 0..N, one at a time.
 * To simulate multiple Producers adding messages to the BufferedQueue,
 * many threads of Producer object are created and each run() of the thread
 * adds 2 messages at random intervals to the BufferedQueue
 *
 * The unique message number is maintained by a static variable in the class
 */
public class Producer implements Runnable {
    private static int msgNumber = 0;
    private BufferedQueue bufferedQueue;

    // Constructor
    // store the BufferedQueue on which the messages should
    // be send out
    public Producer(BufferedQueue bufferedQueue) {
        this.bufferedQueue = bufferedQueue ;
    }

    // synchronized to make it thread safe
    public synchronized int getNextMsgNumber() {
        return msgNumber++;
    }

    public void produceMessages() {
        for( int i=0; i<2 ; i++) {
            try {
                // added a random sleep for the thread so that the messages added
                // to the BufferedQueue are out of order
                Thread.sleep((long) (Math.random() * 10));
                Message msg = new Message("TEXT", getNextMsgNumber());
                System.out.println("Producer created msg -- " + msg);
                bufferedQueue.addMessage(msg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
   }

    @Override
    public void run() {
        produceMessages();
    }
}
