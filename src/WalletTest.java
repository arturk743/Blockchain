import org.junit.Test;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

import static org.junit.Assert.*;

public class WalletTest {
    public KeyPair keys0 = Wallet.generateKeyPair2();
    public KeyPair keys1 = Wallet.generateKeyPair2();
    public KeyPair keys2 = Wallet.generateKeyPair2();
    public KeyPair keys3 = Wallet.generateKeyPair2();
    public  Blockchain chain = new Blockchain(keys0.getPublic());
    public Wallet myWallet = new Wallet();
    public Wallet otherWallet = new Wallet();
    public Transaction tx0 = new Transaction();
    private Base64.Encoder encoder = Base64.getEncoder();

    public WalletTest(){
        myWallet.setPublicKey(keys0.getPublic());
        myWallet.setPrivateKey(keys0.getPrivate());
        otherWallet.setPublicKey(keys1.getPublic());
        otherWallet.setPrivateKey(keys1.getPrivate());
        //chain.setMyPublicAddress(pubKey0);
        myWallet.setChain(this.chain);
        otherWallet.setChain(this.chain);
    }

    @Test
    public void generateSignature() {
        Wallet wallet3 = new Wallet();
        assertEquals(true, Wallet.verifySignature("jak sie masz", wallet3.generateSignature("jak sie masz"),wallet3.getPublicKey()));
    }

    @Test
    public void veryfiSignature() {
    }

    @Test
    public void fromBase64ToPubliKey() {
    }

    @Test
    public void generateTransaction() {
        TransactionInCondition txIn0 = new TransactionInCondition(chain.getLatestBlock().getData().get(0).getHash(),0, this.keys0.getPublic());
        TransactionOutCondition txOut00 = new TransactionOutCondition(20,encoder.encodeToString(this.keys1.getPublic().getEncoded()));
        TransactionOutCondition txOut01 = new TransactionOutCondition(30,encoder.encodeToString(this.keys0.getPublic().getEncoded()));
        tx0.addTxIn(txIn0);
        tx0.addTxOut(txOut00);
        tx0.addTxOut(txOut01);
        tx0.computeTransactionHash();
        myWallet.generateListOfUnspentTransaction();
        Transaction temp = myWallet.generateTransaction(20,encoder.encodeToString(this.keys1.getPublic().getEncoded()));
        System.out.println(temp);
        assertEquals(tx0.getTransactionValue(0),temp.getTransactionValue(0));
        assertEquals(tx0.getTransactionValue(1),temp.getTransactionValue(1));
        assertEquals(tx0.getTxIn().get(0).getHash(), temp.getTxIn().get(0).getHash());
        assertEquals(tx0.getTxOut().get(0).getHashOfPublicKeyOfRecipient(), temp.getTxOut().get(0).getHashOfPublicKeyOfRecipient());
        assertEquals(tx0.getTxOutCount(),temp.getTxOutCount());
    }

    @Test
    public void generateListOfUnspentTransaction() {
        myWallet.generateListOfUnspentTransaction();
        System.out.println(myWallet.getTempListOfUnspentTransaction().size());
        System.out.println(Hash.computeHash(encoder.encodeToString(this.keys0.getPublic().getEncoded())));
        System.out.println(myWallet.getTempWalletBalance());
        assertEquals(chain.getLatestBlock().getData().get(0).getHash(), myWallet.getTempListOfUnspentTransaction().get(0).getHashOfTransaction());
        assertEquals(chain.getLatestBlock().getData().get(0).getTxOutCount()-1, myWallet.getTempListOfUnspentTransaction().get(0).getIndex());

        chain.addUnprocessedTransaction(myWallet.generateTransaction(20, encoder.encodeToString(this.keys1.getPublic().getEncoded())));
        chain.addBlock();
        myWallet.setChain(chain);
        otherWallet.setChain(chain);
        assertEquals(20, otherWallet.getTempWalletBalance());
    }

    @Test
    public void setTempBalance() {
        myWallet.generateListOfUnspentTransaction();
        System.out.println("------------------");
        System.out.println("list of unspenttx : " + chain.getListOfUnspentTransactions());
        System.out.println("------------------");
        otherWallet.generateListOfUnspentTransaction();
        assertEquals(50, myWallet.getTempWalletBalance());
        chain.addUnprocessedTransaction(myWallet.generateTransaction(20, encoder.encodeToString(this.keys1.getPublic().getEncoded())));
        System.out.println("list of unprocessed tx : " + chain.getListOfUnprocessedTransactions().get(0).getHash());
        chain.addBlock();
        System.out.println("------------------");
        System.out.println("list of unspenttx : " + chain.getListOfUnspentTransactions());
        System.out.println("------------------");
        myWallet.setChain(chain);
        otherWallet.setChain(chain);
        chain.displayChain();
        System.out.println("------------------");
        System.out.println("list of unspenttx : " + chain.getListOfUnspentTransactions());
        System.out.println("------------------");
        //System.out.println(otherWallet.getPublicKeyString());
        //System.out.println(encoder.encodeToString(this.pubKey1.getEncoded()));
        //System.out.println(Hash.computeHash(myWallet.getPublicKeyString()));
        assertEquals(80, myWallet.getTempWalletBalance());
        assertEquals(20, otherWallet.getTempWalletBalance());
        System.out.println("------------------");
        System.out.println(otherWallet.getTempListOfUnspentTransaction());
        chain.addUnprocessedTransaction(otherWallet.generateTransaction(10,encoder.encodeToString(this.keys0.getPublic().getEncoded())));
        chain.addBlock();
        myWallet.setChain(chain);
        otherWallet.setChain(chain);
        chain.displayChain();
        assertEquals(140, myWallet.getTempWalletBalance());
        assertEquals(10, otherWallet.getTempWalletBalance());
        chain.addUnprocessedTransaction(myWallet.generateTransaction(40,encoder.encodeToString(this.keys1.getPublic().getEncoded())));
        chain.addBlock();
        myWallet.setChain(chain);
        otherWallet.setChain(chain);
        chain.displayChain();
        assertEquals(150, myWallet.getTempWalletBalance());
        assertEquals(50, otherWallet.getTempWalletBalance());
        chain.addUnprocessedTransaction(myWallet.generateTransaction(30,encoder.encodeToString(this.keys1.getPublic().getEncoded())));
        chain.addUnprocessedTransaction(otherWallet.generateTransaction(30,encoder.encodeToString(this.keys0.getPublic().getEncoded())));
        chain.addBlock();
        myWallet.setChain(chain);
        otherWallet.setChain(chain);
        chain.displayChain();
        assertEquals(200, myWallet.getTempWalletBalance());
        assertEquals(50, otherWallet.getTempWalletBalance());
    }
}
