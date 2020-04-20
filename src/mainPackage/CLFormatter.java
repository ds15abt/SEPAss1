package mainPackage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import sep.seeter.net.channel.ClientChannel;
import sep.seeter.net.message.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * A helper class for the current prototype {@link Client client}.  <i>E.g.</i>,
 * for formatting Command Line messages.
 */
public class CLFormatter {

  static ClientChannel chan;  // Client-side channel for talking to a Seeter server
  static Internationalisation inter = new Internationalisation();
  

  CLFormatter(String host, int port) {
    this.chan = new ClientChannel(host, port);
  }

  /* Interact with Seeter server */

  private void send(Message msg) throws IOException {
    this.chan.send(msg);
  }

  private Message receive() throws IOException, ClassNotFoundException {
    return this.chan.receive();
  }

  /* Following are the auxiliary methods for formatting the UI text */

  static String helloUser(String user) {
      return inter.printlnWithParams(inter.strings.getString("welcome_message"), user)+ inter.strings.getString("note_message");
      
//    return "\nHello " + user + "!\n"
//        + "Note:  Commands can be abbreviated to any prefix, "
//        + "e.g., fe [mytopic].\n";
  }

  static String formatMainMenuPrompt() {
    return "\n[Main] Enter command: "
        + "fetch [mytopic], "
        + "compose [mytopic], "
        + "exit"
        + "\n> ";
  }

  static String formatDraftingMenuPrompt(String topic,
      List<String> lines) {
    return "\nDrafting: " + formatDrafting(topic, lines)
        + "\n[Drafting] Enter command: "
        + "body [mytext], "
        + "send, "
        + "exit"
        + "\n> ";
  }

  static String formatDrafting(String topic, List<String> lines) {
    StringBuilder b = new StringBuilder("#");
    b.append(topic);
    int i = 1;
    for (String x : lines) {
      b.append("\n");
      b.append(String.format("%12d", i++));
      b.append("  ");
      b.append(x);
    };
    return b.toString();
  }

  static String formatFetched(String topic, List<String> users,
      List<String> fetched) {
    StringBuilder b = new StringBuilder("Fetched: #");
    b.append(topic);
    Iterator<String> it = fetched.iterator();
    for (String user : users) {
      b.append("\n");
      b.append(String.format("%12s", user));
      b.append("  ");
      b.append(it.next());
    };
    b.append("\n");
    return b.toString();
  }
}
