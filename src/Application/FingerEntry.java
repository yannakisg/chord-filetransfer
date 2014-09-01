package Application;

import DHash.IdKey;

/**
 * Representation of Fingers
 * @author ChordFPG team
 * @version  1.0
 */
public class FingerEntry implements Comparable<FingerEntry> {

    private IdKey idKey;
    private int lastElement;
    private int firstElement;

    public FingerEntry(IdKey idKey, int firstElement, int lastElement) {
        this.idKey = idKey;
        this.lastElement = lastElement;
        this.firstElement = firstElement;
    }

    /**
     * The IdKey representation of finger
     * @return IdKey representation of finger
     */
    public IdKey getKey() {
        return idKey;
    }

    public int getLastElement() {
        return lastElement;
    }

    public void setLastElement(int element) {
        lastElement = element;
    }

    public int getFirstElement() {
        return this.firstElement;
    }

    public void setFirstElement(int element) {
        firstElement = element;
    }

    /**
     * Checks if this IdKey is equals to the specified
     * @param idKey - IdKey value to be compared
     * @return true if idKey equals to this idKey
     */
    public boolean contains(IdKey idKey) {
        return this.idKey.equals(idKey);
    }

    /**
     * Compares this FingerEntry to the specified
     * @param o - FingerEntry to be compared
     * @return 1 if this.lastElement > o.lastElement , -1 if this.lastElement < o.lastElement , the return value
     * between the comparisson of this.idKey and o.idKey
     */
    public int compareTo(FingerEntry o) {
        FingerEntry obj = (FingerEntry) o;

        if (lastElement > obj.getLastElement()) {
            return 1;
        } else if (lastElement < obj.getLastElement()) {
            return -1;
        } else {
            return this.getKey().getHashKey().compareTo(o.getKey().getHashKey());
        }
    }
}