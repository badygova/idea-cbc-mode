package crypto;

/**
 * Реализация алгоритма IDEA
 */
public class IdeaCipher extends BlockCipher {

    private static final int KEY_SIZE = 16;
    private static final int BLOCK_SIZE = 8;
    private static final int ROUNDS = 8;

    private boolean encrypt;
    private int[] subKey;


    public IdeaCipher(String charKey, boolean encrypt) {
        super(KEY_SIZE, BLOCK_SIZE);
        this.encrypt = encrypt;
        setKey(charKey);
    }


    protected void setKey(byte[] key) {
        int[] tempSubKey = generateSubkeys(key);
        if (encrypt) {
            subKey = tempSubKey;
        } else {
            subKey = invertSubkey(tempSubKey);
        }
    }

    public void crypt(byte[] data, int offset) {
        //Делим 64-битный блок данных на 4 16-битных
        int x1 = CryptoUtils.concat2Bytes(data[offset + 0], data[offset + 1]);
        int x2 = CryptoUtils.concat2Bytes(data[offset + 2], data[offset + 3]);
        int x3 = CryptoUtils.concat2Bytes(data[offset + 4], data[offset + 5]);
        int x4 = CryptoUtils.concat2Bytes(data[offset + 6], data[offset + 7]);

        int k = 0;
        for (int round = 0; round < ROUNDS; round++) {
            int y1 = mul(x1, subKey[k++]);          // Умножаем X1 и 1 subkey
            int y2 = add(x2, subKey[k++]);          // Добавляем X2 и 2 subkey
            int y3 = add(x3, subKey[k++]);          // Добавляем X3 и 3 subkey
            int y4 = mul(x4, subKey[k++]);          // Умножаем X4 и 4 subkey
            int y5 = y1 ^ y3;                       // XOR y1 и y3
            int y6 = y2 ^ y4;                       // XOR y2 и y4
            int y7 = mul(y5, subKey[k++]);          // Умножаем y5 и 5 subkey
            int y8 = add(y6, y7);                   // Добавляем y6 и y7
            int y9 = mul(y8, subKey[k++]);          // Умножаем y8 и 6 subkey
            int y10 = add(y7, y9);                  // Добавляем y7 и y9
            x1 = y1 ^ y9;                           // XOR y1 и y9
            x2 = y3 ^ y9;                           // XOR y3 и y9
            x3 = y2 ^ y10;                          // XOR y2 и y10
            x4 = y4 ^ y10;                          // XOR y4 и y10
        }

        //Выходное преобразование
        int r0 = mul(x1, subKey[k++]);              // Умножаем X1 и 1 subkey
        int r1 = add(x3, subKey[k++]);              // Добавляем X2 и 2 subkey (x2 и x3 помен)
        int r2 = add(x2, subKey[k++]);              // Добавляем X3 и 3 subkey
        int r3 = mul(x4, subKey[k]);                // Умножаем X4 и 4 subkey

        // Соединение блоков
        data[offset + 0] = (byte) (r0 >> 8);
        data[offset + 1] = (byte) r0;
        data[offset + 2] = (byte) (r1 >> 8);
        data[offset + 3] = (byte) r1;
        data[offset + 4] = (byte) (r2 >> 8);
        data[offset + 5] = (byte) r2;
        data[offset + 6] = (byte) (r3 >> 8);
        data[offset + 7] = (byte) r3;
    }


    /**
     *Создаем подключи из пользовательского ключа
     */
    private static int[] generateSubkeys(byte[] userKey) {
        if (userKey.length != 16) {
            throw new IllegalArgumentException();
        }
        int[] key = new int[ROUNDS * 6 + 4]; // 52 16-битных подключей

        // 8 16-битных подключей
        int b1, b2;
        for (int i = 0; i < userKey.length / 2; i++) {
            key[i] = CryptoUtils.concat2Bytes(userKey[2 * i], userKey[2 * i + 1]);
        }

      // Ключ двигается на 25 бит влево и снова делится на 8 подключей
      // Первые 4 используются в раунде 2, последние 4 используются в 3 раунде
      // Ключ двигается еще на 25 бит влево для следующих восьми подключей итд
        for (int i = userKey.length / 2; i < key.length; i++) {
            b1 = key[(i + 1) % 8 != 0 ? i - 7 : i - 15] << 9;   // k1,k2,k3...k6,k7,k0,k9, k10...k14,k15,k8,k17,k18...
            b2 = key[(i + 2) % 8 < 2 ? i - 14 : i - 6] >>> 7;   // k2,k3,k4...k7,k0,k1,k10,k11...k15,k8, k9,k18,k19...
            key[i] = (b1 | b2) & 0xFFFF;
        }
        return key;
    }

    /**
     * Переворачиваем и инвертируем подключи, чтобы получить подключи расшифровки
     * Они являются либо аддитивными, либо мультипликативными инверсиями подключей шифрования в обратном порядке
     */
    private static int[] invertSubkey(int[] subkey) {
        int[] invSubkey = new int[subkey.length];
        int p = 0;
        int i = ROUNDS * 6;
        // Раунд 9
        invSubkey[i]     = mulInv(subkey[p++]);     // 48 <- 0
        invSubkey[i + 1] = addInv(subkey[p++]);     // 49 <- 1
        invSubkey[i + 2] = addInv(subkey[p++]);     // 50 <- 2
        invSubkey[i + 3] = mulInv(subkey[p++]);     // 51 <- 3
        // От раунда 8 к 2
        for (int r = ROUNDS - 1; r > 0; r--) {
            i = r * 6;
            invSubkey[i + 4] = subkey[p++];         // 46 <- 4 ...
            invSubkey[i + 5] = subkey[p++];         // 47 <- 5 ...
            invSubkey[i]     = mulInv(subkey[p++]); // 42 <- 6 ...
            invSubkey[i + 2] = addInv(subkey[p++]); // 44 <- 7 ...
            invSubkey[i + 1] = addInv(subkey[p++]); // 43 <- 8 ...
            invSubkey[i + 3] = mulInv(subkey[p++]); // 45 <- 9 ...
        }
        // Раунд 1
        invSubkey[4] = subkey[p++];                 // 4 <- 46
        invSubkey[5] = subkey[p++];                 // 5 <- 47
        invSubkey[0] = mulInv(subkey[p++]);         // 0 <- 48
        invSubkey[1] = addInv(subkey[p++]);         // 1 <- 49
        invSubkey[2] = addInv(subkey[p++]);         // 2 <- 50
        invSubkey[3] = mulInv(subkey[p]);           // 3 <- 51
        return invSubkey;
    }

    private static int add(int x, int y) {
        return (x + y) & 0xFFFF;
    }

    private static int addInv(int x) {
        return (0x10000 - x) & 0xFFFF;
    }

    private static int mul(int x, int y) {
        long m = (long) x * y;
        if (m != 0) {
            return (int) (m % 0x10001) & 0xFFFF;
        } else {
            if (x != 0 || y != 0) {
                return (1 - x - y) & 0xFFFF;
            }
            return 1;
        }
    }

    /**
     * Обратное число (Евклидов алгоритм)
     */
    private static int mulInv(int x) {
        if (x <= 1) {
            return x;
        }
        try {
            int y = 0x10001;
            int t0 = 1;
            int t1 = 0;
            while (true) {
                t1 += y / x * t0;
                y %= x;
                if (y == 1) {
                    return (1 - t1) & 0xffff;
                }
                t0 += x / y * t1;
                x %= y;
                if (x == 1) {
                    return t0;
                }
            }
        } catch (ArithmeticException e) {
            return 0;
        }
    }
}
