package mainPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author user1
 */
public class BodyCommand implements Command {
    
    String [] rawArgs;
    List<String> draftLines = new LinkedList<>();
   
    public BodyCommand(String [] rawArgs)
    {
        this.rawArgs = rawArgs;
    
    }

        @Override
    public void execute(Client client){
        
        client.state = "Drafting";
        
        String line = Arrays.stream(rawArgs).
        collect(Collectors.joining());
        draftLines.add(line);


        
    }
}
