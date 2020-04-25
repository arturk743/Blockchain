import java.io.Serializable;

public class UnspentTransactionWallet implements Serializable {
    private String hashOfTransaction; // hash of transaction that is unspent
    private int index; // index of out transaction
    private int value; // value of transaction

    public UnspentTransactionWallet(String hashOfTransaction, int index, int value) {
        this.hashOfTransaction = hashOfTransaction;
        this.index = index;
        this. value = value;
    }
    public UnspentTransactionWallet(){
        //
    }

    @Override
    public String toString(){
            return this.hashOfTransaction + " " +this.index+" "+ this.value;
        }

    public String getHashOfTransaction() {
        return hashOfTransaction;
    }

    public void setHashOfTransaction(String hashOfTransaction) {
        this.hashOfTransaction = hashOfTransaction;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}


