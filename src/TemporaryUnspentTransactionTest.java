import org.junit.Test;

import static org.junit.Assert.*;


public class TemporaryUnspentTransactionTest {
    public UnspentTransaction unTx = new UnspentTransaction("asdawdasd",true, true);
    public TemporaryUnspentTransaction tempUnTx = new TemporaryUnspentTransaction(unTx, 4);

    @Test
    public void getIndex() {
        assertEquals(4, tempUnTx.getIndex());
    }

    @Test
    public void setIndex() {
        assertEquals(4, tempUnTx.getIndex());
        tempUnTx.setIndex(6);
        assertEquals(6, tempUnTx.getIndex());

    }

    @Test
    public void usnpendTx() {
        UnspentTransaction returnedTempUnTx = tempUnTx.getUnspentTx();
        assertEquals("asdawdasd",returnedTempUnTx.getHashOfTransaction());
        assertEquals(true, returnedTempUnTx.isOut(0));
        assertEquals(true, returnedTempUnTx.isOut(1));
    }
}
