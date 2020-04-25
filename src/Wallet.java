import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;


public class Wallet implements Serializable {
    private Blockchain chain;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private int walletBalance = 0;
    private int tempWalletBalance = 0;
    private ArrayList<UnspentTransactionWallet> listOfUnspentTransaction = new ArrayList<UnspentTransactionWallet>();
    private ArrayList<UnspentTransactionWallet> tempListOfUnspentTransaction = new ArrayList<UnspentTransactionWallet>();

    public Wallet() {
        this.generateKeyPair();
    }


    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(512);
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static KeyPair generateKeyPair2() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(512);
            KeyPair keyPair = keyGen.generateKeyPair();
            return keyPair;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] generateSignature(String input) {
        try {
            byte[] data = input.getBytes("UTF8");
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initSign(this.privateKey);
            sig.update(data);
            byte[] signatureBytes = sig.sign();
            return signatureBytes;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifySignature(String input, byte[] sigToVerify, PublicKey publicKey) {
        try {
            byte[] data = input.getBytes("UTF8");
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initVerify(publicKey);
            sig.update(data);
            boolean verifies = sig.verify(sigToVerify);
            return verifies;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Transaction generateTransaction(int value, String publicKeyOfRecipient) {
        Transaction tx = new Transaction();
        TransactionInCondition txIn;
        Transaction temp;
        String myPublicKeyString = Base64Encoder.publicKeyToStringBase62(this.publicKey);
        System.out.println("blad0");
        if (this.tempWalletBalance < value) {
            return null;
        }
        int sumValueOfTransaction = 0;
        while (sumValueOfTransaction < value) {
            temp = chain.findTransaction(tempListOfUnspentTransaction.get(0).getHashOfTransaction());
            System.out.println("blad1");
            if (temp == null) {
                return null;
            }
            System.out.println("blad2");
            txIn = new TransactionInCondition(temp.getHash(), tempListOfUnspentTransaction.get(0).getIndex(), this.publicKey);
            txIn.setSignature(this.generateSignature(temp.getHash()));
            if (Wallet.verifySignature(temp.getHash(),txIn.getSignature(),txIn.getPublicKey())){
                System.out.println("----------------------");
                System.out.println("Podpis został zweryfikowany.");
                System.out.println("----------------------");
            }
            tx.addTxIn(txIn);
            sumValueOfTransaction += temp.getTxOut().get(tempListOfUnspentTransaction.get(0).getIndex()).getValue();
            this.tempWalletBalance -= temp.getTxOut().get(tempListOfUnspentTransaction.get(0).getIndex()).getValue();
            tempListOfUnspentTransaction.remove(0);

        }
        TransactionOutCondition txOut;
        //System.out.println("Recipient's public key : " + publicKeyOfRecipient);
        //System.out.println("Recipient's public key hash : " + Hash.computeHash(publicKeyOfRecipient));
        txOut = new TransactionOutCondition(value, publicKeyOfRecipient);
        //System.out.println("Hash of recipient's public key : " + txOut.publicKeyOfRecipient());
        tx.addTxOut(txOut);
        if (sumValueOfTransaction > value) {
            System.out.println("tak");
            txOut = new TransactionOutCondition(sumValueOfTransaction - value, myPublicKeyString);
            tx.addTxOut(txOut);
        }
        tx.computeTransactionHash();
        System.out.println(tx.getHash());

        return tx;
    }

    public void generateListOfUnspentTransaction(){
        this.listOfUnspentTransaction = new ArrayList<UnspentTransactionWallet>();
        this.tempListOfUnspentTransaction = new ArrayList<UnspentTransactionWallet>();
        String myPublicKeyHash = Hash.computeHash( Base64Encoder.publicKeyToStringBase62(this.publicKey) );
        //System.out.println(encoder.encodeToString(this.publicKey.getEncoded()));
        //System.out.println(myPublicKeyHash);
        ArrayList<UnspentTransactionWallet> temp;
        ArrayList<String> tempHashOfTransactions = new ArrayList<String>();
        System.out.println("Rozmiar blockchainu " + chain.getChainSize());
        for (int i = 0; i < chain.getChainSize(); i++){

            temp = chain.getBlock(i).getTransactionWithOutCoinditionPublicKeyHash(myPublicKeyHash);
            listOfUnspentTransaction.addAll(temp);
            tempHashOfTransactions.addAll(chain.getBlock(i).getTransactionWithInCoinditionPublicKey(Base64Encoder.publicKeyToStringBase62(this.publicKey)));
        }
        System.out.println("Lista unspent tranzakcji przed odjęciem " +this.listOfUnspentTransaction.size());
        System.out.println("Ilosc tranzakcji do odjęcia "+tempHashOfTransactions.size());
        int index = 0;
        for(int k = 0; k < listOfUnspentTransaction.size(); k++){
            if (tempHashOfTransactions.contains(listOfUnspentTransaction.get(index).getHashOfTransaction())){
                listOfUnspentTransaction.remove(index);
            }else{
                index += 1;
            }

        }
        System.out.println("Lista unspent tranzakcji po odjęciu " +this.listOfUnspentTransaction.size());
        System.out.println("Ilosc tranzakcji do odjęcia po odcięciu "+tempHashOfTransactions.size());

        this.tempListOfUnspentTransaction = new ArrayList<UnspentTransactionWallet>();
        this.tempListOfUnspentTransaction.addAll(this.listOfUnspentTransaction);
        this.walletBalance = this.setTempBalance();
        this.tempWalletBalance = this.walletBalance;
        System.out.println("-------------------");
        System.out.println(tempListOfUnspentTransaction);
        System.out.println("-------------------");
    }

    public int setTempBalance(){
        int balance = 0;
        for(int i = 0; i < this.tempListOfUnspentTransaction.size(); i++){
            balance += this.tempListOfUnspentTransaction.get(i).getValue();
        }
        return balance;
    }

    public void setChain(Blockchain chain){
        this.chain = new Blockchain(chain);
        this.generateListOfUnspentTransaction();
    }

    public String getPublicKeyString(){
        return Base64Encoder.publicKeyToStringBase62(this.publicKey);
    }

    public void setPublicKey(PublicKey publicKey){
        this.publicKey = publicKey;
    }

    public ArrayList<UnspentTransactionWallet> getTempListOfUnspentTransaction() {
        return tempListOfUnspentTransaction;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public int getTempWalletBalance() {
        return this.tempWalletBalance;
    }
}
