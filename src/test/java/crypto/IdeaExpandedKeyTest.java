package crypto;

import modes.algorithms.CBC;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ideaExpandedKeyTest {

    private static final byte[] inBytes = hexToByte("00000000000000000000000000000000");


    @Test
    void expandKeyTest1_128() throws Exception {
        var expectedKeys = List.of(
            "7a690123", "4a4cf6ee", "d1c145fd", "4a929170", "551a7316",
            "d46b4d1f", "6a68b2dd", "52a45b5f", "d99775e0", "fbec331b",
            "9879762b", "dbdb6103", "b66dfcd5", "560475c7", "897923f4",
            "cb157a67", "be38b5d6", "f5e2a20b", "db46b244", "258e03fb",
            "4e45dcc8", "38a3bf7b", "b600f7b9", "ce23f06b", "b255f1c4",
            "66fe13cb", "3fa7323b", "e5168ed7", "3c1ca161", "ac63d7fb",
            "50826e87", "87b0e657", "77f7012a", "c1e7aa83", "79d936fc",
            "56174f97", "9f8f4547", "c3901cc5", "f32a2b2e", "c604c22b"
        );

        String key = "80000000000000000000000000000000";
        CBC cbc = new CBC(true, key);
        var result = cbc.expandKey(keyBytes);
        assertEquals(expectedKeys, keysToStringList(result));

        var expectedEncryptionRes = "B3E2AD5608AC1B6733A7CB4FDF8F9952";
        var encResult = Hex.encodeHexString(cbc.blockEncryption(inBytes));
        assertEquals(expectedEncryptionRes, encResult.toUpperCase());
    }


    private List<String> keysToStringList(int[] keys) {
        var res = new ArrayList<String>();
        for (int i : keys) {
            var hex = Integer.toHexString(i);
            var addZero = new StringBuilder();
            if (hex.length() < 8) {
                for (int j = 0; j < 8 - hex.length(); j ++) {
                    addZero.append("0");
                }
            }
            addZero.append(hex);
            res.add(addZero.toString());
        }
        return res;
    }

  protected static byte[] hexToByte(String hexString) {
    byte[] keyByte = new byte[hexString.length() / 2];
    String keyBinary = new BigInteger(hexString, 16).toString(2);

    int k = 0;
    if (keyBinary.length() < keyByte.length * 8) {
      var tmp = new StringBuffer();
      for (int i = 0; i < keyByte.length * 8 - keyBinary.length(); i ++) {
        tmp.append("0");
      }
      tmp.append(keyBinary);
      keyBinary = tmp.toString();
    }
    for (int i = 0; i < keyBinary.length(); i += 8) {
      var tmp = new StringBuffer();
      for (int j = i; j < i + 8; j ++) {
        tmp.append(keyBinary.charAt(j));
      }
      keyByte[k] = (byte) Integer.parseInt(tmp.toString(), 2);
      k ++;
    }

    return keyByte;
  }
}
