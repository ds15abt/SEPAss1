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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sep.seeter.net.message.Bye;
import sep.seeter.net.message.Message;
import sep.seeter.net.message.TopicsReply;
import sep.seeter.net.message.SeetsReq;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.Publish;
import sep.seeter.net.message.TopicsReq;

/**
 * To be spawned by {@link sep.seeter.server.Server} to serve one Seeter client
 * concurrently with any other clients.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public class ServerThread extends Thread {

  private final Server server;
  private final Socket sock;
  private final ObjectInputStream in;
  private final ObjectOutputStream out;

  /**
   * Create a thread object for serving the connected Seeter client.
   *
   * @param server The {@link sep.seeter.server.Server} that is spawning this
   *               thread
   * @param sock   The server-side {@link java.net.Socket} of the channel
   *               established with the client
   * @throws IOException If I/O streams cannot be established with the client
   *                     over the socket.
   */
  public ServerThread(final Server server, final Socket sock) throws
      IOException {
    this.server = server;
    this.sock = sock;
    this.in = new ObjectInputStream(sock.getInputStream());
    this.out = new ObjectOutputStream(sock.getOutputStream());
  }

  @Override
  public void run() {
    debugPrintln("Accepted connection.");
    try {
      while (true) {
        Object read = this.in.readObject();
        debugPrintln("Received: " + read);
        if (!(read instanceof Message)) {  // Could replace by a separate header communication
          throw new IOException(getFormattedClientAddr()
              + "Client error, invalid message: " + read.getClass());
        }
        if (handleMessage((Message) read)) {
          return;
        }
      }
    } catch (IOException | ClassNotFoundException ex) {
      final String msg = getFormattedClientAddr() + " I/O error: "
          + ex.getMessage() + " (" + ex.getClass().getSimpleName() + ")";
      Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, msg, ex);
    } finally {
      close();
      debugPrintln("Session ended.");
    }
  }

  // @return  is terminal
  private boolean handleMessage(final Message msg) throws IOException {
    switch (msg.getHeader()) {
      case Bye.HEADER:
        return true;  // close done by calling method (run) finally
      case TopicsReq.HEADER: {
        final Message rep = new TopicsReply(this.server.getTopics());  // Non-empty
        this.out.reset();  // O/w seems to cache the List<Seet> instance reused throughout
        this.out.writeObject(rep);
        this.out.flush();
        debugPrintln("Sent: " + rep);
        break;
      }
      case SeetsReq.HEADER: {
        final SeetsReq req = (SeetsReq) msg;  // pub.index >= 0
        final List<Pair<String, String>> seets
            = this.server.getSeets(req.topic, req.index);
        final Pair<Stream<String>, Stream<String>> reduce
            = seets.stream().map(x -> new Pair<>(
            Stream.of(x.left), Stream.of(x.right)))
                .reduce(new Pair<>(Stream.empty(), Stream.empty()),
                    (z, y) -> new Pair<>(
                        Stream.concat(z.left, y.left),
                        Stream.concat(z.right, y.right)));
        final Pair<List<String>, List<String>> pair
            = new Pair<>(reduce.left.collect(Collectors.toList()),
                reduce.right.collect(Collectors.toList()));
        final Message rep = new SeetsReply(pair.left, pair.right);
        this.out.reset();  // O/w seems to cache the List<Seet> instance reused throughout
        this.out.writeObject(rep);
        this.out.flush();
        debugPrintln("Sent: " + rep);
        break;
      }
      case Publish.HEADER: {
        final Publish pub = (Publish) msg;
        Message.isValidUserId(pub.user);  // Only needed if Message further subclassed
        pub.topics.forEach(Message::isValidTopic);
        pub.lines.stream().forEach(Message::isValidBody);
        final List<Pair<String, String>> seets
            = pub.lines.stream().map(x -> new Pair<>(pub.user, x))
                .collect(Collectors.toList());
        this.server.addSeets(pub.topics, seets);
        break;
      }
      default:
        throw new IOException(getFormattedClientAddr()
            + "Client error, unexpected message: " + msg.getClass());
    }
    return false;
  }

  private void close() {
    debugPrintln("Closing connection.");
    IOException err = null;
    try {
      this.in.close();
    } catch (IOException ex) {
      err = ex;  // msg currently null
    }
    try {
      this.out.close();
    } catch (IOException ex) {
      if (err == null) {
        err = ex;
      }
    }
    try {
      this.sock.close();
    } catch (IOException ex) {
      if (err == null) {
        err = ex;
      }
    }
    if (err != null) {
      final String msg = "Connection close error: " + err.getMessage();
      Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, msg);
    }
  }

  private void debugPrintln(final String msg) {
    if (this.server.debug) {
      System.out.println(getFormattedClientAddr() + " " + msg);
    }
  }

  private String getFormattedClientAddr() {
    return "(Client " + sock.getRemoteSocketAddress() + ")";
  }

}
