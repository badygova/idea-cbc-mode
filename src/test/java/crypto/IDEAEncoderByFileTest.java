package crypto;

import abs.AbstractTest;
import modes.algorithms.ECB;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IDEAEncoderByFileTest extends AbstractTest {

  @Test
  public void test1() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader("key1.txt"));

    while (br.ready()) {
      br.readLine();

      var key = br.readLine().substring(4);
      var expectedResult = br.readLine().substring(7);
      var in = hexToByte(br.readLine().substring(6));

      ECB idea = new ECB(true, key);
      idea.crypt(in,0);
      var result = Hex.encodeHexString(in);

      assertEquals(expectedResult, result.toUpperCase());
    }
  }
}


