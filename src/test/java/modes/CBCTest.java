package modes;

import modes.algorithms.CBC;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;
import abs.AbstractTest;

import static org.junit.jupiter.api.Assertions.*;

class CBCTest extends AbstractTest {

  @Test
  void applyMode1() {
    var key = "80000000000000000000000000000000";
    var in = "00000000000000000000000000000000";

    var inBytes = hexToByte(in);

    CBC cbcMode1 = new CBC(false,key);
    CBC cbcMode2 = new CBC(true,key);

    cbcMode1.crypt(inBytes);
    var encResult = Hex.encodeHexString(inBytes);
    cbcMode2.crypt(inBytes);
    var decResult = Hex.encodeHexString(inBytes);

    assertNotNull(encResult);
    assertNotNull(decResult);
    assertEquals(in, decResult.toUpperCase());
  }

  @Test
  void applyMode2() {
    var key = "CB14A1776ABBC1CDAFE7243DEF2CEA02";
    var in = "F94512A9B42D034EC4792204D708A69B";

    var inBytes = hexToByte(in);

    CBC cbcMode1 = new CBC(false,key);
    CBC cbcMode2 = new CBC(true,key);

    cbcMode1.crypt(inBytes);
    var encResult = Hex.encodeHexString(inBytes);
    cbcMode2.crypt(inBytes);
    var decResult = Hex.encodeHexString(inBytes);

    assertNotNull(encResult);
    assertNotNull(decResult);
    assertEquals(in, decResult.toUpperCase());
  }

  @Test
  void applyMode3() {
    var key = "00000000000000000000000000000000";
    var in = "00000000000000000000000000000000";

    var inBytes = hexToByte(in);

    CBC cbcMode1 = new CBC(false,key);
    CBC cbcMode2 = new CBC(true,key);

    cbcMode1.crypt(inBytes);
    var encResult = Hex.encodeHexString(inBytes);
    cbcMode2.crypt(inBytes);
    var decResult = Hex.encodeHexString(inBytes);

    assertNotNull(encResult);
    assertNotNull(decResult);
    assertEquals(in, decResult.toUpperCase());
  }

  @Test
  void applyMode4() {
    var key = "00000000000000000000000000000000";
    var in = "0000000000000000000000000000000000000000000000000000000000800000";

    var inBytes = hexToByte(in);

    CBC cbcMode1 = new CBC(false,key);
    CBC cbcMode2 = new CBC(true,key);

    cbcMode1.crypt(inBytes);
    var encResult = Hex.encodeHexString(inBytes);
    cbcMode2.crypt(inBytes);
    var decResult = Hex.encodeHexString(inBytes);

    assertNotNull(encResult);
    assertNotNull(decResult);
    assertEquals(in, decResult.toUpperCase());
  }

  @Test
  void applyMode5() {
    var key = "00000000000000000000000000000000";
    var in = "000000000000000000000000000000000000000000";

    var inBytes = hexToByte(in);

    CBC cbcMode1 = new CBC(false,key);
    CBC cbcMode2 = new CBC(true,key);

    cbcMode1.crypt(inBytes);
    var encResult = Hex.encodeHexString(inBytes);
    cbcMode2.crypt(inBytes);
    var decResult = Hex.encodeHexString(inBytes);

    assertNotNull(encResult);
    assertNotNull(decResult);
    assertEquals(in, decResult.toUpperCase());

  }

  @Test
  void applyMode6() {
    var key = "10000000000000000000000000000000";
    var in = "000000000000000000000000000000000000000000";

    var inBytes = hexToByte(in);

    CBC cbcMode1 = new CBC(false,key);
    CBC cbcMode2 = new CBC(true,key);

    cbcMode1.crypt(inBytes);
    var encResult = Hex.encodeHexString(inBytes);
    cbcMode2.crypt(inBytes);
    var decResult = Hex.encodeHexString(inBytes);

    assertNotNull(encResult);
    assertNotNull(decResult);
    assertEquals(in, decResult.toUpperCase());
  }
}
