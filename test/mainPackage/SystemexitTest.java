/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import sep.seeter.server.Server;

/**
 *
 * @author user1
 */
public class SystemexitTest {

    private static Server server;
    private ByteArrayOutputStream printed = new ByteArrayOutputStream();
    private ByteArrayInputStream in;
    private Controller controller;
    private Client client;
    private final PrintStream origOut = System.out;

    public SystemexitTest() {
    }

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    private void inputMethod(String out) throws IOException {
        in = new ByteArrayInputStream(out.getBytes());
        System.setIn(in);
        String user = "Dylan";
        String host = "localhost";
        int port = Integer.parseInt("8888");
        client = new Client(user, host, port);

        Parser parser = new Parser(client);
        controller = new Controller(parser, client);

        System.setOut(new PrintStream(printed));

        String clientState = client.getState();
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        server = new Server(8888);
        new Thread(() -> server.run()).start();
    }

    @AfterClass
    public static void tearDownClass() {
        server.close();
    }

    @Before
    public void setUp() {
        printed = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printed));
    }

    @Test
    public void testExit() throws IOException {

        exit.expectSystemExit();
        inputMethod("exit");
    }
}
