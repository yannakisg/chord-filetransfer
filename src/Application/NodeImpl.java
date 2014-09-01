package Application;

import Chord.Chord;
import DHash.BigIntImpl;
import DHash.FileNameKey;
import DHash.IdKey;
import java.net.MalformedURLException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import Chord.CheckPredecessor;
import Chord.CheckSuccessorList;
import Chord.FixFingers;
import Chord.Stabilize;
import DHash.Key;
import Exceptions.AlreadyConnectedException;
import Exceptions.IDNotFoundException;
import Utilities.Log;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import FileServices.CheckEntries;
import FileServices.Share;
import Utilities.MainFrame;

/**
 * @author ChordFPG team
 * @version  1.0
 */
public class NodeImpl extends UnicastRemoteObject implements Node {

    private final IdKey localID;
    private final Map<IdKey, Set<FIDEntry>> mEntries;
    private final List<IdKey> sucList;
    private IdKey predecessor;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> fixFingers;
    private ScheduledFuture<?> checkPredecessor;
    private ScheduledFuture<?> stabilize;
    private ScheduledFuture<?> checkSuccessorList;
    private ScheduledFuture<?> CheckEntries;
    private List<FingerEntry> fingerList;
    private Share share;
    private NodeProperties properties;
    private int hops = 0;
    private long time = 0;
    private int numSearches = 0;
    private MainFrame mainFrame;
    /*
     * local variable that retains the condition of the node,
     * as far as chord ring is concerned
     */
    private boolean connected = false;

    public NodeImpl(IdKey id) throws RemoteException {
        fingerList = new ArrayList<FingerEntry>(32);

        localID = id;

        mEntries = new HashMap<IdKey, Set<FIDEntry>>();

        predecessor = null;

        sucList = new ArrayList<IdKey>();

    }

    public void setMainFrame(MainFrame frame) {
        this.mainFrame = frame;
    }

    public MainFrame getMainframe() {
        return mainFrame;
    }

    /**
     * Returns the whole log (INFO,TABLES,ERRORS,WARNINGS) in an array of strings
     */
    public String[] getWholeLog() {
        return MainFrame.getWholeLog();
    }

    /**
     * Returns the successor list
     * @return The successor list
     */
    public List<IdKey> getWholeSucList() {
        return this.sucList;
    }

    /**
     * Returns the size of successor list
     * @return The size of successor list
     */
    public int getSucListSize() {
        return sucList.size();
    }

    /**
     * Replaces the finger at the specified position with the specified finger
     * @param key - Finger to be stored at the specified position
     * @param index - Index of the Finger to be replaced
     */
    public void setFinger(IdKey key, int index) {
        if (index > 159) {
            return;
        }

        if (key.equals(localID)) {
            return;
        }

        if (fingerList.size() == 0) {
            System.out.println("Setting NEW FINGERENTRY");
            fingerList.add(0, new FingerEntry(key, 0, 159));
            return;
        }

        Iterator<FingerEntry> iter = fingerList.iterator();
        FingerEntry temp;


        while (iter.hasNext()) {
            temp = iter.next();

            if (temp.contains(key)) {
                return;
            }
        }

        Log.addMessage("NOT FOUND" + index + "=>" + key.hashKeytoHexString(), Log.ERROR);

        System.out.println("_----------------------------NOT FOUND-------------------" + index);
        if (index != 0) {
            int i;
            FingerEntry entry = null;

            for (i = 0; i < fingerList.size(); i++) {
                entry = fingerList.get(i);
                if (entry.getFirstElement() <= index && entry.getLastElement() >= index) {
                    break;
                }
            }
            i++;
            for (; i < fingerList.size(); i++) {
                fingerList.remove(i);
            }

            if (entry == null) {
                System.out.println("-----------------ERROR------------------------");
                return;
            }

            entry.setLastElement(index - 1);

            fingerList.add(fingerList.size(), new FingerEntry(key, index, 159));
        } else {
            for (int i = 0; i < fingerList.size(); i++) {
                fingerList.remove(i);
            }

            fingerList.add(0, new FingerEntry(key, 0, 159));
        }


        System.out.println("---------BEFORE CALLING SORT------------");
        for (int i = 0; i < fingerList.size(); i++) {
            System.out.println(fingerList.get(i).getKey().hashKeytoHexString() + " => " + fingerList.get(i).getLastElement());
        }
        System.out.println("---------AFTER CALLING SORT--------------");
        if (fingerList.size() > 1) {
            Collections.sort(fingerList);
        }
        for (int i = 0; i < fingerList.size(); i++) {
            System.out.println(fingerList.get(i).getKey().hashKeytoHexString() + " => " + fingerList.get(i).getLastElement());
        }


    }

