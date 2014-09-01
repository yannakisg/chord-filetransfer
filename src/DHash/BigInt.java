package DHash;

/**
 * Interface of Unsigned Big Integers
 * @author ChordFPG Team
 * @version 1.0
 */

public interface BigInt {

    /**
     * Returns a BigInt whose value is equals to this + BigInt(secNumber)
     * @param secNumber - value to be added to this BigInt
     * @return a BigInt whose value is equals to this + BigInt(secNumber)
     */
    public BigInt addition(byte[] secNumber);

    /**
     * Returns a BigInt whose value is equals to 2 ^ exponent
     * @param k - The exponent
     * @return 2 ^ exponent
     */
    public BigInt powerOfTwo(int k);

    /**
     * Returns a BigInt whose value is equals to this + 2 ^ exponent
     * @param k - The exponent
     * @return this + 2 ^ exponent
     */
    public BigInt addpowerOfTwo(int k);

    /**
     * Retunrs a string representation of BigInt in hexademical
     * @return The string representation of BigInt in hexademical
     */
    public String toHexString();

    /**
     * @return byte representation of BigInt
     */
    public byte[] getBytes();

    /**
     * Compares this BigInt to the specified BigInt
     * @param b - A BigInt to be compared
     * @return true if the given BigInt equivalent to this BigInt
     */
    public boolean equals(BigInt b);

    /**
     * Compares this byte representation of BigInt to the specified byte array
     * @param b - A byte array to be compared
     * @return true if the given byte array equivalent to the byte representation of this BigInt
     */
    public boolean equals(byte[] b);

    /**
     * Byte representation of this mod(m)
     * @param m - The modulus
     * @return this mod(m)
     */
    public byte[] modM(int m);

    /**
     * Returns a BigInt whose value is this - BigInt(secNumber)
     * @param secNumber - value to be subtracted from this BigInt
     * @return a BigInt whose value is this - BigInt(secNumber)
     */
    public BigInt subtraction(byte[] secNumber);

    /**
     * Compares this BigInt to the specified BigInt
     * @param big - A BigInt to which this BigInt is to be compared
     * @return 1 if this > big , -1 if this < big , 0 if this == big
     */
    public int compareTo(BigInt big);

    /**
     * Returns a BigInt whose value is this << shiftValue
     * @param shiftValue - shift distance in bits
     * @return this << shiftValue
     */

    public BigInt shiftLeft(int shiftValue);

    /**
     * Returns a BigInt whose value is this >> shiftValue
     * @param shiftValue - shift distance in bits
     * @return this >> shiftValue
     */

    public BigInt shiftRight(int shiftValue);

    /**
     *  Returns a string representation of BigInt in binary
     * @return The string representation of BigInt in binary
     */
    public String toBinaryString();

    /**
     * Transforms a representation of a string in binary into a BigInt
     * @param s - String representation of a number in binary
     * @return The equivalent BigInt of the given binary number
     */
    public BigInt binaryToBigInt(String s);

    /**
     *  Returns a string representation of BigInt in binary without leading zeros
     * @return  The string representation of BigInt in binary without leading zeros
     */

    public String toBinaryStringWithoutZeros();


    /**
     * Returns a string representation of BigInt in hexademical without leading zeros
     * @return The string representation of BigInt in hexademical without leading zeros
     */
    public String toHexStringWithoutZeros();

}