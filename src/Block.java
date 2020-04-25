import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable {

    private String version;
    private long Timestamp;
    private String hash; //hash of the block
    private String previousHash;
    private String currentTarget = "0";
    private ArrayList<Transaction> data = new ArrayList<>(); // stores all transactions
    private String dataHash; // hash of all transactions
    private int amountOfTransactions;
    private int blockIndex;
    private int Nounce=0;

    public Block(String version, long timestamp, int blockIndex) {
        this.version = version;
        this.Timestamp = timestamp;
        this.blockIndex = blockIndex;
        this.amountOfTransactions = 0;
    }

    public void addData(Transaction data) {
        Transaction temp = new Transaction();
        for(int i = 0; i < data.getTxInCount(); i++){
            temp.addTxIn(data.getTxIn().get(i));
        }
        for(int i = 0; i < data.getTxOutCount(); i++){
            temp.addTxOut(data.getTxOut().get(i));
        }
        temp.setHash(data.getHash());
        this.data.add(temp);
        this.amountOfTransactions += 1;
    }

    public ArrayList<String> getTransactionWithInCoinditionPublicKey(String publicKey){ //return all transaction hash where publicKey occur in transaction inputs
        ArrayList<String> temp;
        ArrayList<String> tempTxHash = new ArrayList<String>();
        for (int i = 1; i < this.getData().size(); i++){
            temp = this.getData().get(i).transactionHashWherePublicKeyInTxInCondition(publicKey);
            tempTxHash.addAll(temp);
        }
        return tempTxHash;
    }

    public ArrayList<UnspentTransactionWallet> getTransactionWithOutCoinditionPublicKeyHash(String publicKeyHash){ //return all UnspentTransactionWallet objects hash where publicKeyHash occur in transaction outputs
        ArrayList<UnspentTransactionWallet> listUnspentTransactionWallet = new ArrayList<UnspentTransactionWallet>();
        for (int i = 0; i < this.getData().size(); i++){
            if (this.getData().get(i).getPublicKeyHashOfOutCondition(0).equals(publicKeyHash)){
                listUnspentTransactionWallet.add(new UnspentTransactionWallet(this.getData().get(i).getHash(),0,this.getData().get(i).getTransactionValue(0)));
            }else if ((this.getData().get(i).getTxOutCount() == 2) && this.getData().get(i).getPublicKeyHashOfOutCondition(1).equals(publicKeyHash)){
                listUnspentTransactionWallet.add(new UnspentTransactionWallet(this.getData().get(i).getHash(),1,this.getData().get(i).getTransactionValue(1)));
            }
        }
        return listUnspentTransactionWallet;
    }



    public String computeBlockHash() {
        return Hash.computeHash(this.toString());

    }

    private void setdataHash() {
        String output = "";
        for(int i = 0; i < this.amountOfTransactions; i++){
            output = output + data.get(i).getHash();
        }

        this.dataHash = Hash.computeHash(output);
    }

    public Transaction findTransaction(String hash){
        for(int i = 0; i < this.amountOfTransactions; i++){
            if(this.data.get(i).getHash().equals(hash)){
                return data.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString(){
        this.setdataHash();
        return this.version + this.Timestamp + this.previousHash + this.currentTarget + this.Nounce + this.dataHash + this.amountOfTransactions + this.blockIndex + this.dataHash;
    }

    public void displayBlock(){
        for (int i = 0; i < this.data.size(); i++){
            this.data.get(i).displayTransaction();
        }
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }

    public String getHash() {
        return hash;
    }

    public int getNumberOfTransactions() {
        return amountOfTransactions;
    }


    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public ArrayList<Transaction> getData() {
        return this.data;
    }


    public String getCurrentTarget() {
        return currentTarget;
    }

    private void setCurrentTarget(String currentTarget) {
        this.currentTarget = currentTarget;
    }



}