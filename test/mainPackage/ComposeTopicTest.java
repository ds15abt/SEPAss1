///* 
//* This test is designed to verify that the compose topic command works 
//*This checks whether the topic is saved and displayed on output
// */
//package mainPackage;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import static java.lang.System.in;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import sep.seeter.server.Server;
//
///**
// *
// * @author user1
// */
//public class ComposeTopicTest {
//
//    private static Server server;
//    private ByteArrayOutputStream printed = new ByteArrayOutputStream();
//    private ByteArrayInputStream in;
//    private Controller controller;
//
//    public ComposeTopicTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    private void inputMethod(String out) throws IOException {
//        in = new ByteArrayInputStream(out.getBytes());
//        System.setIn(in);
//
//    }
//
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    // @Test
//    // public void hello() {}
//}
