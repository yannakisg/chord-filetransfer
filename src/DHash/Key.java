package DHash;

/**
 * Interface of Keys
 * @author ChordFPG Team
 * @version 1.0
 */
public abstract class Key {

    /**
     * Abstract method which returns a byte representation of a hashed key
     * @return byte representation of a hashed key
     */
    public abstract byte[] getByteKey();

    /**
     * Abstract method which compares this Key to the specified key
     * @param k - A Key value to be compared
     * @return - true the given Key equivalent to this Key
     */
    public abstract boolean equals(Key k);

    /**
     * Abstract method which returns a BigInt representation of a hashed key
     * @return BigInt representation of a hashed key
     */
    public abstract BigInt getHashKey();

    /**
     * Abstract method which returns a string representation of  a hashed key
     * @return String representation of a hashed key
     */
    public abstract String hashKeytoHexString();

    /**
     * Checks if id ε (first , last] - appropriate for the find_successor method of Chord
     * @return true if id ε (first , last]
     */
    public static boolean isBetweenSuccessor(Key id, Key first, Key last) {
        BigInt idBig = id.getHashKey();
        BigInt firstBig = first.getHashKey();
        BigInt lastBig = last.getHashKey();

        if (firstBig.compareTo(lastBig) == 1) {
            if (idBig.compareTo(firstBig) == -1 && idBig.compareTo(lastBig) <= 0) {
                return true;
            }

            if (idBig.compareTo(firstBig) == 1) {
                return true;
            }
        }

        if (firstBig.compareTo(lastBig) == -1) {
            if (idBig.compareTo(firstBig) == 1 && idBig.compareTo(lastBig) <= 0) {
                return true;
            }
        }

        if (firstBig.compareTo(lastBig) == 0 && (firstBig.compareTo(idBig) > 0 || firstBig.compareTo(idBig) < 0)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if id ε (first , last)
     * @return true if id ε (first , last)
     */
    public static boolean isBetween(Key id, Key first, Key last) {
        BigInt idBig = id.getHashKey();
        BigInt firstBig = first.getHashKey();
        BigInt lastBig = last.getHashKey();

        if (firstBig.compareTo(lastBig) == -1) {
            if (idBig.compareTo(firstBig) == 1 && idBig.compareTo(lastBig) == -1) {
                return true;
            }
        }

        if (firstBig.compareTo(lastBig) == 1) {
            if (idBig.compareTo(firstBig) == -1 && idBig.compareTo(lastBig) == -1) {
                return true;
            }
            if (idBig.compareTo(firstBig) == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if id ε (first , last ) - appropriate for the notify method of Chord
     * @return true if id ε (first , last)
     */
    public static boolean isBetweenNotify(Key id, Key first, Key last) {
        BigInt idBig = id.getHashKey();
        BigInt firstBig = first.getHashKey();
        BigInt lastBig = last.getHashKey();

        if (firstBig.compareTo(lastBig) == -1) {
            if (idBig.compareTo(firstBig) == 1 && idBig.compareTo(lastBig) == -1) {
                return true;
            }
        }

        if (firstBig.compareTo(lastBig) == 1) {
            if (idBig.compareTo(firstBig) == -1 && idBig.compareTo(lastBig) == -1) {
                return true;
            }
            if (idBig.compareTo(firstBig) == 1) {
                return true;
            }
        }

        if (firstBig.compareTo(lastBig) == 0) {
            return true;
        }

        return false;
    }
}