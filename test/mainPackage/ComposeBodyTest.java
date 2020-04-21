package mainPackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sep.seeter.server.Server;

public class ComposeBodyTest {

    private static Server server;
    private ByteArrayOutputStream printed = new ByteArrayOutputStream();
    private ByteArrayInputStream in;
    private Controller controller;

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

    @After
    public void tearDown() {

    }

    private void inputMethod(String out) throws IOException {
        in = new ByteArrayInputStream(out.getBytes());
        System.setIn(in);

        //Run the client
        String user = "Dylan";
        String host = "localHost";
        int port = Integer.parseInt("1234");
        Client client = new Client(user, host, port);

        Parser parser = new Parser(client);
        controller = new Controller(parser, client);

        System.setOut(new PrintStream(printed));

    }

    //REFERENCE TEST
    @Test
    public void TestBodyCommand() throws IOException {
        inputMethod("compose topic\n"
                + "body");
        Assert.assertTrue(printed.toString().contains("Drafting: #topic"));
    }

//    @Test
//    public void thisbodytestmet() throws UnsupportedEncodingException, IOException {
//
//        ByteArrayInputStream in = new ByteArrayInputStream("compose working\n send it \nexit".getBytes("UTF-8"));
//
//        System.setIn(in);
//        Client.main(new String[]{"Dylan", "localHost", "8888"});
//
//        String str = printed.toString("UTF-8");
//
//        assertTrue(str.contains("Drafting: #test"));
//    }
}
