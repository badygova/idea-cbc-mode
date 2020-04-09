package modes.algorithms;

import crypto.IdeaCipher;
import modes.OperationMode;


/**
 * Режим ECB
 */
public class ECB extends OperationMode {

    public ECB(boolean encrypt, String key) {
        super(new IdeaCipher(key, encrypt), encrypt);
    }

    @Override
    public void crypt(byte[] data, int pos) {
        idea.crypt(data, pos);
    }
}
