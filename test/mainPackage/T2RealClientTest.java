package mainPackage;

/*
 * Checks to make sure user data is saved correctly in the client class
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
public class T2RealClientTest {

    private ByteArrayOutputStream printed = new ByteArrayOutputStream();
    private Controller controller;

    @Test
    public void checkUserDeets() throws IOException {
        String user = "Dylan";
        String host = "localHost";
        int port = Integer.parseInt("8888");
        Client client = new Client(user, host, port);

        Parser parser = new Parser(client);
        controller = new Controller(parser, client);

        Server server = new Server(8888);
        new Thread(() -> server.run()).start();

        assertEquals("Dylan", client.getUser());
        assertEquals("localHost", client.getHost());
        assertEquals(8888, client.getPort());

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
