package EchoTest;

import EchoTest.*;

import java.rmi.AlreadyBoundException;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

public class run {
    public static void main(String[] args) throws AlreadyBoundException, SQLException, ClassNotFoundException, InterruptedException{
        String networkID = "EchoTest";
        String nodeID = "EchoTest";
        int PORT = 69;
        String viewerKey = "KKK";
        
        new Thread(new Network(networkID, PORT, viewerKey)).start();;

        Thread.sleep(500);

        new Node(nodeID, PORT, viewerKey).request();
    }
}
