package DHash;

import java.io.Serializable;

/**
 * Key representation of FileNames
 * @author ChordFPG Team
 * @version 1.0
 */
public class FileNameKey extends Key implements Serializable {

    private final String fileName;
    private final BigInt hashKey;
    private final String hashKeyHexString;

    public FileNameKey(String fileName) {
        this.hashKey = Hashing.hash(fileName.getBytes());
        this.fileName = fileName;
        this.hashKeyHexString = this.hashKey.toHexString();
    }

    @Override
    /**
     * method which returns a byte representation of a hashed key
     * @return byte representation of a hashed key
     */
    public byte[] getByteKey() {
        return hashKey.getBytes();
    }

    /**
     * Returns the BigInt  representation of hashed FileNameKey
     * @return BigInt representation of hashed FileNameKey
     */
    public BigInt getBigInt() {
        return this.hashKey;
    }

    /**
     * Returns the string representation of filename
     * @return String representation of filename
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    /**
     * method which compares this Key to the specified key
     * @param k - A Key value to be compared
     * @return - true the given Key equivalent to this Key
     */
    public boolean equals(Key k) {
        return hashKey.equals(k.getByteKey());
    }

    /**
     * method which returns a string representation of  a hashed key
     * @return String representation of a hashed key
     */
    public String hashKeytoHexString() {
        return this.hashKeyHexString;
    }

    /**
     * method which returns a BigInt representation of a hashed key
     * @return BigInt representation of a hashed key
     */
    public BigInt getHashKey() {
        return this.hashKey;
    }
}