package com.sailpoint.assignment;

/** A class to represent the single unit of data that is passed
 * from Producer to BufferedQueue to Consumer.
 * Along with the actual data, this class stores a unique sequence number
 * which is used to order the Messages
 * This sequence number is passed in by the creator of the Message object
 *
 * Note that this is a very basic implementation with data stored as String
 * since this was not the focus of this assignment.
 */
public class Message {

    // both txt and seq are declared final as once the message is
    // created, it should never be changed.
    private final String txt;
    private final int seq;

    public Message(String txt, int seq) {
        this.txt = txt;
        this.seq = seq;
    }

    public String getTxt() {
        return txt;
    }

    public int getSeq() {
        return seq;
    }

    @Override
    public String toString() {
        return "Message{" +
                "txt='" + txt + '\'' +
                ", seq=" + seq +
                '}';
    }
}
