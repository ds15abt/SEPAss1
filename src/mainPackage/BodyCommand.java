package mainPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import static com.sun.tools.javac.jvm.PoolConstant.LoadableConstant.String;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.String;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user1
 */
public class BodyCommand implements Command {

    String[] rawArgs;
//    List<String> draftLines = new LinkedList<>();

    public BodyCommand(String[] rawArgs) {
        this.rawArgs = rawArgs;

    }

    @Override
    public void execute(Client client) {
        try {
            String line = Arrays.stream(rawArgs).
                    collect(Collectors.joining());

            client.draftLines.add(line);

        } catch (Exception E) {

            System.out.println("sorry I cant accept that body. ");
            client.setStateDrafting();
        }

    }
}
