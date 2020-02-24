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
package sep.seeter.net.channel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.message.Message;

/**
 * A client-side channel for interacting with an instance of
 * {@code sep.seeter.server.Server}.
 * <p>
 * Usage protocol:
 * <ul>
 * <li>
 * Create a new instance of
 * {@linkplain #ClientChannel(String, int) ClientChannel} supplying the host
 * name and port number of the server.
 * <li>
 * Use {@linkplain #send(Message) send} and {@linkplain #receive() receive} to
 * exchange instances of {@link sep.seeter.net.message.Message} with the
 * server. These calls may be performed in any <i>sequence</i> (note:
 * {@code ClientChannel} is <i>not</i> thread-safe) that respects the message
 * protocol (see the documentation for each message), and any number of times
 * (possibly zero), up to the occurrence of any error (<i>e.g.</i>,
 * {@code IOException}).
 * <li>
 * Use {@linkplain #close() close} to close the communication session.
 * </ul>
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public class ClientChannel implements AutoCloseable {

  private final String host;
  private final int port;

  private Socket sock;  // isOpen() <=> this.sock != null
  private ObjectOutputStream out;
  private ObjectInputStream in;

  /**
   * Allocate a new client channel object for communicating with an instance of
   * {@code sep.seeter.server.Server} listening at the specified host and port.
   *
   * @param host The server host, <i>e.g.</i>, {@code "localhost"}
   * @param port The server port, <i>e.g.</i>, {@code 8888}
   */
  public ClientChannel(final String host, final int port) {
    this.host = host;
    this.port = port;
    this.sock = null;
  }

  /**
   * Returns {@code true} if, and only if, this channel is open, meaning that
   * it currently encapsulates a TCP socket.
   *
   * @return whether this channel is currently open
   */
  public boolean isOpen() {
    return sock != null;
  }

  private void open() throws IOException {
    if (!isOpen()) {
      this.sock = new Socket(host, port);
      this.out = new ObjectOutputStream(this.sock.getOutputStream());
      this.in = new ObjectInputStream(this.sock.getInputStream());
    }
  }

  /**
   * Send a message to the server specified at channel creation.
   *
   * @param msg The message to send
   * @throws IOException If an I/O error occurs
   */
  public void send(final Message msg) throws IOException {
    try {
      if (isOpen()) {
        this.out.reset();
      } else {
        open();
      }
      this.out.writeObject(msg);
      this.out.flush();
    } catch (IOException ex) {
      try {
        close();
      } catch (IOException ex1) {
        Logger.getLogger(ClientChannel.class.getName()).log(Level.WARNING,
            "Connection close error: ", ex1);
      }
      throw ex;
    }
  }

  /**
   * Receive a message from the server specified at channel creation. This
   * method blocks (<i>i.e.</i>, does not return) until a message is received,
   * or an error occurs.
   *
   * @return The received message, if successful
   * @throws IOException            If an I/O error occurs
   * @throws ClassNotFoundException Class of serialized object cannot be found
   */
  public Message receive() throws IOException, ClassNotFoundException {
    Object obj;
    try {
      if (!isOpen()) {
        open();
      }
      obj = this.in.readObject();
    } catch (IOException ex) {
      try {
        close();
      } catch (IOException ex1) {
        Logger.getLogger(ClientChannel.class.getName()).log(Level.WARNING,
            "Connection close error: ", ex1);
      }
      throw ex;
    }
    if (!(obj instanceof Message)) {
      throw new IOException("[Server error]  Received unexpected object type: "
          + obj.getClass());
    }
    return (Message) obj;
  }

  /**
   * Close the encapsulated socket and I/O streams, if any currently open.
   * <p>
   * Guaranteed post condition: {@code !isOpen()}. This operation is idempotent
   * provided the other methods are not called.
   *
   * @throws IOException If an I/O error occurs
   */
  @Override
  public void close() throws IOException {
    IOException err = null;
    if (this.in != null) {
      try {
        this.in.close();  // Idempotent
      } catch (IOException ex) {
        err = ex;  // err currently null
      } finally {
        this.in = null;
      }
    }
    if (this.out != null) {
      try {
        this.out.close();  // Idempotent
      } catch (IOException ex) {
        if (err == null) {
          err = ex;
        }
      } finally {
        this.out = null;
      }
    }
    if (this.sock != null) {  // i.e., isOpen
      try {
        this.sock.close();  // Idempotent
      } catch (IOException ex) {
        if (err == null) {
          err = ex;
        }
      } finally {
        this.sock = null;  // isOpen -> false
      }
    }
    if (err != null) {
      throw err;
    }
  }
}
