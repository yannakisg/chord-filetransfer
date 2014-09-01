package Application;

import DHash.IdKey;
import DHash.FileNameKey;
import Exceptions.AlreadyConnectedException;
import Exceptions.IDNotFoundException;
import java.util.Map;
import java.rmi.*;
import java.util.Set;
import java.util.List;
import DHash.Key;
import FileServices.Share;
import Utilities.MainFrame;
import java.rmi.registry.Registry;

/**
 * @author ChordFPG team
 * @version  1.0
 */
public interface Node extends Remote {public void clearEntries() throws RemoteException;public Share getShare() throws RemoteException;

    public void create() throws AlreadyConnectedException, IDNotFoundException, RemoteException;

    public void notifyNode(Node possiblePredecessor) throws RemoteException;
   
    public void join(Node connectedNode) throws RemoteException;

    public Node find_successor(Key id) throws RemoteException;

    public IdKey find_successor_ID(Key id) throws RemoteException;

    public void disconnect() throws RemoteException;

    /**
     * Returns the id of this node
     * @return The id of this node
     */
    public IdKey getLocalID() throws RemoteException;

    public Registry getRMIHandle() throws RemoteException;

    public boolean isConnected() throws RemoteException;

    /**
     * Replaces the successor at the specified position with the specified successor
     * @param key - Successor to be stored at the specified position
     * @param index - Index of the successor to be replaced
     */
    public void addSuccessor(IdKey id, int index) throws RemoteException;

    public void addEntries(IdKey idKey, Set<FIDEntry> entries) throws RemoteException;

    public boolean isAlive() throws RemoteException;

    /**
     * Returns the predecessor of this node
     * @return The predecessor of this node
     */
    public IdKey getPredecessor() throws RemoteException;

    /**
     * Returns the immediate successor of this node
     * @return The immediate successor of this node
     */
    public IdKey getImmediateSuccessor() throws RemoteException;


    public void setPredecessor(IdKey idKey) throws RemoteException;

    /**
     * Returns the IdKeys of the Finger List in an array
     * @return The IdKeys of the Finger List
     */
    public IdKey[] getFingers() throws RemoteException;


    /**
     * Returns the finger at the specified position in the Finger List
     * @param index - The index of the finger to be returned
     * @return The finger at the specified position in the Finger List
     */
    public IdKey getFinger(int index) throws RemoteException;

    /**
     * Replaces the finger at the specified position with the specified finger
     * @param key - Finger to be stored at the specified position
     * @param index - Index of the Finger to be replaced
     */
    public void setFinger(IdKey key, int index) throws RemoteException;

    public Map<IdKey, Set<FIDEntry>> giveEntries(IdKey predecessorID) throws RemoteException;

    public void setEntries(Map<IdKey, Set<FIDEntry>> mEntries) throws RemoteException;

    /**
     * Removes the successor at the  specified position in Successor List
     * @param index - The index of the successor to be removed
     */
    public void removeSuccessor(int index) throws RemoteException;

    /**
     * Checks the integrity of successor list
     */
    public void checkIntegrityOfSucList() throws RemoteException;

    /**
     * Returns the successor at the specified position in the Successor List
     * @param index - The index of the successor to be returned
     * @return The successor at the specified position in the Successor List
     */
    public IdKey getSucList(int index) throws RemoteException;

    /**
     * Returns the successor list
     * @return The successor list
     */
    public List<IdKey> getWholeSucList() throws RemoteException;

    public Map<IdKey, Set<FIDEntry>> getAllEntries() throws RemoteException;

    public void setConnected(boolean connected) throws RemoteException;

    /**
     * Returns the size of finger list
     * @return The size of finger list
     */
    public int getFingerSize() throws RemoteException;

    /**
     * Returns the size of successor list
     * @return The size of successor list
     */
    public int getSucListSize() throws RemoteException;

    public void addEntry(IdKey idKey, FIDEntry entry) throws RemoteException;

    /**
     * Removes the FingerEntry at the  specified position in FingerList
     * @param index - The index of the FingerEntry to be removed
     */
    public void removeFinger(int index) throws RemoteException;

    /**
     * Returns the FingerList , which is consisted of objects of FingerEntry
     * @return The FingerList
     */
    public List<FingerEntry> getFingerEntries() throws RemoteException;

    public boolean existsInEntries(FIDEntry fid, IdKey id) throws RemoteException;

    public void removeEntrySet(IdKey id) throws RemoteException;

    public FIDEntry getEntry(IdKey owner, FileNameKey fkey) throws RemoteException;

    public FIDEntry getEntry(FileNameKey fkey) throws RemoteException;

    /**
     * Clears the successor list
     */
    public void clearSuccessorList() throws RemoteException;

    /**
     * Returns the IdKeys of the Finger List in a List
     * @return The IdKeys of the Finger List
     */
    public List<IdKey> getFingerKeys() throws RemoteException;

    /**
     * Returns the whole log (INFO,TABLES,ERRORS,WARNINGS) in an array of strings
     */
    public String[] getWholeLog() throws RemoteException;

    /**
     * Clears the finger list
     */
    public void clearFingers() throws RemoteException;

    /**
     * Returns the number of remote files
     * @return The number of remote files
     */
    public int getNumberOfEntries() throws RemoteException;

    /**
     * Returns the number of local files
     * @return The number of local files
     */
    public int getNumberOfFiles() throws RemoteException;

    public NodeProperties getProperties() throws RemoteException;

    /**
     * Returns an array of the statistics of the node
     * @return The array of the statistics of the node
     */
    public double[] getNodeStatistics() throws RemoteException;

    /**
     * Returns an array of the statistics of the ring
     * @return The array of the statistics of the ring
     */
    public double[] getRingStatistics() throws RemoteException;

    public long getTime() throws RemoteException;

    public int getHops() throws RemoteException;

    public int getNumSearches() throws RemoteException;

    public void addTime(long time) throws RemoteException;

    public void addHops(int hops) throws RemoteException;

    /**
     * Returns an array of local filenames
     * @return The array of local filenames
     */
    public String[] getFileNames() throws RemoteException;

    /**
     * Returns an array of remote filenames
     * @return The array of remote filenames
     */
    public String[] entriesToStringArray() throws RemoteException;

    public MainFrame getMainframe() throws RemoteException;

    public void setMainFrame(MainFrame frame) throws RemoteException;
}