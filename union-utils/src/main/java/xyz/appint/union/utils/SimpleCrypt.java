package xyz.appint.union.utils;


public class SimpleCrypt {
    public static char[] encodeByte(byte[] data) {
        char[] out = new char[((data.length + 2) / 3) * 4];

        //
        // 3 bytes encode to 4 chars.  Output is always an even
        // multiple of 4 characters.
        //
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            boolean quad = false;
            boolean trip = false;

            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length) {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index + 0] = alphabet[val & 0x3F];
        }
        return out;
    }

    public static byte[] decodeByte(String msg) {
        char[] swap = new char[msg.length()];
        msg.getChars(0, msg.length(), swap, 0);
        return decodeByte(swap);
    }

    public static byte[] decodeByte(char[] data) {
        int len = ((data.length + 3) / 4) * 3;
        if (data.length > 0 && data[data.length - 1] == '=') --len;
        if (data.length > 1 && data[data.length - 2] == '=') --len;
        byte[] out = new byte[len];

        int shift = 0;   // # of excess bits stored in accum
        int accum = 0;   // excess bits
        int index = 0;

        for (int ix = 0; ix < data.length; ix++) {
            int value = codes[data[ix] & 0xFF];   // ignore high byte of char
            if (value >= 0) {                     // skip over non-code
                accum <<= 6;            // bits shift up by 6 each time thru
                shift += 6;             // loop, with new bits being put in
                accum |= value;         // at the bottom.
                if (shift >= 8) {     // whenever there are 8 or more shifted in,
                    shift -= 8;         // write them out (from the top, leaving any
                    out[index++] =      // excess at the bottom for next iteration.
                            (byte) ((accum >> shift) & 0xff);
                }
            }
        }
        return out;
    }

    private static byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++) codes[i] = -1;
        for (int i = 'A'; i <= 'Z'; i++) codes[i] = (byte) (i - 'A');
        for (int i = 'a'; i <= 'z'; i++) codes[i] = (byte) (26 + i - 'a');
        for (int i = '0'; i <= '9'; i++) codes[i] = (byte) (52 + i - '0');
        codes['+'] = 62;
        codes['/'] = 63;
    }

    private static char[] alphabet =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
                    .toCharArray();

    public static String encodeString(String msg) {
        byte[] swap = msg.getBytes();
        String str = new String(encodeByte(swap));
        return str;
    }


    public static String decodeString(String msg) {
        char[] swap = new char[msg.length()];
        msg.getChars(0, msg.length(), swap, 0);
        String str = new String(decodeByte(swap));
        return str;
    }

    public static void main(String[] args) throws Exception {

        String str = "NzU1NjEw";
        System.out.println(decodeString(str));
        if (true) return;
//		  	String keys[]=str.split("[|]");
//		    BigInteger n = new BigInteger(SimpleCrypt.decodeByte(keys[0]));
//		  	BigInteger e = new BigInteger(SimpleCrypt.decodeByte(keys[1]));
//		  	System.out.println("n="+n);
//		  	System.out.println("e="+e);
        //  String en=SimpleCrypt.encodeString("��");
        //System.out.println("Ly/Qwr2oIM7Esb7OxLW1JS50eHQ=");
        //System.out.println(SimpleCrypt.decodeString("PQ=="));
        // System.out.println(SimpleCrypt.decodeString("/wEWCwKw846vBQLZkfXHBALfkfXHBALNkfXHBALV/t+pCALejcuGCQKWzavVDwL5u4fwDAKv7p10ArnRnywC0+bp4wxYUWpnSt2EpGDtiGxEKxc5hZCAgw=="));
    }


}
