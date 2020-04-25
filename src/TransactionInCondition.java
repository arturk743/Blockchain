import java.io.Serializable;
import java.security.PublicKey;
import java.util.Base64;

public class TransactionInCondition implements Serializable {
    private String hash; //the hash of the referenced transaction
    private int index; //the index of the specific output in the transaction. The first output is 0, etc.
    private byte[] signature; //the signed hash of reference transaction
    private PublicKey publicKey; //public key of sender

    public TransactionInCondition(String hash, int index, PublicKey publicKey) {
        this.hash = hash;
        this.index = index;
        this.publicKey = publicKey;
    }

    public TransactionInCondition(TransactionInCondition txIn){
        this.hash = txIn.getHash();
        this.index = txIn.getIndex();
        this.publicKey = txIn.getPublicKey();
        this.signature = txIn.getSignature();
    }

    @Override
    public String toString(){
        String output = "";
        output = output + Base64Encoder.publicKeyToStringBase62(this.publicKey) + this.hash + this.index + this.signature;
        return output;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String publicKeyHash(){
        return Hash.computeHash(Base64Encoder.publicKeyToStringBase62(this.publicKey));
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPublicKeyString() { return Base64Encoder.publicKeyToStringBase62(this.publicKey); }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
