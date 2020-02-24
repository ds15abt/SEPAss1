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

import java.io.Serializable;

/**
 * A base interface for messages that can be communicated using
 * {@link sep.seeter.net.channel.ClientChannel} with an instance of
 * {@link sep.seeter.server.Server}.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public interface Message extends Serializable {

  /**
   * Returns the <i>unique</i> header code for this message. The code should be
   * a non-empty word composed of digits, letters, underscores and hyphens.
   *
   * @return The header code
   */
  String getHeader();

  /* Name well-formedness checks */

  /**
   * Validate a user name.
   *
   * @param name The name to validate
   */
  static void isValidUserId(String name) {
    if (name.isEmpty() || name.length() > 8) {
      throw new IllegalArgumentException(
          "User name should be non-empty and not longer than 8 characters.");
    }
    if (!name.matches("^[a-zA-Z0-9]*$")) {
      throw new IllegalArgumentException(
          "User name should be alphanumeric, not: " + name);
    }
  }

  /**
   * Validate a topic name.
   *
   * @param name The name to validate
   */
  static void isValidTopic(String name) {
    if (name.isEmpty() || name.length() > 8) {
      throw new IllegalArgumentException(
          "Topic name should be non-empty and not longer than 8 characters.");
    }
    if (!name.matches(
        "^[a-zA-Z0-9]*$")) {
      throw new IllegalArgumentException(
          "Topic should be alphanumeric, not: " + name);
    }
  }

  /**
   * Validate a seet body line.
   *
   * @param body The line to validate
   */
  static void isValidBody(String body) {
    if (body.isEmpty() || body.length() > 48) {
      throw new IllegalArgumentException(
          "Seet body should be non-empty and not longer than 48 characters.");
    }
    if (body.contains(System.getProperty("line.separator"))) {
      throw new IllegalArgumentException(
          "Seet body should be a single line, not: " + body);
    }
  }
}
