package com.sailpoint.assignment;

/** This class receives messages send from the BufferedQueue
 * All messages come in batches :
 *    - messages in a given batch are in order, with no gaps
 *    - Batches are in the proper order, with no gaps
 * The Consumer class consumes the messages by displaying
 * the sequence number of the message
 *
 * It implements the BufferedQueueListener interface.
 * This decouples the Consumer implementation from the BufferedQueue
 */

public class Consumer implements BufferedQueueListener {
    @Override
    public void process(Message[] batch) {
        synchronized (System.out) {
            System.out.print("**** Consumer Received Msgs with Seq Num **** |");
            for (int i=0; i<batch.length ; i++) {
                System.out.print(batch[i].getSeq() + " | ");
            }
            System.out.println(" ");
        }
    }
}