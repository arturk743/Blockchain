import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Transaction implements Serializable {
    private String hash=""; // hash(name) of whole transaction
    private String version;
    private int randomNumber;
    private int txInCount; //number of input transactions
    private ArrayList<TransactionInCondition> txIn = new ArrayList<TransactionInCondition>(); //list contains list of conditions of input transactions
    private int txOutCount; //number of output transactions
    private ArrayList<TransactionOutCondition> txOut = new ArrayList<TransactionOutCondition>(); //list contains list of conditions of output transactions

    public Transaction() {
        this.version = "00001";
        this.txInCount = 0;
        this.txOutCount = 0;
        Random generator = new Random();
        this.randomNumber = generator.nextInt(1000000);
    }



    @Override
    public String toString(){//Transaction to string
        String output = "";
        for(int i =0; i < this.txIn.size(); i++){
            output = output + i + i + i + i + i + i + i + i + txIn.get(i).toString();
        }

        for(int k =0; k < this.txOut.size(); k++){
            output = output + k +k + k + k + k + k+ k + txOut.get(k).toString();
        }

        output = output + "VERSION"+this.version + this.txInCount + this.txOutCount + this.randomNumber;
        return output;
    }


    public void addTxIn(TransactionInCondition txIn) { //add transaction input
        TransactionInCondition temp = new TransactionInCondition(txIn.getHash(), txIn.getIndex(), txIn.getPublicKey());
        temp.setSignature(txIn.getSignature());
        this.txIn.add(temp);
        this.txInCount += 1;
    }

    public void addTxOut(TransactionOutCondition txOut) { //add transaction output
        TransactionOutCondition temp = new TransactionOutCondition();
        temp.setHashOfPublicKeyOfRecipient(txOut.getHashOfPublicKeyOfRecipient());
        temp.setValue(txOut.getValue());
        this.txOut.add(temp);
        this.txOutCount += 1;

    }

    public int getTransactionValue() { //return sum value of transaction outputs
        int sum = 0;
        for (int i = 0; i < this.txOutCount; i++) {
            sum += this.txOut.get(i).getValue();
        }
        return sum;
    }

    public int getTransactionValue(int index){ //return value of specific transaction output
        return this.txOut.get(index).getValue();
    }

    public ArrayList<String> transactionHashWherePublicKeyInTxInCondition(String publicKey){
        ArrayList<String> temp = new ArrayList<String>();
        for(int i =0; i < this.txInCount; i++){
            if(this.txIn.get(i).getPublicKeyString().equals(publicKey)){
                temp.add(this.txIn.get(i).getHash());
            }
        }
        return temp;
    }

    public void displayTransaction(){
        System.out.println("Transaction hash : " + this.hash);
        System.out.println("TransactionInCondition");
        for (int i =0; i < txInCount; i++){
            System.out.println(this.txIn.get(i).getHash() + " "+ this.txIn.get(i).getIndex());
        }
        System.out.println("TransactionOutCondition");
        for (int i =0; i < txOutCount; i++){
            System.out.println(i + " " +this.txOut.get(i).getHashOfPublicKeyOfRecipient() + " " + this.txOut.get(i).getValue());
        }
    }


    public void computeTransactionHash(){
        this.hash = Hash.computeHash(this.toString());
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getVersion() {
        return version;
    }

    public int getTxInCount() {
        return txInCount;
    }

    public ArrayList<TransactionInCondition> getTxIn() {
        return txIn;
    }

    public int getTxOutCount() {
        return txOutCount;
    }

    public ArrayList<TransactionOutCondition> getTxOut() {
        return txOut;
    }

    public String getPublicKeyHashOfOutCondition(int index){
        return this.getTxOut().get(index).getHashOfPublicKeyOfRecipient();
    }
}
