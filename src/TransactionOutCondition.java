import java.io.Serializable;

public class TransactionOutCondition implements Serializable {
    private int value; // sum of money to send
    private String hashOfPublicKeyOfRecipient; //hash of the public key of recipient

    public TransactionOutCondition(){
        //
    }

    public TransactionOutCondition(int value, String publicKeyOfReciepient){
        this.value = value;
        this.hashOfPublicKeyOfRecipient = Hash.computeHash(publicKeyOfReciepient);
    }

    @Override
    public String toString(){
        return this.value + this.hashOfPublicKeyOfRecipient;
    }

    public String getHashOfPublicKeyOfRecipient() {
        return hashOfPublicKeyOfRecipient;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setHashOfPublicKeyOfRecipient(String hashOfPublicKeyOfRecipient) {
        this.hashOfPublicKeyOfRecipient = hashOfPublicKeyOfRecipient;
    }

}
