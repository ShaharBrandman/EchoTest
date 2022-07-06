package EchoTest;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

import EchoTest.BlockInterface;

public class Block extends UnicastRemoteObject implements BlockInterface{

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