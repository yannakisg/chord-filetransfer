package DHash;

import java.io.Serializable;

/**
 * Unsigned implementation of Big Integers
 * @author ChordFPG Team
 * @version 1.0
 */
public class BigIntImpl implements BigInt, Serializable {

    private final byte[] number;

    public BigIntImpl(byte[] number) {
        this.number = number;
    }

    /**
     * Returns a BigInt whose value is equals to this + BigInt(secNumber)
     * @param secNumber - value to be added to this BigInt
     * @return a BigInt whose value is equals to this + BigInt(secNumber)
     */
    public BigInt addition(byte[] secNumber) {

        int length = (this.number.length > secNumber.length) ? this.number.length : secNumber.length;
        int low = (this.number.length < secNumber.length) ? this.number.length : secNumber.length;
        byte[] temp = new byte[length];
        byte[] result = new byte[length];
        int i;
        int sum;
        int carry = 0;

        System.arraycopy(secNumber, 0, temp, length - low, low);

        for (i = 0; i < length - low; i++) {
            temp[i] = 0;
        }

        for (i = 0; i < 20; i++) {

            if (temp[i] == 0) {
                sum = (int) number[i] + (int) temp[i] + carry;
                result[i] = number[i];
            } else {
                sum = (int) number[i] + (int) temp[i] + carry;
                carry = sum >> 8;
                result[i] = (byte) (sum & 0xFF);
            }
        }

        return new BigIntImpl(result);
    }

    /**
     * Returns a BigInt whose value is equals to 2 ^ exponent
     * @param k - The exponent
     * @return 2 ^ exponent
     */
    public BigInt powerOfTwo(int k) {
        int i, byteSize;
        byte[] array = null;
        String s = "1", t;

        for (i = 0; i < k; i++) {
            s += "0";
        }

        while (s.length() % 8 != 0) {
            s = "0" + s;
        }

        byteSize = s.length() / 8;

        array = new byte[byteSize];

        for (i = 0; i < byteSize; i++) {
            t = s.substring(i * 8, i * 8 + 8);

            if (t.equals("10000000")) {
                array[i] = -128;
            } else {
                array[i] = Byte.parseByte(t, 2);
            }
        }

        return new BigIntImpl(array);
    }

    /**
     * Returns a BigInt whose value is this - BigInt(secNumber)
     * @param secNumber - value to be subtracted from this BigInt
     * @return a BigInt whose value is this - BigInt(secNumber)
     */
    public BigInt subtraction(byte[] secNumber) {
        int carry = 0;
        int locLen = this.number.length;
        int remLen = secNumber.length;
        byte[] temp;
        byte[] result = null;
        int i;
        int diff, totalSub;

        if (locLen > remLen) {

            temp = new byte[locLen];
            System.arraycopy(secNumber, 0, temp, locLen - remLen, remLen);

            for (i = 0; i < locLen - remLen; i++) {
                temp[i] = 0;
            }

            result = new byte[locLen];

            for (i = 0; i < locLen; i++) {
                totalSub = temp[i] + carry;
                if (this.number[i] < totalSub) {
                    diff = (this.number[i] + 0x100) - totalSub;
                    carry = 1;
                } else {
                    diff = this.number[i] - totalSub;
                    carry = 0;
                }

                result[i] = (byte) diff;
            }
        } else if (locLen < remLen) {

            temp = new byte[remLen];
            System.arraycopy(this.number, 0, temp, remLen - locLen, locLen);

            for (i = 0; i < remLen - locLen; i++) {
                temp[i] = 0;
            }

            result = new byte[remLen];

            for (i = 0; i < remLen; i++) {

                totalSub = secNumber[i] + carry;
                if (temp[i] < totalSub) {
                    diff = (temp[i] + 0x100) - totalSub;
                    carry = 1;
                } else {
                    diff = temp[i] - totalSub;
                    carry = 0;
                }
                result[i] = (byte) diff;
            }
        } else {
            result = new byte[remLen];

            for (i = 0; i < remLen; i++) {
                totalSub = secNumber[i] + carry;
                if (this.number[i] < totalSub) {
                    diff = (this.number[i] + 0x100) - totalSub;
                    carry = 1;
                } else {
                    diff = this.number[i] - totalSub;
                    carry = 0;
                }
                result[i] = (byte) diff;
            }

        }

        return new BigIntImpl(result);
    }

    /**
     * Byte representation of this mod(m)
     * @param m - The modulus
     * @return this mod(m)
     */
    public byte[] modM(int m) {
        BigInt bgM = this.powerOfTwo(m);
        BigInt bg;

        if (this.compareTo(bgM) >= 0) {
            bg = this.shiftRight(m);
            bg = bg.shiftLeft(m);
            bg = this.subtraction(bg.getBytes());

            byte[] array = bg.getBytes();
            byte[] newArray = new byte[20];

            System.arraycopy(array, array.length - 20, newArray, 0, newArray.length);

            return newArray;
        } else {
            return this.number;
        }

    }

    /**
     * Returns a BigInt whose value is equals to this + 2 ^ exponent
     * @param k - The exponent
     * @return this + 2 ^ exponent
     */
    public BigInt addpowerOfTwo(int k) {
        byte[] array = this.powerOfTwo(k).getBytes();

        return this.addition(array);
    }

    /**
     * Retunrs a string representation of BigInt in hexademical
     * @return The string representation of BigInt in hexademical
     */
    public String toHexString() {
        String hexString = "";
        String t;

        for (byte b : number) {
            t = Integer.toHexString(b & 0xff).toUpperCase();

            if (t.length() == 1) {
                t = "0" + t;
            }

            hexString += t;
        }

        return hexString;
    }

