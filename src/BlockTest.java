import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class BlockTest {
    /*public Transaction tx1 = new Transaction();
    public Transaction tx2 = new Transaction();
    public String tx1Hash = "rrrrrr432543";
    public String tx2Hash = "ffffffffffff";
    public String pubKey1 = "hello world";
    public String pubKey2 = "jak sie masz";
    public String pubKeyHash1 = "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9";
    public String transactionHash1 = "asfadfgsdfhsdfhs";
    public String transactionHash2 = "bbbbbbbbbbdfdfhs";
    public TransactionInCondition txIn1 = new TransactionInCondition(this.transactionHash1, 0, this.pubKey1);
    public TransactionOutCondition txOut1 = new TransactionOutCondition(50, this.pubKey1);
    public TransactionInCondition txIn2 = new TransactionInCondition(this.transactionHash2, 0, this.pubKey2);
    public TransactionOutCondition txOut2 = new TransactionOutCondition(70, this.pubKey2);
    public Block blk = new Block("00001",124353434,4);

    public BlockTest(){
        tx1.addTxOut(this.txOut2);
        tx1.addTxOut(this.txOut1);
        tx1.addTxIn(this.txIn1);
        tx2.addTxOut(this.txOut2);
        tx2.addTxIn(this.txIn2);
        tx1.setHash(this.tx1Hash);
        tx2.setHash(this.tx2Hash);
    }

    @Test
    public void getVersion() {
        assertEquals("00001",blk.getVersion());
    }

    @Test
    public void setVersion() {
        blk.setVersion("00002");
        assertEquals("00002",blk.getVersion());
    }

    @Test
    public void getTimestamp() {
        assertEquals(124353434,blk.getTimestamp());
    }

    @Test
    public void setTimestamp() {
        blk.setTimestamp(1234);
        assertEquals(1234,blk.getTimestamp());
    }

    @Test
    public void addData() {
        blk.addData(tx1);
        assertEquals(1,blk.getNumberOfTransactions());
        tx1.getTxIn().get(0).setPublicKey("asdasf");
        System.out.println(blk.getData().get(0).getTxIn().get(0).getPublicKey());
        blk.addData(tx2);
        assertEquals(2,blk.getNumberOfTransactions());
        System.out.println("-------------------------");
    }

    @Test
    public void getData() {
        blk.addData(tx1);
        blk.addData(tx2);
        Transaction temp = blk.getData().get(1);
        System.out.println(temp.getTxIn().get(0).getPublicKey());
        temp.getTxIn().get(0).setPublicKey("jak sie masz");
        System.out.println(temp.getTxIn().get(0).getPublicKey());
        System.out.println(blk.getData().get(1).getTxIn().get(0).getPublicKey());
    }

    @Test
    public void findTransaction() {
        System.out.println(blk.getNumberOfTransactions());
        blk.addData(tx1);
        blk.addData(tx2);
        System.out.println(blk.getNumberOfTransactions());
        System.out.println(blk.findTransaction(this.tx2Hash));
        Transaction temp = blk.findTransaction(this.tx2Hash);
        assertEquals(temp.getHash(), this.tx2Hash);
    }

    @Test
    public void getPreviousHash() {
    }

    @Test
    public void setPreviousHash() {
    }

    @Test
    public void getCurrentTarget() {
    }

    @Test
    public void getTransactionWithOutCoinditionPublicKeyHash() {
        blk.addData(tx1);
        blk.addData(tx2);
        ArrayList<UnspentTransactionWallet> listUnspentTransactionWallet = this.blk.getTransactionWithOutCoinditionPublicKeyHash(this.pubKeyHash1);
        System.out.println(listUnspentTransactionWallet.size());
        System.out.println(Hash.computeHash(pubKey1));
        assertEquals(blk.getData().get(0).getHash(), listUnspentTransactionWallet.get(0).getHashOfTransaction());
    }

    @Test
    public void getTransactionWithInCoinditionPublicKey() {
        blk.addData(tx1);
        blk.addData(tx2);
        ArrayList<String> listOfTransactionHash = this.blk.getTransactionWithInCoinditionPublicKey(this.pubKey1);
        System.out.println(listOfTransactionHash.size());
        assertEquals(this.transactionHash1, listOfTransactionHash.get(0));

    }*/

}
