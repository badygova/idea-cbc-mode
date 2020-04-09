package abs;

import java.math.BigInteger;

public class AbstractTest {

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
