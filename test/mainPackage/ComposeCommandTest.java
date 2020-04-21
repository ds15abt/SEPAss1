package mainPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sep.seeter.server.Server;

/**
 *
 * @author user1
 */
public class ComposeCommandTest {

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

        System.setOut(new PrintStream(printed));

    }

    @Test
    public void composeState() throws UnsupportedEncodingException, IOException {

        String expResult = "Drafting";
        String toRead = "compose this is some random text\nexit";
        ByteArrayInputStream input = new ByteArrayInputStream(toRead.getBytes("UTF-8"));
        System.setIn(input);

        controller.run();

        String out = printed.toString("UTF-8");

        assertTrue(out.contains(expResult));

    }
}
