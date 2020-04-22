/*
 * This test is to make sure that the state is set to main upon running the program 
 * and before the user has input anything
 */
package mainPackage;

//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sep.seeter.server.Server;

/**
 *
 * @author user1
 */
public class T1ClientTest {

    private static Server server;
    private ByteArrayOutputStream printed = new ByteArrayOutputStream();

    private Controller controller;
    private String clientState;

    @Test
    public void TestBodyDrafting() throws IOException {
        String user = "Dylan";
        String host = "localHost";
        int port = Integer.parseInt("1234");
        Client client = new Client(user, host, port);

        Parser parser = new Parser(client);
        controller = new Controller(parser, client);

        System.setOut(new PrintStream(printed));

        String clientState = client.getState();
        String expRes = "Main";

        assertEquals(expRes, clientState);

    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        server = new Server(1234);
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
