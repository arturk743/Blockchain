public class Test {
    public static Wallet walletA;
    public static Wallet walletB;
    public static Blockchain chain;

    public static void main(String[] args) {
        walletA = new Wallet();
        walletB = new Wallet();
        chain = new Blockchain(walletA.getPublicKey());
        System.out.println(chain.getListOfUnspentTransactions().size());
        walletA.setChain(chain);
        walletB.setChain(chain);
        System.out.println("List of unspent transactions "+chain.getListOfUnspentTransactions());
        System.out.println("Client A send 20 Bitcoin to client B");
        chain.addUnprocessedTransaction(walletA.generateTransaction(20, walletB.getPublicKeyString()));
        chain.addBlock();
        System.out.println("List of unspent transactions "+chain.getListOfUnspentTransactions());
        System.out.println(chain.getListOfUnspentTransactions().size());
        walletA.setChain(chain);
        walletB.setChain(chain);
        System.out.println("Client A has now " + walletA.getTempWalletBalance());
        System.out.println("Client B has now " + walletB.getTempWalletBalance());
        System.out.println("Client B try to sent 30 Bitcoint to client A");
        if (walletB.generateTransaction(30, walletA.getPublicKeyString()) == null){
            System.out.println("Client B doesn't have enough bitcoin.");
            System.out.println("Client B sent 10 Bitcoint to client A.");
        }
        chain.addUnprocessedTransaction(walletB.generateTransaction(10, walletA.getPublicKeyString()));
        chain.addBlock();
        System.out.println("List of unspent transactions "+chain.getListOfUnspentTransactions());
        System.out.println(chain.getListOfUnspentTransactions().size());
        walletA.setChain(chain);
        walletB.setChain(chain);
        System.out.println(chain.getListOfUnprocessedTransactions());
        System.out.println("Client A has now " + walletA.getTempWalletBalance());
        System.out.println("Client B has now " + walletB.getTempWalletBalance());
        System.out.println("Client A send 80 Bitcoin to client B");
        System.out.println("Client B sent 10 Bitcoint to client A");
        chain.addUnprocessedTransaction(walletA.generateTransaction(80, walletB.getPublicKeyString()));
        chain.addUnprocessedTransaction(walletB.generateTransaction(10, walletA.getPublicKeyString()));
        chain.addBlock();
        System.out.println("List of unspent transactions "+chain.getListOfUnspentTransactions());
        System.out.println(chain.getListOfUnspentTransactions().size());
        walletA.setChain(chain);
        walletB.setChain(chain);

        System.out.println("Client A has now " + walletA.getTempWalletBalance());
        System.out.println("Client B has now " + walletB.getTempWalletBalance());

    }

}
