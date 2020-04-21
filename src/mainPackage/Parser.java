package mainPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user1
 */
public class Parser {

    private Command command;
    private Client client;

    private String cmd;
    private String[] rawArgs;

    public Parser(Client client) {
        this.client = client;

    }
//    private CLFormatter;

    public void userInput(BufferedReader reader) throws IOException {

        String raw = reader.readLine();
        if (raw == null) {
            throw new IOException("Input stream closed while reading.");
        }
        // Trim leading/trailing white space, and split words according to spaces
        List<String> split = Arrays.stream(raw.trim().split("\\ "))
                .map(x -> x.trim()).collect(Collectors.toList());
        cmd = split.remove(0);  // First word is the command keyword
        rawArgs = split.toArray(new String[split.size()]);
    }

    public Command get() throws IOException {

        switch (cmd) {
            case "exit":
                return command = new ExitCommand();

            case "compose":
                return command = new ComposeCommand(rawArgs);

            case "body":
                return command = new BodyCommand(rawArgs);

            case "fetch":
                return command = new FetchCommand(rawArgs);

            case "send":
                return command = new SendCommand(client.getUser(), client.getDraftTopic(), client.getDraftLines());

            default:
                System.out.println("Error could not get() from Parser");
                return command;

        }

    }

}
