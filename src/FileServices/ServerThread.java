package FileServices;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;

/**
 * @author ChordFPG Team
 * @version 1.0
 */
public class ServerThread implements Runnable {

    private Share share;
    private ServerSocket serverSocket;
    private int port;
    volatile boolean stop = false;

    /**
     *
     * @param share The Share object that contains files to be served.
     */
    public ServerThread(Share share, int port) {
        this.share = share;
        this.port = port;
    }

    public ServerThread(Share share) {
        this.share = share;
        this.port = 5000;
    }

    public void run() {
        System.out.println("Fileserver started");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ioe1) {
            System.out.println(ioe1.getMessage());
        }
        while (!stop) {
            try {

                Socket generatedSocket;

                if (serverSocket == null) {
                    throw new NullPointerException();
                }

                generatedSocket = serverSocket.accept();
                System.out.println("Connection accepted");
                Thread con = new Thread(new Connection(generatedSocket, share));
                con.start();
            } catch (IOException ioe) {
                System.err.println("Could not initiate FileServer");
                System.err.println(ioe.getMessage());
                stop = true;
            }

        }

    }
}