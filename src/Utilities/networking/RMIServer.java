package Utilities.networking;

import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author ChordFPG Team
 * @version 1.0
 */
public class RMIServer implements Runnable {

    boolean listen;

    public void run() {

        try {

            MulticastSocket listeningSocket = new MulticastSocket(4446);
            DatagramSocket responseSocket = new DatagramSocket();
            InetAddress group = InetAddress.getByName("230.0.0.1");

            byte[] buff = new byte[32];
            DatagramPacket answer = new DatagramPacket(buff, buff.length);

            /*Join the Multicast group for RMI Server*/
            listeningSocket.joinGroup(group);

            DatagramPacket packet_out = null;

            System.out.println("RMI Server has started.");

            while (!Thread.interrupted()) {

                listeningSocket.receive(answer);

                /*Generate the answer*/
                packet_out = new DatagramPacket(buff, buff.length, answer.getAddress(), 4440);
                responseSocket.send(packet_out);

            }

        } catch (Exception ioe) //Generic Exception
        {
        }
    }
}