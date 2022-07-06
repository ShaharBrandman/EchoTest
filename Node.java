package EchoTest;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.ArrayList;

import EchoTest.BlockInterface;

public class Node {
    private String nodeID;
    private int nodePORT;
    private String viewerKey;

    public Node(String ID, int PORT, String viewerKey) {
        this.nodeID = ID;
        this.nodePORT = PORT;
        this.viewerKey = viewerKey;
    }

    public void request() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", this.nodePORT);
            BlockInterface cmp = (BlockInterface) registry.lookup("/" + this.viewerKey + "/" + this.nodeID);
            ArrayList<String> received = cmp.getText();
            System.out.println(received);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}