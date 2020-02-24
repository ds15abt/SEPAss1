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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A {@code PUB} message: tells a {@link sep.seeter.server.Server} instance to
 * append a list of seets from a user to each (possibly new) topic in the set
 * of topics.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class Publish implements Message {

  private static final long serialVersionUID = 1L;  // Generated

  /**
   * The header code.
   */
  public static final String HEADER = "PUB";

  /**
   * The name of the user publishing these seets.
   *
   * @see sep.seeter.net.message.Message#isValidUserId(String)
   */
  public final String user;

  /**
   * The non-empty set of target topics. The set is not modifiable.
   *
   * @see sep.seeter.net.message.Message#isValidTopic(String)
   */
  public final Set<String> topics;

  /**
   * The non-empty list of seet body lines. The list is not modifiable.
   *
   * @see sep.seeter.net.message.Message#isValidBody(String)
   */
  public final List<String> lines;

  /**
   * Create a {@code PUB} message for a single topic. This is equivalent to
   * {@linkplain #Publish(String, Set, List)} where the {@code Set} is a
   * singleton containing {@code topic}.
   *
   * @param user  A user name
   * @param topic The target topic
   * @param lines A non-empty list of seet body lines to append to each topic
   *              non-empty
   * @throws IllegalArgumentException If topics or lines is empty, or any of
   *                                  the user name, topics or body lines are
   *                                  invalid
   *
   * @see sep.seeter.net.message.Message#isValidUserId(String)
   * @see sep.seeter.net.message.Message#isValidTopic(String)
   * @see sep.seeter.net.message.Message#isValidBody(String)
   */
  public Publish(final String user, final String topic,
      final List<String> lines) {
    this(user, Stream.of(topic).collect(Collectors.toSet()), lines);
  }

  /**
   * Create a {@code PUB} message.
   *
   * @param user   A user name
   * @param topics A non-empty set of target topics
   * @param lines  A non-empty list of seet body lines to append to each topic
   *               non-empty
   * @throws IllegalArgumentException If topics or lines is empty, or any of
   *                                  the user name, topics or body lines are
   *                                  invalid
   *
   * @see sep.seeter.net.message.Message#isValidUserId(String)
   * @see sep.seeter.net.message.Message#isValidTopic(String)
   * @see sep.seeter.net.message.Message#isValidBody(String)
   */
  public Publish(final String user, final Set<String> topics,
      final List<String> lines) {
    if (topics.isEmpty()) {
      throw new IllegalArgumentException("topics set should be non-empty.");
    }
    if (lines.isEmpty()) {
      throw new IllegalArgumentException("seets list should be non-empty.");
    }
    Message.isValidUserId(user);
    topics.forEach(Message::isValidTopic);
    lines.forEach(Message::isValidBody);
    this.user = user;
    this.topics = Collections.unmodifiableSet(topics);
    this.lines = Collections.unmodifiableList(lines);
  }

  @Override
  public String getHeader() {
    return Publish.HEADER;
  }

  @Override
  public String toString() {
    return Publish.HEADER
        + this.topics.stream().map(x -> " #" + x)
            .collect(Collectors.joining())
        + this.lines.stream().map(x -> " @" + this.user + " " + x)
            .collect(Collectors.joining());
  }
}
