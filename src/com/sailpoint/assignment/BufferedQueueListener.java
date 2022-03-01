package com.sailpoint.assignment;

/**
 * Interface that decouples the Consumers of Messages from the BufferedQueue
 * A class implementing this interface can register itself with
 * the BufferedQueue. Whenever a new batch of messages is ready,
 * the process method with be invoked by the BufferedQueue to pass the
 * batch of messages to the Consumer
 */
public interface BufferedQueueListener {
    public void process(Message[] batch);
}
