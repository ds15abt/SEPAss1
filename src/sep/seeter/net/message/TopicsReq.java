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
 * A {@code TOPIC} message: tells a {@link sep.seeter.server.Server} instance
 * to return the currently recorded set of topics. This set may be empty.
 * <p>
 * After sending this message, the client should expected receive a
 * {@link sep.seeter.net.message.TopicsReply}.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class TopicsReq implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "TOPIC";

  /**
   * Create a new {@code TOPIC} message.
   */
  public TopicsReq() {
    // Empty constructor
  }

  @Override
  public String getHeader() {
    return TopicsReq.HEADER;
  }

  @Override
  public String toString() {
    return TopicsReq.HEADER;

  }
}
