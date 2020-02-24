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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import sep.seeter.net.message.Message;

/**
 * {@code Server} is a minimal but adequately functional and robust Seeter
 * server for you to develop and test your client against.
 * <p>
 * The argument required to run the server corresponds to {@link #Server(int)}:
 * the port number that the server should listen on.
 * <p>
 * You can compile and run Server using <b>NetBeans</b> (<i>e.g.</i>, Run
 * {@literal ->} Set Project Configuration {@literal ->} Customize...
 * {@literal ->} New...).
 * <p>
 * Assuming you have compiled using NetBeans (or another method), you can also
 * run Server from the command line:
 * <ul>
 * <li>
 * Run:<br> {@code C:\...\seeter> java -cp build\classes
 * sep.seeter.server.Server 8888}
 * </ul>
 * <p>
 * <i>Note:</i> On some platforms (<i>e.g.</i>, Windows), you may be prompted
 * with a security warning due to the Server opening a network socket. You must
 * allow this for the Server to function.
 * <p>
 * An instance of {@code Server} understands and communicates the messages in
 * {@link sep.seeter.net.message}.
 * <ul>
 * <li> {@link sep.seeter.net.message.Bye} - tells the {@code Server} to end
 * this communication session with the sender client.
 * <li> {@link sep.seeter.net.message.TopicsReq} - request the set of topics
 * recorded by the {@code Server}.
 * <ul>
 * <li> {@link sep.seeter.net.message.TopicsReply} - the {@code Server}'s
 * reply: the set of topics (possibly empty).
 * </ul>
 * <li> {@link sep.seeter.net.message.SeetsReq} - request the (sub)list of
 * seets recorded by the {@code Server} for the specified topic, from the
 * specified index (inclusive, starting from index 0). Note: The topic may not
 * be <i>explicitly</i> recorded by the {@code Server}.
 * <ul>
 * <li> {@link sep.seeter.net.message.SeetsReply} - the {@code Server}'s reply,
 * the list of seets (possibly empty).
 * </ul>
 * <li> {@link sep.seeter.net.message.Publish} - append a (non-empty) list of
 * seets to each (possibly new) topic in the (non-empty) set of topics.
 * </ul>
 * <p>
 * Your client should use {@link sep.seeter.net.channel.ClientChannel} top send
 * and receive messages of the above types with Server: see the
 * {@link sep.seeter.net.channel.ClientChannel#send(Message)} and
 * {@link sep.seeter.net.channel.ClientChannel#receive()} methods.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public class Server implements AutoCloseable {

  protected final boolean debug;

  private final ServerSocket servSock;

  private final Object mutex = new Object();

  /*
   * {@code db} maps a topic to a list of <user, line> pairs.
   * {@code Server} is neutral w.r.t. topic/seet data types used by a client -
   * it simply records topics, user names and text as strings.
   */
  private final Map<String, List<Pair<String, String>>> db = new HashMap<>();


  /**
   * Create a new Seeter servSock, bound to the specified port. This is
   * equivalent to
   * {@linkplain #Server(boolean,int) Server}{@code (false, port)}.
   *
   * @param port The port number that the servSock will listen on
   * @throws IOException If the servSock socket could not be opened
   */
  public Server(final int port) throws IOException {
    this(false, port);
  }

  /**
   * Create a new Seeter servSock, bound to the specified port, with debug
   * output enabled or disabled.
   *
   * @param debug Enable or disable local debug output printing ({@code true}
   *              means enabled)
   * @param port  The port number that the servSock will listen on
   * @throws IOException If the servSock socket could not be opened
   */
  public Server(final boolean debug, final int port) throws IOException {
    this.debug = debug;
    this.servSock = new ServerSocket(port);
  }

  /**
   * To be called once, and once only, on a {@code Seeter} instance to start
   * accepting and serving client connections.
   */
  public void run() {
    while (true) {
      try {
        final Socket s = this.servSock.accept();
        new ServerThread(this, s).start();
      } catch (SocketException ex) {
        // Server socket closed -- swallow silently
      } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.WARNING,
            "Connection accept error: ", ex);
      }
    }
  }

  /**
   * Shut down this Seeter servSock.
   */
  @Override
  public void close() {
    try {
      this.servSock.close();
    } catch (IOException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.WARNING,
          "Server socket close error: ", ex);
    }
  }

  /* Seets -- synchronized methods (keep critical sections small) */

  protected void addSeets(final Set<String> topics,
      final List<Pair<String, String>> seets) {
    synchronized (this.mutex) {
      for (final String topic : topics) {
        List<Pair<String, String>> tmp = this.db.get(topic);
        if (tmp == null) {
          tmp = new LinkedList<>();
          this.db.put(topic, tmp);
        }
        tmp.addAll(seets);
      }
    }
  }

  protected Set<String> getTopics() {
    synchronized (this.mutex) {
      return new HashSet<>(this.db.keySet());
    }
  }

  // Pre: req.index >= 0 -- guaranteed by SeetsReq
  protected List<Pair<String, String>> getSeets(final String topic,
      final int index) {
    synchronized (this.mutex) {
      if (!this.db.containsKey(topic)) {
        return Collections.emptyList();
      }
      final List<Pair<String, String>> seets = this.db.get(topic);
      if (index >= seets.size()) {
        return Collections.emptyList();
      }
      return Collections.unmodifiableList(seets.subList(index, seets.size()));
      // ^Because SubList is not Seriablizable
    }
  }

  /* Main */

  /**
   * Create and run a {@code Seeter} instance.
   *
   * @param args Use {@code -h} to list the supported options
   * @throws IOException If the servSock could not be created
   */
  public static void main(final String[] args) throws IOException {
    final String help
        = "Usage:  java [-cp ...] sep.seeter.server.Server [-s] port\n"
        + "            --silent -s  Suppress debug messages\n"
        + "            --help   -h  Print this help message";
    if (Arrays.stream(args)
        .anyMatch(x -> "--help".equals(x) || "-h".equals(x))) {
      System.out.println(help);
      System.exit(0);
    } else if (args.length < 1) {
      System.err.println(help);
      System.exit(1);
    }
    final int port = Integer.parseInt(args[args.length - 1]);
    final boolean silent = args.length > 1
        && Stream.of("--silent", "-s").anyMatch(x -> x.equals(args[0]));
    try (final Server s = new Server(!silent, port)) {
      s.run();
    }
  }
}
