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
package sep.seeter.server;

/**
 * Helper utility for {@link sep.seeter.server.Server} - namely, a seet is a
 * pair of strings (user name, line).
 * <p>
 * The references of a <tt>Pair</tt> to its elements cannot be changed after it
 * is created. A <tt>Pair</tt> is thus immutable if its elements are immutable.
 *
 * @param <L> The type of the left element
 * @param <R> The type of the right element
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public class Pair<L, R> {

  /**
   * The left element of this pair.
   */
  public final L left;

  /**
   * The right element of this pair.
   */
  public final R right;

  /**
   * Create a new pair.
   *
   * @param left  The left element of the pair
   * @param right The right element of the pair
   */
  public Pair(final L left, final R right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public String toString() {
    return "(" + this.left + ", " + this.right + ")";
  }
}
