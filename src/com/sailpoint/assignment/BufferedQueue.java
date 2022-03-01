package com.sailpoint.assignment;

import java.util.HashMap;

/**
 * BufferedQueue  receives an unknown number of messages that are numbered
 * from 0..N, one at a time. Delivery is reliable in the sense that all
 * messages will eventually be received. However, the order in which they
 * are received can be completely random.
 *
 * This class receives single messages one by one, buffers them in batches
 * of given batchSize, and when a batch is ready, sends it to the
 * registered Listener in the proper order
 *
 * Since
 */
public class BufferedQueue {

    // Currently, BufferedQueueListener is a member variable
    // but if there are multiple Consumers, then it can be a list
    // and the process method can be called on each item in the list
    private BufferedQueueListener bufQueueListener;

    // stores all the batches of messages as they get created
    // In a real world scenario, this HashMap will need to be flushed
    // after a certain amount of time
    // but for this simple case, the producer stops producing msgs
    // when maxBufferSize is reached.
    HashMap<Integer, Batch> batchMap;
    private final int batchSize;
    private int batchNumberToSend;
    private final int maxBufferSize;

    public int getMaxBufferSize() {
        return maxBufferSize;
    }

    public BufferedQueue(int batchSize, int maxBufferSize) {
        this.batchSize = batchSize;
        this.batchMap = new HashMap<>();
        batchNumberToSend = 0;
        this.maxBufferSize = maxBufferSize;
    }

    // msgs are added in random order by Producer
    // create new Batch object when 1st msg arrives
    // in the Batch, finds correct position to insert by Message.seq % batchSize
    // finds the batchNum by Message.seq / batchSize
    // Eg: batchSize = 3
    // Msgs come in the order: 7, 5, 1, 0, 10, 2, (ready to ship 0,1,2) 8, 4, 11, 6
    // (6,7,8 ready but waiting), 3(ship 3,4,5) , (ship 6,7,8), 9 (ship 9,10,11)
    public synchronized void addMessage(Message msg) {
        int msgSeq = msg.getSeq();
        int batchNum = msgSeq / batchSize;
        int msgNum = msgSeq % batchSize;
        Batch batch;
        if( batchMap.containsKey(batchNum)) {
            // the batch was already created
            batch = batchMap.get(batchNum);
        }
        else {
            // create a new Batch with batchNum and add to the HaspMap
            batch = new Batch(batchSize, batchNum);
            batchMap.put(batchNum, batch);
        }
        batch.addMessage(msg, msgNum);

        // check if the batch is ready to be shipped
        // also ensure that it is the correct order for the consumer
        if ( batch.isBatchReady() && batch.getBatchNum()== batchNumberToSend) {
            bufQueueListener.process(batch.getBatch());
            batchNumberToSend++;
            batchMap.remove(batch.getBatchNum());
        }

        // check if any more subsequent batches are ready to be shipped
        // if so, ship them
        boolean keepSending = true;
        while (keepSending && batchMap.containsKey(batchNumberToSend)) {
            batch = batchMap.get(batchNumberToSend);
            if( batch.isBatchReady() && batch.getBatchNum()== batchNumberToSend) {
                bufQueueListener.process(batch.getBatch());
                batchMap.remove(batch.getBatchNum());
                batchNumberToSend++;
            }
            else {
                keepSending = false;
            }
        }
    }

    public void registerListener(BufferedQueueListener lis) {
        this.bufQueueListener = lis;
    }

    /**
     * A private class to create batches of Messages before they are sent out
     * to the registered Listeners
     * The messages are stored in order of their sequence number in an array
     * of given batchSize
     * A msgCounter keeps track of how many messages have been inserted in the
     * Message array. This counter helps us determine when the Batch is ready
     * to be shipped to the Consumer
     */
    private class Batch {
        private Message[] batch;
        private final int batchNum;
        private int msgCounter;

        public Batch(int batchSize, int batchNum) {
            batch = new Message[batchSize];
            this.batchNum = batchNum;
            msgCounter = 0;
        }

        public int getBatchNum() {
            return batchNum;
        }

        public Message[] getBatch() {
            return batch;
        }

        public synchronized void incrementMsgCounter(){
            msgCounter++;
        }

        public boolean isBatchReady() {
            return msgCounter == batch.length;
        }

        public synchronized void addMessage(Message msg, int pos) {
            batch[pos] = msg;
            incrementMsgCounter();
        }
    }
}
