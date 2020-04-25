import java.io.Serializable;

public class TemporaryUnspentTransaction extends UnspentTransaction {
    private int index; // index in ListOfUnspentTransaction in Blckchain

    public TemporaryUnspentTransaction(UnspentTransaction transaction, int index) {
        super(transaction.getHashOfTransaction(),transaction.isOut(0),transaction.isOut(1));
        this.index = index;
    }


    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public UnspentTransaction getUnspentTx(){
        return new UnspentTransaction(this.getHashOfTransaction(),this.isOut(0),this.isOut(1));
    }
}