    /**
     * Clears the finger list
     */
    public void clearFingers() {
        for (int i = 0; i < fingerList.size(); i++) {
            fingerList.remove(i);
        }
    }

     /**
     * Removes the FingerEntry at the  specified position in FingerList
     * @param index - The index of the FingerEntry to be removed
     */
    public void removeFinger(int index) {
        fingerList.remove(index);
    }

    /**
     * Returns the IdKeys of the Finger List in an array
     * @return The IdKeys of the Finger List
     */
    public IdKey[] getFingers() {
        if (fingerList.size() == 0) {
            return null;
        }
        Object[] fEntries = fingerList.toArray();
        IdKey[] arrayKeys = new IdKey[fEntries.length];
        for (int i = 0; i < arrayKeys.length; i++) {
            arrayKeys[i] = ((FingerEntry) fEntries[i]).getKey();
        }
        return arrayKeys;
    }

     /**
     * Returns the size of finger list
     * @return The size of finger list
     */
    public int getFingerSize() {
        return fingerList.size();
    }

    /**
     * Returns the finger at the specified position in the Finger List
     * @param index - The index of the finger to be returned
     * @return The finger at the specified position in the Finger List
     */
    public IdKey getFinger(int index) {
        return fingerList.get(index).getKey();
    }

    /**
     * Returns the FingerList , which is consisted of objects of FingerEntry
     * @return The FingerList
     */
    public List<FingerEntry> getFingerEntries() {
        return fingerList;
    }

    /**
     * Returns the IdKeys of the Finger List in a List
     * @return The IdKeys of the Finger List
     */
    public List<IdKey> getFingerKeys() {
        List<IdKey> list = new ArrayList<IdKey>();
        for (int i = 0; i < fingerList.size(); i++) {
            list.add(i, fingerList.get(i).getKey());
        }
        return list;

    }

    /**
     * adds the FIDEntry to the node's Map
     * if the idKey has other entries, then appends on its set
     * else, creates a new set for the idKey
     */
    public void addEntry(IdKey idKey, FIDEntry entry) {
        Set<FIDEntry> nSet;
        synchronized (this.mEntries) {
            boolean contains = false;
            Iterator<Map.Entry<IdKey, Set<FIDEntry>>> it = mEntries.entrySet().iterator();
            Map.Entry<IdKey, Set<FIDEntry>> itEntry = null;

            //iterate through the entire Map
            while (it.hasNext()) {
                itEntry = it.next();

                //check if any of the ids already in the map equal with idKey
                if (itEntry.getKey().equals(idKey)) {
                    contains = true;
                    break;
                }
            }

            //if the id already existed in the map, then add entry to its set
            if (contains) {
                nSet = itEntry.getValue();
                nSet.add(entry);
            } //otherwise, create a new set for the id and add the entry in it
            else {
                nSet = new TreeSet<FIDEntry>();
                nSet.add(entry);
                mEntries.put(idKey, nSet);
            }
        }
    }

    public void setPredecessor(IdKey idKey) {
        this.predecessor = idKey;
    }

    public void setProperties(NodeProperties properties) {
        this.properties = properties;
    }

    /**
     * Returns the predecessor of this node
     * @return The predecessor of this node
     */
    public IdKey getPredecessor() {
        return this.predecessor;
    }

    public NodeProperties getProperties() {
        return properties;
    }

    /**
     * Replaces the successor at the specified position with the specified successor
     * @param key - Successor to be stored at the specified position
     * @param index - Index of the successor to be replaced
     */
    public void addSuccessor(IdKey idKey, int index) {
        synchronized (this.sucList) {

            for (int i = 0; i < sucList.size(); i++) {
                if (idKey.equals(sucList.get(i))) {
                    return;
                }
            }

            sucList.add(index, idKey);

            this.checkIntegrityOfSucList();
        }
    }

