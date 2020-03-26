package modes;

import crypto.IdeaCipher;

/**
 * Режимы
 */
public abstract class OperationMode {

    public enum Mode {
        ECB, CBC
    }

    protected IdeaCipher idea;
    protected boolean encrypt;

    public OperationMode(IdeaCipher idea, boolean encrypt) {
        this.idea = idea;
        this.encrypt = encrypt;
    }

    protected abstract void crypt(byte[] data, int pos);

    void crypt(byte[] data){
        crypt(data, 0);
    }

    public boolean isEncrypt() {
        return encrypt;
    }
}
