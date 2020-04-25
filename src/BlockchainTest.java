import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

import static org.junit.Assert.*;

public class BlockchainTest {
    public KeyPair kyes0 = Wallet.generateKeyPair2();
    public PublicKey pubKey1 = Wallet.generateKeyPair2().getPublic();
    public PublicKey pubKey2 = Wallet.generateKeyPair2().getPublic();
    public PublicKey pubKey3 = Wallet.generateKeyPair2().getPublic();
    public  Blockchain chain = new Blockchain(kyes0.getPublic());
    public Wallet myWallet = new Wallet();
    public Transaction temp;
    private Base64.Encoder encoder = Base64.getEncoder();


    public BlockchainTest(){
        TransactionInCondition txIn0 = new TransactionInCondition(chain.getLatestBlock().getData().get(0).getHash(),0, kyes0.getPublic());
        //TransactionOutCondition txOut00 = new TransactionOutCondition(25,encoder.encodeToString(this.pubKey1.getEncoded()));
        //TransactionOutCondition txOut01 = new TransactionOutCondition(25,encoder.encodeToString(this.pubKey0.getEncoded()));
        myWallet.setPublicKey(kyes0.getPublic());
        myWallet.setPrivateKey(kyes0.getPrivate());
        myWallet.setChain(chain);
    }


    @Test
    public void addBlock() {
        temp = myWallet.generateTransaction(25, encoder.encodeToString(this.pubKey1.getEncoded()));
        chain.addUnprocessedTransaction(temp);
        assertEquals(1,chain.getListOfUnprocessedTransactions().size());
        chain.addBlock();
        System.out.println("------------------");
        assertEquals(0,chain.getListOfUnprocessedTransactions().size());
        assertEquals(2, chain.getChainSize());
        assertEquals(temp.getHash(), chain.getLatestBlock().getData().get(1).getHash());
        System.out.println("------------------");
        chain.displayChain();
    }

    @Test
    public void verifyTransaction() {
        temp = myWallet.generateTransaction(25, encoder.encodeToString(this.pubKey1.getEncoded()));
        String trueHash = temp.getTxIn().get(0).getHash();
        temp.getTxIn().get(0).setHash("gdfk6l54g");
        assertEquals(false, chain.verifyTransaction(temp));
        temp.getTxIn().get(0).setHash(trueHash);
        assertEquals(true, chain.verifyTransaction(temp));
        assertEquals(null, myWallet.generateTransaction(25, encoder.encodeToString(this.pubKey1.getEncoded())));

    }

}
