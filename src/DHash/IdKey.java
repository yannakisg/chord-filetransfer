package DHash;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Key representation of nodes
 * @author ChordFPG Team
 * @version 1.0
 */
public class IdKey extends Key implements Serializable
{

    private final BigInt hashKey;
    private final int pid;
    private final String ip;
    private String hashKeytoString;
    private String[] macInter = { "" , "" };

    public IdKey(int pid, String ip)
    {
        this(Hashing.hash((ip +"|"+pid).getBytes()), pid, ip);
    }

    public IdKey(BigInt hashKey, int pid, String ip)
    {  
        this.pid = pid;
        this.ip = ip;
        this.hashKey = hashKey;
        this.hashKeytoString = hashKey.toHexString();
        setMacAndInterface();
    }

    /**
     * Returns the process ID
     * @return pid
     */
    public int getPID()
    {
        return pid;
    }

    /**
     * Returns the ip of this node
     * @return ip
     */
    public String getIP()
    {
        return ip;
    }


    @Override
    /**
     * method which returns a byte representation of a hashed key
     * @return byte representation of a hashed key
     */
    public byte[] getByteKey()
    {
        return hashKey.getBytes();
    }

    @Override
    /**
     * method which compares this Key to the specified key
     * @param k - A Key value to be compared
     * @return - true the given Key equivalent to this Key
     */
    public boolean equals(Key k)
    {
        return hashKey.equals(k.getByteKey());
    }

     /**
     * method which returns a BigInt representation of a hashed key
     * @return BigInt representation of a hashed key
     */
    public BigInt getHashKey()
    {
        return this.hashKey;
    }

    @Override
    /**
     * Returns the string representation of this IdKey
     * @return String representation of this IdKey
     */
    public String toString()
    {
        return getIP() + "|" + getPID();
    }

     /**
     * method which returns a string representation of  a hashed key
     * @return String representation of a hashed key
     */
    public String hashKeytoHexString() {
        return this.hashKeytoString;
    }



    /**
     * Void method which finds the MAC address and network interface of a computer
     */
    private void setMacAndInterface() {
        try {
            String ipA = getIP().substring(1);
            InetAddress inet = InetAddress.getByName(ipA);
            NetworkInterface nInterface = NetworkInterface.getByInetAddress(inet);

            if (nInterface != null) {
                byte[] macAddress = nInterface.getHardwareAddress();
                macInter[1] = nInterface.getDisplayName();

                if (macAddress != null) {
                    for (byte b : macAddress) {
                        String temp = Integer.toHexString(b & 0xFF).toUpperCase();

                        if (temp.length() == 1) {
                            temp = "0" + temp;
                        }
                        macInter[0] += temp +":";
                    }
                    macInter[0] = macInter[0].substring(0, macInter[0].length() - 1);
                }
                else {
                    macInter[0] = "Unknown";
                }

            }
            else {
               macInter[0] = "Unknown";
               macInter[1] = "Unknown";
            }

        } catch (java.net.UnknownHostException ex) {
            macInter[0] = "Unknown";
            macInter[1] = "Unknown";
        }
        catch (SocketException ex) {
            macInter[0] = "Unknown";
            macInter[1] = "Unknown";
        }

    }

    /**
     * Returns the string representation of MAC address and network interface
     * @return String representation of MAC address and network interface
     */
    public String[] getMacAndInterface() {

        return macInter;
    }
    
}