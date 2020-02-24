/*
 * This file is part of the base framework given to you for the 6COM1038 
 * Software Engineering Practice coursework assessment -- a text-based command
 * line tweeter application.
 * 
 * Generally, you do not need to, and should not, modify this file.  Any 
 * testing of your submitted coded will, in the first instance, be against the
 * base framework as provided.  
 * 
 * You may, however, wish to modify the base framework classes as part of your 
 * debugging or for your own interest -- that is fine, but you should ensure 
 * any such modifications are compatible with the existing base code, i.e., 
 * with regards to compilation and functionality.  You are advised to conduct 
 * your own tests against the base framework.
 */
package sep.seeter.net.message;

/**
 * A {@code SEET} message: tells a {@link sep.seeter.server.Server} instance to
 * return the currently recorded (sub)list of seets for a given topic, as
 * listed from the specified index (inclusive, starting from index 0).
 * <p>
 * After sending this message, the client should expected receive a
 * {@link sep.seeter.net.message.SeetsReply}.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class SeetsReq implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "SEET";

  /**
   * The specified topic.
   */
  public final String topic;

  /**
   * The specified index.
   */
  public final int index;  // Invariant: index >= 0 (starts at 0)

  /**
   * Create a {@code SEET} message with implicit index 0. This is equivalent to
   * {@linkplain #SeetsReq(String, int)}{@code (topic, 0)}.
   *
   * @param topic The target topic
   * @throws IllegalArgumentException If the topic is invalid or the index is
   *                                  negative
   *
   * @see sep.seeter.net.message.Message#isValidTopic(String)
   */
  public SeetsReq(final String topic) {
    this(topic, 0);
  }

  /**
   * Create a {@code SEET} message. Note: it does not matter whether or not the
   * topic is explicitly recorded by the server.
   *
   * @param topic The target topic
   * @param index The starting index (inclusive) of the list of seets to return
   * @throws IllegalArgumentException If the topic is invalid or the index is
   *                                  negative
   *
   * @see sep.seeter.net.message.Message#isValidTopic(String)
   */
  public SeetsReq(final String topic, final int index) {
    Message.isValidTopic(topic);
    if (index < 0) {
      throw new IllegalArgumentException("Invalid index value: " + index);
    }
    this.topic = topic;
    this.index = index;
  }

  @Override
  public String getHeader() {
    return SeetsReq.HEADER;
  }

  @Override
  public String toString() {
    return SeetsReq.HEADER + " " + this.index + " " + this.topic;

  }
}
