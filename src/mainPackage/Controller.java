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
    LinkedList<String> draftLines = new LinkedList<>();

    public Controller(Parser parser, Client client) {
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
        String state = "Main";  // Initial state
//
//    // Holds the current draft data when in the "Drafting" state
//    String draftTopic = null;
//    List<String> draftLines = new LinkedList<>();

        // The loop
        for (boolean done = false; !done;) {

            // Print user options
            if (client.getState().equals("Main")) {
                System.out.print(helper.formatMainMenuPrompt());
            } else {  // state = "Drafting"
                System.out.print(helper.
                        formatDraftingMenuPrompt(client.getDraftTopic(), draftLines));

            }

            parser.userInput(reader);

            Command command = parser.get();
            if (command == null) {
                System.out.println("Error");
            } else {
                command.execute(client);

            }

        }
        return;
    }

    public List getDraftLines() {
        return draftLines;

    }

}
