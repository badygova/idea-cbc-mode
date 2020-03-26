package modes.algorithms;

import crypto.CryptoUtils;
import crypto.IdeaCipher;

/**
 * Режим CBC
 */
public class CBC {

    private int blockSize;
    private byte[] prev;
    private byte[] newPrev;
    protected IdeaCipher idea;
    protected boolean encrypt;

    public CBC(boolean encrypt, String key) {
        this.idea = new IdeaCipher(key, encrypt);
        this.encrypt = encrypt;
        blockSize = idea.getBlockSize();
        prev = CryptoUtils.makeKey(key, blockSize);
        newPrev = new byte[blockSize];
    }

    public void crypt(byte[] data, int pos) {
        if (encrypt) {
            CryptoUtils.xor(data, pos, prev, blockSize);
            idea.crypt(data, pos);
            System.arraycopy(data, pos, prev, 0, blockSize);
        } else {
            System.arraycopy(data, pos, newPrev, 0, blockSize);
            idea.crypt(data, pos);
            CryptoUtils.xor(data, pos, prev, blockSize);
            prev = newPrev.clone();
        }
    }

    public void crypt(byte[] data){
      crypt(data, 0);
    }

    public boolean isEncrypt() {
    return encrypt;
  }
}
