import static org.junit.Assert.*;

public class UnspentTransactionTest {
    @org.junit.Test
    public void isOut() {
        UnspentTransaction unTx = new UnspentTransaction("88219083hajhk2j3h4knbkj34h", true, false);

        assertEquals(true, unTx.isOut(0));
        assertEquals(false, unTx.isOut(1));
    }

    @org.junit.Test
    public void isSpent() {
        UnspentTransaction unTx1 = new UnspentTransaction("88219083hajhk2j3h4knbkj34h", true, false);
        UnspentTransaction unTx2 = new UnspentTransaction("88219083hajhk2asfdsfsdf34h", false, false);

        assertEquals(false, unTx1.isSpent());
        assertEquals(true, unTx2.isSpent());
    }

    @org.junit.Test
    public void getHashOfTransaction() {
        UnspentTransaction unTx1 = new UnspentTransaction("88219083hajhk2j3h4knbkj34h", true, false);

        assertEquals("88219083hajhk2j3h4knbkj34h",unTx1.getHashOfTransaction());
    }

    @org.junit.Test
    public void changeOut() {
        UnspentTransaction unTx1 = new UnspentTransaction("88219083hajhk2j3h4knbkj34h", true, true);

        assertEquals(true,unTx1.isOut(0) );
        unTx1.changeOut(0);
        assertEquals(false,unTx1.isOut(0) );

        assertEquals(true,unTx1.isOut(1) );
        unTx1.changeOut(1);
        assertEquals(false,unTx1.isOut(1) );
    }
}
