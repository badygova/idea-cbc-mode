package crypto;

public class CryptoUtils{

    /**
     * Преобразуем строку в ключ заданной длины
     */
    public static byte[] makeKey(String charKey, int size) {
        byte[] key = new byte[size];
        int i, j;
        for (j = 0; j < key.length; ++j) {
            key[j] = 0;
        }
        for (i = 0, j = 0; i < charKey.length(); i++, j = (j + 1) % key.length) {
            key[j] ^= (byte) charKey.charAt(i);
        }
        return key;
    }

    /**
     * XOR 2 блоков
     */
    public static void xor(byte[] a, int pos, byte[] b, int blockSize) {
        for (int p = 0; p < blockSize; p++) {
            a[pos + p] ^= b[p];
        }
    }

    /**
     * Объединяем два байта в один 16-битный блок
     */
    static int concat2Bytes(int b1, int b2) {
        b1 = (b1 & 0xFF) << 8;  // xxxxxxxx00000000
        b2 = b2 & 0xFF;         // 00000000xxxxxxxx
        return (b1 | b2);       // xxxxxxxxxxxxxxxx
    }

    /**
     * Объединяем два байтовых массива в один
     */
    public static byte[] concat2Bytes(byte[] b1, byte[] b2) {
        byte[] out = new byte[b1.length + b2.length];
        int i = 0;
        for (byte aB1 : b1) {
            out[i++] = aB1;
        }
        for (byte aB2 : b2) {
            out[i++] = aB2;
        }
        return out;
    }
}
