package mainPackage;

import mainPackage.Command;
import mainPackage.Client;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.message.Message;
import sep.seeter.net.message.Publish;
import sep.seeter.net.message.SeetsReq;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user1
 */
public class SendCommand implements Command {

    String[] rawArgs;
    CLFormatter helper;

//    List<String> draftLines = new LinkedList<>();
    public SendCommand(String user, String draftTopic, List<String> rawArgs) throws IOException {

        List<String> draft = new LinkedList();
        draft.add(rawArgs.get(0));

        System.out.println(draft);
        helper.chan.send(new Publish(user, draftTopic, draft));
    }

    @Override
    public void execute(Client client) {
        client.setStateMain();

        try {

            helper.chan.send(new Publish(client.getUser(), client.draftTopic, client.draftLines));

        } catch (IOException ex) {
            Logger.getLogger(SendCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.draftTopic = null;

    }

}
