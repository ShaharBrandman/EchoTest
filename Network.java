package EchoTest;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import java.util.ArrayList;

import EchoTest.Block;

import java.lang.Runnable;

public class Network implements Runnable{
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