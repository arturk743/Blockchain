import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;

public class Blockchain implements Serializable {

    private ArrayList<Block> chain;
    private ArrayList<UnspentTransaction> listOfUnspentTransactions; // list of simplified transactions that can be used
    private ArrayList<Transaction> listOfUnprocessedTransactions; // list of transactions to verified and put in block
    private PublicKey publicKey;
    private PrivateKey privateKey;
    //private Base64.Encoder encoder = Base64.getEncoder();

    public Blockchain(PublicKey pubKey) {
        this.listOfUnspentTransactions = new ArrayList<UnspentTransaction>();
        this.listOfUnprocessedTransactions = new ArrayList<Transaction>();
        chain = new ArrayList<Block>();
        this.publicKey = pubKey;
        chain.add(generateGenesis());
        this.generateListOfUnspentTransactions();
    }

    public Blockchain(Blockchain blockch){
        chain = new ArrayList<Block>();
        chain.addAll(blockch.getChain());
    }

    private Block generateGenesis() { //create first block
        Block genesis = new Block("00001", new java.util.Date().getTime(),0);
        genesis.addData(this.generateFirstTransaction());
        genesis.setPreviousHash(null);
        genesis.computeBlockHash();
        return genesis;
    }

    public void addBlock() { //add new block to the chain
        Block newBlock = new Block("00001", new java.util.Date().getTime(),chain.size());
        newBlock.setPreviousHash(chain.get(chain.size()-1).getHash());
        newBlock.addData(this.generateFirstTransaction());
        //System.out.println("Weryfikuję tranzakcje");
        int listOfUnprocessedTransactionsSize = this.listOfUnprocessedTransactions.size();
        for(int i=0; i < listOfUnprocessedTransactionsSize; i++){
            //System.out.println("Numer przetworzonej tranzakcji " +i);
            if( this.verifyTransaction(this.listOfUnprocessedTransactions.get(0)) ){
                //System.out.println("Udało dodać się tranzakcję");
                newBlock.addData(this.listOfUnprocessedTransactions.remove(0));
            }else{
                //System.out.println("Odrzucona tranzakcja");
                this.listOfUnprocessedTransactions.remove(0);
            }
            if( i == 3 ){ break; }
        }
        newBlock.computeBlockHash();
        chain.add(newBlock);
        this.listOfUnspentTransactions.add(new UnspentTransaction(newBlock.getData().get(0).getHash(), true, false)); //add first transaction (for miner) to listOfUnspentTransactions
        this.deleteSpentTransactions(); //check if any unspent tx has two out false and remove if true
    }

