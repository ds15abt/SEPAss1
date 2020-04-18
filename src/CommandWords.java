
import java.util.HashMap;
import java.util.Map;

/*
 * Responsible for storing of all commands
 * 
 * perhaps extra functionality for getting a command (HashMap?)
 */

/**
 *
 * @author Dylan
 */
public class CommandWords {
    String [] rawArgs;
    private HashMap<String, Command> commands = new HashMap<>();
    
    
    public CommandWords() {
        
        commands = new HashMap<String, Command>();
        
        
        commands.put("exit", new ExitCommand());
        commands.put("compose", new ComposeCommand(rawArgs));
        commands.put("fetch", new FetchCommand());
        commands.put("body", new BodyCommand());
        commands.put("send", new SendCommand());
        commands.put("discard", new DiscardCommand());
        commands.put("list", new ListCommand());
        
    }
    
    
    public Command get(String cmd){
        
        return commands.get(cmd);
            
    }
    
    
    public boolean isValidCommand (String cmd){
        
        return commands.containsKey(cmd);
    }
    
    public String listAll() {
        String list = "";
        for(String s : commands.keySet() ){
            list += s + " ";
        }
        return list;
    }
    
}