    /**
     * Removes the successor at the  specified position in Successor List
     * @param index - The index of the successor to be removed
     */
    public void removeSuccessor(int index) {
        synchronized (this.sucList) {
            if (index < sucList.size() && index >= 0) {
                sucList.remove(index);
            }
        }
    }

    /**
     * @param idKey - IdKey instance
     * @return true if node contains the idKey
     */
    public boolean containsSuccessor(IdKey idKey) {
        return sucList.contains(idKey);
    }

    /**
     * Returns the id of this node
     * @return The id of this node
     */
    public IdKey getLocalID() {
        return new IdKey(new BigIntImpl(this.localID.getByteKey()), this.localID.getPID(), this.localID.getIP());
    }

    public Map<IdKey, Set<FIDEntry>> getAllEntries() {
        return this.mEntries;
    }
    
    /**
     * Returns the immediate successor of this node
     * @return The immediate successor of this node
     */
    public IdKey getImmediateSuccessor() {
        return sucList.get(0);
    }

    /*
     * adds entries to the node, at the idKey position
     * no need to check if it exists, since this will be called only by
     * redistribute keys
     */
    public void addEntries(IdKey idKey, Set<FIDEntry> entries) {
        Set<FIDEntry> nSet;
        synchronized (this.mEntries) {
            nSet = entries;
            mEntries.put(idKey, nSet);
        }
    }

    /**
     * set the value of connected
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * @return  returs the value of connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * creates a new chord ring, with node as the sole node for present
     * creates the threads that run periodically
     */
    public void create() throws AlreadyConnectedException, IDNotFoundException {

        Chord.create(this);
        createThreads();
    }

    public void notifyNode(Node possiblePredecessor) throws RemoteException {
        Chord.notifyNode(this, possiblePredecessor);
    }