    /**
     *  Returns a string representation of BigInt in hexademical without leading zeros
     * @return  The string representation of BigInt in hexademical without leading zeros
     */
    public String toHexStringWithoutZeros() {
        String s = toHexString();
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                num++;
            }
        }

        return s.substring(num);
    }

    /**
     * Compares this BigInt to the specified BigInt
     * @param big - A BigInt to which this BigInt is to be compared
     * @return 1 if this > big , -1 if this < big , 0 if this == big
     */
    public int compareTo(BigInt big) {
        byte[] array = big.getBytes();
        int i;
        int lenLoc = this.number.length;
        int lenRem = big.getBytes().length;
        byte[] temp;

        if (lenLoc > lenRem) {
            temp = new byte[lenLoc];

            System.arraycopy(array, 0, temp, lenLoc - lenRem, lenRem);

            for (i = 0; i < lenRem - lenLoc; i++) {
                temp[i] = 0;
            }

            for (i = 0; i < lenLoc; i++) {
                if ((this.number[i] & 0xff) > (temp[i] & 0xff)) {
                    return 1;
                } else if ((this.number[i] & 0xff) < (temp[i] & 0xff)) {
                    return -1;
                } else {
                    continue;
                }
            }
            return 0;
        } else if (lenRem > lenLoc) {
            temp = new byte[lenRem];

            System.arraycopy(this.number, 0, temp, lenRem - lenLoc, lenLoc);

            for (i = 0; i < lenLoc - lenRem; i++) {
                temp[i] = 0;
            }

            for (i = 0; i < lenRem; i++) {
                if ((temp[i] & 0xff) > (array[i] & 0xff)) {
                    return 1;
                } else if ((temp[i] & 0xff) < (array[i] & 0xff)) {
                    return -1;
                } else {
                    continue;
                }
            }
            return 0;
        } else {

            for (i = 0; i < lenLoc; i++) {

                if ((this.number[i] & 0xff) > (array[i] & 0xff)) {
                    return 1;
                } else if ((this.number[i] & 0xff) < (array[i] & 0xff)) {
                    return -1;
                } else {
                    continue;
                }
            }

            return 0;
        }
    }

    /**
     * Transforms a representation of a string in binary into a BigInt
     * @param s - String representation of a number in binary
     * @return The equivalent BigInt of the given binary number
     */
    public BigInt binaryToBigInt(String s) {
        int size = s.length();

        byte[] array = new byte[size / 8];
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            String temp = s.substring(index, index + 8);

            int value = 0;
            int exp = 1;
            index += 8;

            for (int j = 0; j < 8; j++) {
                if (temp.charAt(j) == '1') {
                    if (j == 7) {
                        exp = 1;
                    } else {
                        exp = exp << (7 - j);
                    }

                    value += exp;
                    exp = 1;
                }
            }

            array[i] = (byte) (value);

        }

        return new BigIntImpl(array);
    }

    /**
     * Returns a BigInt whose value is this << shiftValue
     * @param shiftValue - shift distance in bits
     * @return this << shiftValue
     */
    public BigInt shiftLeft(int shiftValue) {

        String s = this.toBinaryString();

        for (int i = 0; i < shiftValue; i++) {
            s += "0";
        }

        while (s.length() % 8 != 0) {
            s = "0" + s;
        }


        BigInt big = binaryToBigInt(s);

        return big;

    }

    /**
     * Returns a BigInt whose value is this >> shiftValue
     * @param shiftValue - shift distance in bits
     * @return this >> shiftValue
     */
    public BigInt shiftRight(int shiftValue) {

        String s = this.toBinaryString();

        s = s.substring(0, s.length() - shiftValue);

        for (int i = 0; i < shiftValue; i++) {
            s = "0" + s;
        }


        BigInt big = binaryToBigInt(s);

        return big;
    }

    /**
     *  Returns a string representation of BigInt in binary
     * @return The string representation of BigInt in binary
     */
    public String toBinaryString() {
        String str = "";
        String temp;
        boolean eightZeros = true;

        for (byte b : number) {
            temp = Integer.toBinaryString(b & 0xFF);


            while (temp.length() % 8 != 0) {
                temp = "0" + temp;
            }

            str += temp;
        }

        for (int i = 0; i < 8; i++) {
            if (str.charAt(i) == '1') {
                eightZeros = false;
                break;
            }
        }

        if (!eightZeros) {
            return str;
        } else {
            return str.substring(8);
        }
    }

    /**
     *  Returns a string representation of BigInt in binary without leading zeros
     * @return  The string representation of BigInt in binary without leading zeros
     */
    public String toBinaryStringWithoutZeros() {
        String s = this.toBinaryString();
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                num++;
            }
        }

        return s.substring(num);
    }

    /**
     * @return byte representation of BigInt
     */
    public byte[] getBytes() {
        byte[] nBigNum = new byte[this.number.length];

        System.arraycopy(this.number, 0, nBigNum, 0, this.number.length);

        return nBigNum;
    }

    /**
     * Compares this BigInt to the specified BigInt
     * @param b - A BigInt to be compared
     * @return true if the given BigInt equivalent to this BigInt
     */
    public boolean equals(BigInt b) {
        return (compareTo(b) == 0);
    }

    /**
     * Compares this byte representation of BigInt to the specified byte array
     * @param b - A byte array to be compared
     * @return true if the given byte array equivalent to the byte representation of this BigInt
     */
    public boolean equals(byte[] b) {
        return (compareTo(new BigIntImpl(b)) == 0);
    }
}
