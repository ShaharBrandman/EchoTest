import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.AlreadyBoundException;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

import java.util.ArrayList;

public class runWhole {
    public static void main(String[] args) throws AlreadyBoundException, SQLException, ClassNotFoundException, InterruptedException{
        String networkID = "EchoTest";
        String nodeID = "EchoTest";
        int PORT = 69;
        String viewerKey = "KKK";
        
        //new Thread(new Network(networkID, PORT, viewerKey)).start();

        //Thread.sleep(500);

        new Node(nodeID, PORT, viewerKey).request();
    }
    
    private static class Network implements Runnable{
        private String networkID;
        private int networkPORT;
        private String viewerKey;

        public Network(String ID, int PORT, String viewKey) {
            this.networkID = ID;
            this.networkPORT = PORT;
            this.viewerKey = viewKey;
        }

        @Override
        public void run() {
            try {
                ArrayList<String> textRequested = new ArrayList<String>();
                textRequested.add("example1");
                textRequested.add("example2");

                Block data = new Block();

                data.setText(textRequested);

                Registry registry = LocateRegistry.createRegistry(this.networkPORT);

                registry.bind("/" + this.viewerKey + "/" + this.networkID, data);

                System.out.println("Server starts....");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            catch (AlreadyBoundException e) {
                e.printStackTrace();
            }
    }
}
    
    private static class Node {
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
    
        private class Block extends UnicastRemoteObject implements BlockInterface{

            private static final long serialVersionUID = 1L;
            private ArrayList<String> text;

            protected Block() throws RemoteException {
                super();
            }

            public ArrayList<String> getText() {
                return text;
            }

            public void setText(ArrayList<String> text) {
                this.text = text;
            }
        }

    }

    private static class Block extends UnicastRemoteObject implements BlockInterface{

        private static final long serialVersionUID = 1L;
        private ArrayList<String> text;
    
        protected Block() throws RemoteException {
            super();
        }
    
        public ArrayList<String> getText() {
            return text;
        }
    
        public void setText(ArrayList<String> text) {
            this.text = text;
        }
    }

    private static interface BlockInterface extends Remote {
        public ArrayList<String> getText()  throws RemoteException;
        public void setText(ArrayList<String> text) throws RemoteException;
    }
}