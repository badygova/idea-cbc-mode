package crypto;


public abstract class BlockCipher {

    private int keySize;
    private int blockSize;

    BlockCipher(int keySize, int blockSize) {
        this.keySize = keySize;
        this.blockSize = blockSize;
    }

    public int getBlockSize() {
        return blockSize;
    }


    protected abstract void setKey(byte[] key);


    protected void setKey(String charKey) {
        setKey(CryptoUtils.makeKey(charKey, keySize));
    }


    public abstract void crypt(byte[] data, int offset);


    public void crypt(byte[] data) {
        crypt(data, 0);
    }
}