    public boolean verifyTransaction(Transaction tx) {
        ArrayList<TransactionInCondition> listOfInTransaction = new ArrayList<TransactionInCondition>(); // list of  in transaction to check ??
        ArrayList<TemporaryUnspentTransaction> temporaryListOfUnspentTransaction = new ArrayList<TemporaryUnspentTransaction>(); // list of transaction used in tx ??

        //---------------1----------- check if transaction inputs haven't been used in another transaction
        for (int i = 0; i < tx.getTxInCount(); i++) {
            listOfInTransaction.add(new TransactionInCondition(tx.getTxIn().get(i)));

            for (int k = 0; k < this.listOfUnspentTransactions.size(); k++) {
                if (listOfUnspentTransactions.get(k).getHashOfTransaction().equals(listOfInTransaction.get(i).getHash()) && listOfUnspentTransactions.get(k).isOut(listOfInTransaction.get(i).getIndex())) {
                    temporaryListOfUnspentTransaction.add(new TemporaryUnspentTransaction(listOfUnspentTransactions.get(k), k));
                    temporaryListOfUnspentTransaction.get(temporaryListOfUnspentTransaction.size() - 1).changeOut(listOfInTransaction.get(i).getIndex());
                }
            }
        }
        if (temporaryListOfUnspentTransaction.size() != listOfInTransaction.size()) {
            //System.out.println("Blad 12");
            return false;
        }

        System.out.print(chain.size());
        //--------------2-------------------finding input transactions in blockchain and check signatures and values of transactions
        int sumOfTransactions = 0;
        int numberOfFoundTransactions = 0;
        Transaction tempTransaction = null;

        for (int k = chain.size()-1; k >= 0; k--) {

            for (int j = 0; j < listOfInTransaction.size(); j++) {
                tempTransaction = chain.get(k).findTransaction(listOfInTransaction.get(j).getHash());

                if (tempTransaction != null) {

                    if (tempTransaction.getTxOut().get(listOfInTransaction.get(j).getIndex()).getHashOfPublicKeyOfRecipient().equals(listOfInTransaction.get(j).publicKeyHash())) { //check hash of public key
                        if(Wallet.verifySignature(tempTransaction.getHash(), listOfInTransaction.get(j).getSignature(),listOfInTransaction.get(j).getPublicKey())) { // chech if signature is valid
                            sumOfTransactions += tempTransaction.getTransactionValue(listOfInTransaction.get(j).getIndex());
                            numberOfFoundTransactions += 1;
                        }else{
                            //System.out.println("Podpis nie został zatwierdzony.");
                            return false;
                        }
                    }
                }
            }
        }

        if (numberOfFoundTransactions != listOfInTransaction.size()) {
            System.out.println("Blad");
            return false;
        }

        if (sumOfTransactions < tx.getTransactionValue()) {
            return false;
        } else {
            for (int i = 0; i < temporaryListOfUnspentTransaction.size(); i++) { // changing listOfUnspentTransactions after transaction validation
                this.listOfUnspentTransactions.set(temporaryListOfUnspentTransaction.get(i).getIndex(), temporaryListOfUnspentTransaction.get(i).getUnspentTx());
            }
            //adding new record dodanie nowych rekordow do unspentTransactions z tej tranzakcji z poli out
            if (tx.getTxOutCount() == 1){
                this.listOfUnspentTransactions.add(new UnspentTransaction(tx.getHash(),true, false));
            } else {
                this.listOfUnspentTransactions.add(new UnspentTransaction(tx.getHash(), true, true));
            }
            //System.out.println(this.listOfUnspentTransactions.get(1).isOut(0));
            //System.out.println(this.listOfUnspentTransactions.get(1).isOut(1));
            //System.out.println(this.listOfUnspentTransactions.size());
            //System.out.print(this.listOfUnspentTransactions.get(2).isSpent());
            return true;
        }
    }


    public Transaction generateFirstTransaction(){ // create first transaction in the block (for miner)
        Transaction temp = new Transaction();
        TransactionOutCondition txOut = new TransactionOutCondition(50, Base64Encoder.publicKeyToStringBase62(this.publicKey));
        temp.addTxOut(txOut);
        temp.computeTransactionHash();
        return temp;
    }

    public void generateListOfUnspentTransactions() { //create first transaction where miner gets payment for work
        UnspentTransaction temp = new UnspentTransaction(this.getLatestBlock().getData().get(0).getHash(),true, false); // only for first block (genesis)
        this.listOfUnspentTransactions.add(temp);
    }

    public Transaction findTransaction(String hash){
        Transaction tx;
        for(int i = 0; i < this.chain.size(); i++){
            tx = chain.get(i).findTransaction(hash);

            if(tx != null){ return tx; }
        }
        return null;
    }

    public void deleteSpentTransactions(){
        int listOfUnspentTransactionsSize = this.listOfUnspentTransactions.size();
        int index = 0;
        for(int i = 0; i < listOfUnspentTransactionsSize; i++){
            if(this.listOfUnspentTransactions.get(index).isSpent()){
                this.listOfUnspentTransactions.remove(index);
            }else{
                index += 1;
            }
        }
    }

    public void displayChain() {

        for(int i=0; i<chain.size(); i++) {
            chain.get(i).displayBlock();
        }
    }

    public void addUnprocessedTransaction(Transaction tx) { //add transaction to stack in order to be processed
        this.listOfUnprocessedTransactions.add(tx);
    }

    public int getChainSize(){
        return this.chain.size();
    }

    public Block getBlock(int index){
        return chain.get(index);
    }

    public void setMyPublicAddress(PublicKey keys) { this.publicKey = keys; }

    public ArrayList<Block> getChain() { return chain; }

    public ArrayList<UnspentTransaction> getListOfUnspentTransactions() {
        return listOfUnspentTransactions;
    }

    public ArrayList<Transaction> getListOfUnprocessedTransactions() {
        return listOfUnprocessedTransactions;
    }

    public Block getLatestBlock() {
        return this.chain.get(chain.size()-1);
    }


}