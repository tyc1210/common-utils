package com.tyc.utils.encrypt;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-06-13 11:05:49
 */

import java.io.UnsupportedEncodingException;

/**
 * Base58是比特币的一种特殊编码方式，主要用于产生比特币钱包地址。
 * 相比Base64，Base58不使用数字"0"，字母大写"O"，字母大写"I"，和字母小写"l"，以及"+"和"/"符号
 * 避免混淆，在某些字体下，数字0和字母大写O，以及字母大写I和字母小写l会非常相似
 * @author tyc
 * @version 1.0
 * @date 2021-08-09 13:42:08
 */
public class Base58 {
    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int BASE_58;
    private static final int BASE_256 = 256;
    private static final int[] INDEXES;

    public Base58() {
    }

    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        } else {
            input = copyOfRange(input, 0, input.length);

            int zeroCount;
            for(zeroCount = 0; zeroCount < input.length && input[zeroCount] == 0; ++zeroCount) {
            }

            byte[] temp = new byte[input.length * 2];
            int j = temp.length;

            byte mod;
            for(int startAt = zeroCount; startAt < input.length; temp[j] = (byte)ALPHABET[mod]) {
                mod = divmod58(input, startAt);
                if (input[startAt] == 0) {
                    ++startAt;
                }

                --j;
            }

            while(j < temp.length && temp[j] == ALPHABET[0]) {
                ++j;
            }

            while(true) {
                --zeroCount;
                if (zeroCount < 0) {
                    byte[] output = copyOfRange(temp, j, temp.length);
                    return new String(output);
                }

                --j;
                temp[j] = (byte)ALPHABET[0];
            }
        }
    }

    public static byte[] decode(String input) {
        if (input.length() == 0) {
            return new byte[0];
        } else {
            byte[] input58 = new byte[input.length()];

            int zeroCount;
            int j;
            for(zeroCount = 0; zeroCount < input.length(); ++zeroCount) {
                char c = input.charAt(zeroCount);
                j = -1;
                if (c >= 0 && c < 128) {
                    j = INDEXES[c];
                }

                if (j < 0) {
                    throw new RuntimeException("Not a Base58 input: " + input);
                }

                input58[zeroCount] = (byte)j;
            }

            for(zeroCount = 0; zeroCount < input58.length && input58[zeroCount] == 0; ++zeroCount) {
            }

            byte[] temp = new byte[input.length()];
            j = temp.length;

            byte mod;
            for(int startAt = zeroCount; startAt < input58.length; temp[j] = mod) {
                mod = divmod256(input58, startAt);
                if (input58[startAt] == 0) {
                    ++startAt;
                }

                --j;
            }

            while(j < temp.length && temp[j] == 0) {
                ++j;
            }

            return copyOfRange(temp, j - zeroCount, temp.length);
        }
    }

    private static byte divmod58(byte[] number, int startAt) {
        int remainder = 0;

        for(int i = startAt; i < number.length; ++i) {
            int digit256 = number[i] & 255;
            int temp = remainder * 256 + digit256;
            number[i] = (byte)(temp / BASE_58);
            remainder = temp % BASE_58;
        }

        return (byte)remainder;
    }

    private static byte divmod256(byte[] number58, int startAt) {
        int remainder = 0;

        for(int i = startAt; i < number58.length; ++i) {
            int digit58 = number58[i] & 255;
            int temp = remainder * BASE_58 + digit58;
            number58[i] = (byte)(temp / 256);
            remainder = temp % 256;
        }

        return (byte)remainder;
    }

    private static byte[] copyOfRange(byte[] source, int from, int to) {
        byte[] range = new byte[to - from];
        System.arraycopy(source, from, range, 0, range.length);
        return range;
    }

    static {
        BASE_58 = ALPHABET.length;
        INDEXES = new int[128];

        int i;
        for(i = 0; i < INDEXES.length; ++i) {
            INDEXES[i] = -1;
        }

        for(i = 0; i < ALPHABET.length; INDEXES[ALPHABET[i]] = i++) {
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "111111111";
        String outToken = encode(s.getBytes("UTF-8"));
        System.out.println(new String(decode(outToken),"UTF-8"));
    }
}
