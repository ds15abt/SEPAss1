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

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@code TOPIC-REPLY} message: the server reply to a
 * {@linkplain sep.seeter.net.message.TopicsReq TOPIC} request: the set of
 * topics explicitly recorded by the server. The set may be empty.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class TopicsReply implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "TOPIC-REPLY";

  /**
   * A set of the topics explicitly recorded by the server. Possibly empty.
   */
  public final Set<String> topics;

  /**
   * Create a new {@code TOPIC-REPLY} message. (Used by a server.)
   *
   * @param topics The set of requested topics
   * @throws IllegalArgumentException If any topic is invalid
   *
   * @see sep.seeter.net.message.Message#isValidTopic(String)
   */
  public TopicsReply(final Set<String> topics) {
    topics.forEach(x -> Message.isValidTopic(x));
    this.topics = Collections.unmodifiableSet(topics);
  }

  @Override
  public String getHeader() {
    return TopicsReply.HEADER;
  }

  @Override
  public String toString() {
    return TopicsReply.HEADER + " "
        + this.topics.stream().map(x -> "#" + x)
            .collect(Collectors.joining(" "));
  }
}