    public void join(Node connectedNode) throws RemoteException, NullPointerException {
        System.out.println("node join");
        try {
            Chord.join(this, connectedNode);
        } catch (MalformedURLException ex) {
            Logger.getLogger(NodeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        createThreads();

    }

    public Node find_successor(Key id) throws RemoteException {
        System.out.println("node find_successor");
        try {
            return Chord.find_successor(this, id);
        } catch (MalformedURLException ex) {
            Logger.getLogger(NodeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void disconnect() throws RemoteException {
        Chord.disconnect(this);
    }

    public void createThreads() {
        scheduler = Executors.newScheduledThreadPool(3);


        fixFingers = scheduler.scheduleAtFixedRate(new FixFingers(this), 2, 1, TimeUnit.SECONDS);
        checkPredecessor = scheduler.scheduleAtFixedRate(new CheckPredecessor(this), 35, 5, TimeUnit.SECONDS);
        stabilize = scheduler.scheduleAtFixedRate(new Stabilize(this), 3, 2, TimeUnit.SECONDS);
        checkSuccessorList = scheduler.scheduleAtFixedRate(new CheckSuccessorList(this), 5, 10, TimeUnit.SECONDS);

    }

    /**
     * used in order to find out if node is alive
     */
    public boolean isAlive() {
        return true;
    }

    public IdKey find_successor_ID(Key id) {
        try {
            IdKey smth = null;
            try {
                smth = Chord.find_successor(this, id).getLocalID();
            } catch (MalformedURLException ex) {
                Log.addMessage("MalformedURLException", Log.ERROR);
            }
            return smth;
        } // Extremely Dangerous
        catch (ConnectException ce) {
            Log.addMessage("Successor is dead. Continue", Log.ERROR);
        } catch (RemoteException ex) {
            Log.addMessage("Successor is dead. Continue", Log.ERROR);
        }

        return null;
    }

    public java.rmi.registry.Registry getRMIHandle() {
        return NodeClient.rmiReg;
    }

    /**
     * Returns the successor at the specified position in the Successor List
     * @param index - The index of the successor to be returned
     * @return The successor at the specified position in the Successor List
     */
    public IdKey getSucList(int i) {
        return this.sucList.get(i);
    }

    /**
     * Returns the FIDEntry for the filename given, that belongs to the owner
     * @param owner of the file
     * @param filename to be searched
     * @return
     */
    public FIDEntry getEntry(IdKey owner, FileNameKey fkey) {
        Set<FIDEntry> traverseSet = mEntries.get(owner);

        Iterator<FIDEntry> it = traverseSet.iterator();

        while (it.hasNext()) {
            FIDEntry temp = it.next();
            if (temp.getfKey().equals(fkey)) {
                return temp;
            }
        }
        return null;
    }

    public FIDEntry getEntry(FileNameKey fkey) {
        /*Iterate over the map to find the specific entry*/
        for (Map.Entry<IdKey, Set<FIDEntry>> entry : mEntries.entrySet()) {
            FIDEntry found = getEntry(entry.getKey(), fkey);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * returns a map that contains all entries that had idkey less or equal to the
     * parameter id
     */
    public Map<IdKey, Set<FIDEntry>> giveEntries(IdKey newSuccessorID) {

        synchronized (this.mEntries) {
            Map<IdKey, Set<FIDEntry>> temporaryMap = new HashMap<IdKey, Set<FIDEntry>>();
            FIDEntry entry = null;
            Map.Entry<IdKey, Set<FIDEntry>> itEntry;
            Iterator<FIDEntry> iter;
            Set<FIDEntry> set;
            Iterator<Map.Entry<IdKey, Set<FIDEntry>>> it = mEntries.entrySet().iterator();

            //iterate through the entire Map
            while (it.hasNext()) {
                //initialize a mpa for every IdKey
                set = new HashSet<FIDEntry>();
                itEntry = it.next();
                iter = itEntry.getValue().iterator();

                //iterate through the entire set of the IdKey
                while (iter.hasNext()) {
                    entry = iter.next();

                    //if id is not in (newSuccessor, successor], then it should be moved to successor
                    //(it belongs to (predecessor, newSuccessor] part of the entries)
                    if (!Key.isBetweenSuccessor(entry.getfKey(), newSuccessorID, this.localID))/*(itEntry.getKey().getHashKey().compareTo(predecessorID.getHashKey()) <= 0)*/ {
                        //add to the set the FIDEntry that corresponds to the above
                        set.add(entry);
                        Log.addMessage("moved from" + this.localID.hashKeytoHexString() + " to " + newSuccessorID.hashKeytoHexString(), Log.ERROR);
                    }
                }

                //add the set of the entries to the returning map
                if (!set.isEmpty()) {
                    temporaryMap.put(itEntry.getKey(), set);
                }

                //delete the entries of the set, from the node's Set
                itEntry.getValue().removeAll(set);
            }
            return temporaryMap;
        }
    }

    public void setEntries(Map<IdKey, Set<FIDEntry>> newEntries) {
        synchronized (this.mEntries) {
            Iterator<Map.Entry<IdKey, Set<FIDEntry>>> it = newEntries.entrySet().iterator();
            Map.Entry<IdKey, Set<FIDEntry>> itEntry;
            Iterator<FIDEntry> iter;
            FIDEntry entry;

            //iterate the entire map
            while (it.hasNext()) {
                itEntry = it.next();
                iter = itEntry.getValue().iterator();

                //for every id key, iterate in its set of entries
                while (iter.hasNext()) {
                    entry = iter.next();
                    //add the entries to the node's map
                    this.addEntry(itEntry.getKey(), entry);
                }
            }
        }
    }

    /**
     * Checks the integrity of successor list
     */
    public void checkIntegrityOfSucList() {

        if (this.sucList.size() > 3) {
            for (int i = sucList.size() - 1; i > 2; i--) {
                sucList.remove(i);
            }
        }
    }

    public List<IdKey> printSuccessorsList() {
        List<IdKey> temp = sucList;
        return temp;
    }

    public int entriesSize() {
        Iterator<Map.Entry<IdKey, Set<FIDEntry>>> it = mEntries.entrySet().iterator();
        Map.Entry<IdKey, Set<FIDEntry>> itEntry;

        int i = 0;

        while (it.hasNext()) {
            itEntry = it.next();

            Iterator<FIDEntry> iter = itEntry.getValue().iterator();
            while (iter.hasNext()) {
                i++;
                iter.next();
            }
        }

        return i;
    }

    /**
     * removes the file Entries that have as key, the id
     */
    public void removeEntrySet(IdKey id) {
        Set<FIDEntry> nSet;
        synchronized (this.mEntries) {
            Iterator<Map.Entry<IdKey, Set<FIDEntry>>> it = mEntries.entrySet().iterator();
            Map.Entry<IdKey, Set<FIDEntry>> itEntry = null;

            //iterate through the entire Map
            while (it.hasNext()) {
                itEntry = it.next();

                //check if any of the ids already in the map equal with idKey
                //if anyone does, then remove it and stop the repetition
                if (itEntry.getKey().equals(id)) {
                    this.mEntries.remove(itEntry.getKey());
                    break;
                }
            }
        }
    }

    /**
     *
     * checks if the fid is already present in the mEntries of the Node, for
     * the node with IdKey id.
     */
    public boolean existsInEntries(FIDEntry fid, IdKey id) {
        Set<FIDEntry> nSet;
        synchronized (this.mEntries) {
            boolean contains = false;
            Iterator<Map.Entry<IdKey, Set<FIDEntry>>> it = mEntries.entrySet().iterator();
            Map.Entry<IdKey, Set<FIDEntry>> itEntry = null;
            FIDEntry entry = null;

            //iterate through the entire Map
            while (it.hasNext()) {
                itEntry = it.next();

                //check if any of the ids already in the map equal with id
                if (itEntry.getKey().equals(id)) {
                    contains = true;
                    break;
                }
            }

            //if the id existed in the map check if there was also the filename present
            if (contains) {
                Iterator<FIDEntry> iter = itEntry.getValue().iterator();
                while (iter.hasNext()) {
                    entry = iter.next();

                    //if it does, return true, else return false
                    if (fid.equals(entry.getfKey())) {
                        return true;
                    }
                }
            } else {
                return false;
            }

            return false;
        }
    }

    public void setShare(Share share) {
        this.share = share;
        this.createCheckEntriesThread();
    }

    public void createCheckEntriesThread() {
        CheckEntries = scheduler.scheduleAtFixedRate(new CheckEntries(this, this.share), 15, 15, TimeUnit.SECONDS);
    }

    /**
     * Clears the successor list
     */
    public void clearSuccessorList() throws RemoteException {
        for (int i = 0; i < sucList.size(); i++) {
            sucList.remove(i);
        }
    }

    /**
     * Returns the number of remote files
     * @return The number of remote files
     */
    public int getNumberOfEntries() {
        int numberOfFiles = 0;
        Iterator<Map.Entry<IdKey, Set<FIDEntry>>> it = mEntries.entrySet().iterator();
        Map.Entry<IdKey, Set<FIDEntry>> entry;


        while (it.hasNext()) {
            entry = it.next();
            numberOfFiles += entry.getValue().size();
        }

        return numberOfFiles;
    }

    /**
     * Returns the number of local files
     * @return The number of local files
     */
    public int getNumberOfFiles() {
        return this.share.getFilenames().length;
    }

    /**
     * Returns an array of local filenames
     * @return The array of local filenames
     */
    public String[] getFileNames() {
        return this.share.getFilenames();

    }

    /**
     * Returns an array of remote filenames
     * @return The array of remote filenames
     */
    public String[] entriesToStringArray() {

        int size = getNumberOfEntries();

        String[] files = new String[size];
        String text = "";
        int index = 0;

        Iterator<Map.Entry<IdKey, Set<FIDEntry>>> iterMap = mEntries.entrySet().iterator();
        Iterator<FIDEntry> iterSet;
        Map.Entry<IdKey, Set<FIDEntry>> entry;
        FIDEntry fidEntry;

        while (iterMap.hasNext()) {
            entry = iterMap.next();
            if (!entry.getValue().isEmpty()) {
                text = "<li><font color=\"red\">" + entry.getKey().toString() + "</font></li><ul>";
                iterSet = entry.getValue().iterator();

                while (iterSet.hasNext()) {
                    fidEntry = iterSet.next();
                    text += "<li><font color=\"blue\">" + fidEntry.getHashKey().toHexString() + "</font></li>";
                }
                text += "</ul>";
                files[index++] = text;
            }
        }

        return files;
    }

    /**
     * Returns an array of the statistics of the node
     * @return The array of the statistics of the node
     */
    public double[] getNodeStatistics() {
        double[] array = new double[5];

        array[0] = this.getNumberOfFiles();
        array[1] = this.getNumberOfEntries();
        array[2] = (double) time / numSearches;
        array[3] = (double) hops / numSearches;
        array[4] = numSearches;

        Log.addMessage("Number of Files in Node: " + array[0], Log.TABLES);
        Log.addMessage("Number of entries in Node: " + array[1], Log.TABLES);
        Log.addMessage("Average Time for searches: " + array[2], Log.TABLES);
        Log.addMessage("Average Hops for searches: " + array[3], Log.TABLES);
        Log.addMessage("Number of searches: " + numSearches, Log.TABLES);

        return array;
    }

    /**
     * Returns an array of the statistics of the ring
     * @return The array of the statistics of the ring
     */
    public double[] getRingStatistics() {
        IdKey nodeID = this.getImmediateSuccessor();
        Node tempNode = null;

        double[] array = new double[5];
        long totalTime = time;
        int totalHops = hops;
        int totalNumSearches = numSearches;
        int totalNodes = 1;
        int totalFiles = 0;

        while (!nodeID.equals(this.localID)) {
            totalNodes++;
            try {
                tempNode = (Node) Naming.lookup("/" + nodeID.getIP() + ":1099/" + String.valueOf(nodeID.getPID()));
            } catch (NotBoundException ex) {
                System.out.println(nodeID.hashKeytoHexString() + " is not bound or was unbound. Spotted in getRingStatitics");
                Log.addMessage(nodeID.hashKeytoHexString() + " is not bound or was unbound. Spotted in getRingStatitics", Log.ERROR);
            } catch (MalformedURLException ex) {
                Log.addMessage("Malformed URL in getRingStatistics, while looking up for " + nodeID.hashKeytoHexString(), Log.ERROR);
            } catch (RemoteException ex) {
                System.out.println("A problem occurred while looking up for " + nodeID + " in getRingStatistics");
                Log.addMessage("A problem occurred while looking up for " + nodeID + " in getRingStatistics", Log.ERROR);
            }
            try {
                totalHops += tempNode.getHops();
                totalTime += tempNode.getTime();
                totalNumSearches += tempNode.getNumSearches();
                totalFiles += tempNode.getNumberOfFiles();
            } catch (RemoteException ex) {
                Log.addMessage("A problem occurred in getRingStatistics while searching suming hops/time/searches", Log.ERROR);
            }
            try {
                nodeID = tempNode.getImmediateSuccessor();
            } catch (RemoteException ex) {
                Log.addMessage("A problem occurred while refreshing nodeID variable in getRingStatistics", Log.ERROR);
            }
        }

        array[0] = totalNodes;
        array[1] = totalFiles;
        array[2] = (double) totalTime / totalNumSearches;

        array[3] = (double) totalHops / totalNumSearches;
        array[4] = totalNumSearches;


        Log.addMessage("Total Number of Nodes: " + totalNodes, Log.TABLES);
        Log.addMessage("Total Number of Files: " + totalFiles, Log.TABLES);
        Log.addMessage("Average Total Time for searches: " + array[2], Log.TABLES);
        Log.addMessage("Average Total Hops for searches: " + array[3], Log.TABLES);
        Log.addMessage("Total Number of searches: " + totalNumSearches, Log.TABLES);

        return array;
    }

    public long getTime() throws RemoteException {
        return time;
    }

    public int getHops() throws RemoteException {
        return hops;
    }

    public int getNumSearches() throws RemoteException {
        return numSearches;
    }

    public void addTime(long time) {
        this.time += time;
        this.numSearches++;
    }

    public void addHops(int hops) {
        this.hops += hops;
    }

    public void clearEntries() {
        this.mEntries.clear();
    }

    public Share getShare() {
        return this.share;
    }
}