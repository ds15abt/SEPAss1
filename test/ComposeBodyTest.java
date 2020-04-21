package mainPackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sep.seeter.server.Server;

public class ComposeBodyTest {

    private ByteArrayOutputStream printed = new ByteArrayOutputStream();
    private Controller controller;
    Server server;

    @After
    public void tearDown() {
        server.close();

    }

    @Test
    public void thisbodytestmet() throws UnsupportedEncodingException, IOException {

        ByteArrayInputStream in = new ByteArrayInputStream("compose working\n or maybe not \nexit".getBytes("UTF-8"));

        System.setIn(in);
        Client.main(new String[]{"Dylan", "localHost", "8888"});

        String str = printed.toString("UTF-8");

        assertTrue(str.contains("Drafting: #test"));
    }
}
