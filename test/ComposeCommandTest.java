package mainPackage;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user1
 */
public class ComposeCommandTest {
    
    public ComposeCommandTest() {
    }
    
    @Before
    public void setUp() throws IOException {
        
        BufferedReader reader = null;
        String cmd = "Compose";
        String user = "user123";
        String host = "hostting";
        int port = Integer.parseInt("8888");
        Client client = new Client(user, host, port);
        
        Parser parser = new Parser(client);
        Controller controller = new Controller(parser, client);
        controller.run();
        
        String toRead = "Compose" + " Ting";
        
        
        
        ByteArrayInputStream input= new ByteArrayInputStream(toRead.getBytes("UTF-8"));
        System.setIn(input); 

        
        
        
        
        
        
        
        
        
        
        
        
        
    }

    /**
     * Test of execute method, of class ComposeCommand.
     */
    
    @Test
    public void testExecute() throws UnsupportedEncodingException{
        
        String toRead = "Compose" + " Ting";
        ByteArrayInputStream input = new ByteArrayInputStream(toRead.getBytes("UTF-8"));
        System.setIn(input);
//        System.out.println("state = " + client.state);
        
        
        
        
    }
        
    
    
    
//    @Test
//    public void testExecute() {
//        String expectedRes = "Drafting";
//    }
//   
//    @Test
//    public static String testUserInput(InputStream in,PrintStream out) {
//    Scanner reader = new Scanner(in);
//    
//    String input = reader.next("Compose");
//    
//
//    
//
//    return input;
//}
//    
//    public static int testUserInput(InputStream in,PrintStream out) {
//    Scanner keyboard = new Scanner(in);
//    out.println("Give a number between 1 and 10");
//    int input = keyboard.nextInt();
//
//    while (input < 1 || input > 10) {
//        out.println("Wrong number, try again.");
//        input = keyboard.nextInt();
//    }
//
//    return input;
//}
    
    
}
