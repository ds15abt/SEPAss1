package mainPackage;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sep.seeter.server.Server;

/**
 *
 * @author user1
 */
public class ClientTest {
    
   private ByteArrayOutputStream printed = new ByteArrayOutputStream();
   private Controller controller;
    
    @Before
    public void setUp() throws IOException {
        
        String user = "Dylan";
        String host = "localHost";
        int port = Integer.parseInt("8888");
        Client client = new Client(user, host, port);
        
        Parser parser = new Parser(client);
        controller = new Controller(parser, client);
       
        Server server = new Server(8888);
        new Thread(() -> server.run()).start();
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
