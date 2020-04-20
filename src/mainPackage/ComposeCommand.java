package mainPackage;


import mainPackage.Command;
import mainPackage.Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user1
 */
public class ComposeCommand implements Command {
    
    String [] rawArgs;
    
    public ComposeCommand(String [] rawArgs)
    {
        this.rawArgs = rawArgs;
    
    }
    
    @Override
    public void execute(Client client){
        
        
<<<<<<< HEAD
        client.state = "Drafting";
=======
        client.setStateDrafting();
>>>>>>> unittests
        
        client.draftTopic = rawArgs[0];
        
//        draftTopic = rawArgs[0];
        
    }
    
   
    
    
    
}
