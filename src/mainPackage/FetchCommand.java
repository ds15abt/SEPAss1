package mainPackage;


import mainPackage.Command;
import mainPackage.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.message.SeetsReply;
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
public class FetchCommand implements Command {
    
    String [] rawArgs;
    CLFormatter helper;

    public FetchCommand(String[] rawArgs) {
        this.rawArgs = rawArgs; //To change body of generated methods, choose Tools | Templates.
    }

        @Override
    public void execute( Client client){
        
        client.setStateMain();
        try {
            helper.chan.send(new SeetsReq(rawArgs[0]));
        } catch (IOException ex) {
            Logger.getLogger(FetchCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
          SeetsReply rep = null;
        try {
            rep = (SeetsReply) helper.chan.receive();
        } catch (IOException ex) {
            Logger.getLogger(FetchCommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FetchCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
          System.out.print(
             helper.formatFetched(rawArgs[0], rep.users, rep.lines));
       } 
        
    
}
