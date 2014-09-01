package Chord;

import Application.Node;
import Application.NodeImpl;
import DHash.BigIntImpl;
import java.rmi.RemoteException;
import DHash.IdKey;
import Utilities.Log;

/**
 * thread that runs periodically and fixes a random finger of the finger table
 * @author ChordFPG Team
 * @version 1.0
 */
public class FixFingers implements Runnable {

    private final Node node;
    int nextFinger;

    public FixFingers(Node node) {
        this.node = node;
        nextFinger = 0;
    }

    public void run() {

        try {
            System.out.println("\nFix Fingers called at: " + node.getLocalID().toString());
        } catch (RemoteException ex) {
            Log.addMessage("A problem occurred in Fix Fingers. Try again later", Log.ERROR);
        }

        nextFinger += 1;

        if (nextFinger > 160) {
            nextFinger = 1;
        }

        /*
         * get the IdKey of the node that is successor of the id
         * (node's ID + 2^(nextFinger-1))
         */
        IdKey Finger = null;
        try {


            byte[] array = node.getLocalID().getHashKey().addpowerOfTwo(nextFinger - 1).modM(160);

            IdKey temp = new IdKey(new BigIntImpl(array), node.getLocalID().getPID(), node.getLocalID().getIP());
            System.out.println("temp = " + temp.hashKeytoHexString());

            if (temp.equals(node.getLocalID())) {
                return;
            }

            Finger = node.find_successor_ID(temp);
        } catch (java.rmi.ConnectException ce) {
            Log.addMessage("FixFingers: Could not connect with node... Message from the beyond:P", Log.ERROR);
        } catch (RemoteException ex) {
            Log.addMessage("A problem occurred in Fix Fingers. Try again later...", Log.ERROR);
        } catch (NullPointerException nexc) {
            ((NodeImpl) node).clearFingers();
            Finger = null;
        }
        try {
            //set it as the new IdKey in the fingerTable

            if (Finger != null) {

                node.setFinger(Finger, nextFinger - 1);

            }
        } catch (RemoteException ex) {
            Log.addMessage("A problem occurred in Fix Fingers. Try again later...", Log.ERROR);
        }
        if (Finger != null) {
            System.out.println("Finger: " + Finger.toString() + " => " + (nextFinger - 1));
        } else {
            nextFinger = 0;
        }
    }
}