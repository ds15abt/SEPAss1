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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@code SEET-REPLY} message: the server reply to a
 * {@linkplain sep.seeter.net.message.SeetsReq SEET} request: the list of lines
 * recorded by the server for the specified topic, listed from the specified
 * index.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class SeetsReply implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "SEET-REPLY";

  /**
   * A list naming the users who authored the requested seets. The size of this
   * list and the order of the items contained corresponds to that of {@link
   * #lines}
   */
  public final List<String> users;

  /**
   * A list of the body lines of the requested seets. This list may be empty.
   */
  public final List<String> lines;

  /**
   * Create a new {@code SEET-REPLY} message. (Used by a server.)
   *
   * @param users The list of user names of the seet authors
   * @param lines The list of requested seet body lines
   * @throws IllegalArgumentException If users or lines is empty, or any of the
   *                                  users names or body lines are invalid
   *
   * @see sep.seeter.net.message.Message#isValidUserId(String)
   * @see sep.seeter.net.message.Message#isValidBody(String)
   */
  public SeetsReply(final List<String> users, final List<String> lines) {
    if (users.size() != lines.size()) {
      throw new IllegalArgumentException(
          "users and lines must be the same size: " + users + " , " + lines);
    }
    users.forEach(Message::isValidUserId);
    lines.forEach(Message::isValidBody);
    this.users = Collections.unmodifiableList(users);
    this.lines = Collections.unmodifiableList(lines);
  }

  @Override
  public String getHeader() {
    return SeetsReply.HEADER;
  }

  @Override
  public String toString() {
    final Iterator<String> it = this.lines.iterator();
    return SeetsReply.HEADER
        + this.users.stream().map(x -> " @" + x + " " + it.next())
            .collect(Collectors.joining());
  }
}
