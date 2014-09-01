package DHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-1 Hash Implementation
 * @author ChordFPG Team
 * @version 1.0
 */
public final class Hashing {

    private static final String DEFAULT_METHOD = "SHA-1";
    private static String hashMethod;
    private static MessageDigest md;

    /**
     * Returns BigInt representation of a hashed byte array
     * @param key - A byte number to be hashed
     * @return BigInt representation of a hashed byte array
     */
    public static BigInt hash(byte[] key) {

        // SHA - 1 implementation
        if (hashMethod == null) {
            hashMethod = DEFAULT_METHOD;
        }

        try {
            md = MessageDigest.getInstance(hashMethod);
        } catch (NoSuchAlgorithmException ex) {  }

        md.reset();
        md.update(key);

        return new BigIntImpl(md.digest());

    }
}
