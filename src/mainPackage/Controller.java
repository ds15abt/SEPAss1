package mainPackage;


import mainPackage.Command;
import mainPackage.Client;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import sep.seeter.net.message.Bye;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user1
 */
public class Controller {
    
    private Parser parser;
    private Client client; 
    
    public Controller (Parser parser, Client client){
        this.parser = parser;
        this.client = client;
        
                
    }
    
    @SuppressFBWarnings(
      value = "DM_DEFAULT_ENCODING",
      justification = "When reading console, ignore default encoding warning")      
  void run() throws IOException {

    BufferedReader reader = null;
    CLFormatter helper = null;
    try {
      reader = new BufferedReader(new InputStreamReader(System.in));


      if (client.getUser().isEmpty() || client.getHost().isEmpty()) {
        System.err.println("User/host has not been set.");
        System.exit(1);
      }
      helper = new CLFormatter(client.getHost(), client.getPort());

      if (client.printSplash = true);
      {
        System.out.print(helper.helloUser(client.getUser()));

      }
      loop(helper, reader);
    } catch (IOException | ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    } finally {
      reader.close();
      if (helper.chan.isOpen()) {
        // If the channel is open, send Bye and close
        helper.chan.send(new Bye());
        helper.chan.close();
      }
    }
  }
  
  
  
  
  
  

// Main loop: print user options, read user input and process
  void loop(CLFormatter helper, BufferedReader reader) throws IOException,
      ClassNotFoundException {

//    // The app is in one of two states: "Main" or "Drafting"
//    String state = "Main";  // Initial state
//
//    // Holds the current draft data when in the "Drafting" state
//    String draftTopic = null;
    List<String> draftLines = new LinkedList<>();

    // The loop
    for (boolean done = false; !done;) {
        

      // Print user options

      if (client.getState().equals("Main")) {
        System.out.print(helper.formatMainMenuPrompt());
      } else {  // state = "Drafting"
        System.out.print(helper.
            formatDraftingMenuPrompt(client.getDraftTopic(), draftLines));

      }

//      // Read a line of user input
//      String raw = reader.readLine();
//      if (raw == null) {
//        throw new IOException("Input stream closed while reading.");
//      }
//      // Trim leading/trailing white space, and split words according to spaces
//      List<String> split = Arrays.stream(raw.trim().split("\\ "))
//          .map(x -> x.trim()).collect(Collectors.toList());
//      String cmd = split.remove(0);  // First word is the command keyword
//      String[] rawArgs = split.toArray(new String[split.size()]);
      // Remainder, if any, are arguments

      // Process user input
//      if ("exit".startsWith(cmd)) {
//        // exit command applies in either state
//        done = true;
//      } // "Main" state commands
//      
        parser.userInput(reader);
        
        Command command = parser.get();
        if(command == null){
            System.out.println("Error");
        }
        else{
        command.execute(client);

        

        }
//        if("exit".startsWith(cmd)) {
//        done = true;
//    }
//      
//       if (state.equals("Main")) {
//        if ("compose".startsWith(cmd)) {
//          // Switch to "Drafting" state and start a new "draft"
//            state = "Drafting";
//            draftTopic = rawArgs[0];
//            
//          
//          
//        } else if ("fetch".startsWith(cmd)) {
//          // Fetch seets from server
//          helper.chan.send(new SeetsReq(rawArgs[0]));
//          SeetsReply rep = (SeetsReply) helper.chan.receive();
//          System.out.print(
//              helper.formatFetched(rawArgs[0], rep.users, rep.lines));
//        } else {
//          System.out.println("Could not parse command/args.");
//        }
//      } // "Drafting" state commands
//      else if (state.equals("Drafting")) {
//        if ("body".startsWith(cmd)) {
//          // Add a seet body line
//          String line = Arrays.stream(rawArgs).
//              collect(Collectors.joining());
//          draftLines.add(line);
//        } else if ("send".startsWith(cmd)) {
//          // Send drafted seets to the server, and go back to "Main" state
//          helper.chan.send(new Publish(user, draftTopic, draftLines));
//          state = "Main";
//          draftTopic = null;
//        } else {
//          System.out.println("Could not parse command/args.");
//        }
//      } else {
//        System.out.println("Could not parse command/args.");
//      }
    }
    return;
  }
    
}
