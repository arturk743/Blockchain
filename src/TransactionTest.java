import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class TransactionTest {
    /*public Transaction tx = new Transaction();
    public String pubKey1 = "hello world";
    public String pubKeyHash1 = "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9";
    public TransactionInCondition txIn = new TransactionInCondition("asfadfgsdfhsdfhs", 0, this.pubKey1);


    /*@Test
    public void getHash() {
    }*/

    /*@Test
    public void getVersion() {
        assertEquals("00001", tx.getVersion());
    }

    @Test
    public void getTxInCount() {
        assertEquals(0,tx.getTxInCount());
        tx.addTxIn(this.txIn);
        assertEquals(1,tx.getTxInCount());
    }

    @Test
    public void getTxIn() { // tutaj też addTxIn
        tx.addTxIn(this.txIn);
        TransactionInCondition temp = new TransactionInCondition(tx.getTxIn().get(0).getHash(),tx.getTxIn().get(0).getIndex(), tx.getTxIn().get(0).getPublicKey());
        assertEquals("asfadfgsdfhsdfhs", temp.getHash());
        assertEquals(0, temp.getIndex());
        assertEquals(this.pubKey1, temp.getPublicKey());
        assertEquals("", temp.getSignature());
    }


    @Test
    public void getTxOutCount() {
        TransactionOutCondition txOut = new TransactionOutCondition(50, this.pubKey1);
        assertEquals(0,tx.getTxOutCount());
        tx.addTxOut(txOut);
        assertEquals(1,tx.getTxOutCount());
    }

    @Test
    public void getTxOut() { // tutaj tez addTxOut
        tx.addTxOut(new TransactionOutCondition(50, this.pubKey1));
        TransactionOutCondition temp = tx.getTxOut().get(0);
        assertEquals(this.pubKeyHash1,temp.getHashOfPublicKeyOfReciepient());
        assertEquals(50, temp.getValue());
    }

    @Test
    public void getOutValue() {
        tx.addTxOut(new TransactionOutCondition(50, this.pubKey1));
        assertEquals(50, tx.getOutValue());
        tx.addTxOut(new TransactionOutCondition(50, this.pubKey1));
        assertEquals(100, tx.getOutValue());
    }

    @Test
    public void getOutValue1() {
        tx.addTxOut(new TransactionOutCondition(50, this.pubKey1));
        assertEquals(50, tx.getOutValue(0));
        tx.addTxOut(new TransactionOutCondition(60, this.pubKey1));
        assertEquals(60, tx.getOutValue(1));
    }*/
}
