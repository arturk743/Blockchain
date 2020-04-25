import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class TransactionOutConditionTest {
    public String pubKey = "hello world";
    public String pubKeyHash = "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9";
    public TransactionOutCondition txOut = new TransactionOutCondition(50, pubKey);

    @Test
    public void getHashOfPublicKeyOfRecipient() {
        assertEquals(pubKeyHash, txOut.getHashOfPublicKeyOfRecipient());
    }

    @Test
    public void getValue() {
        assertEquals(50, txOut.getValue());
    }
}
