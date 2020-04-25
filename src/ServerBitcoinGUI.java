import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ServerBitcoinGUI {
    private JTextField walletBalanceTx;
    private JList publicAddressesList;
    private JButton addNextBlockButton;
    private JButton sendButton;
    private JTextField moneyToSendTx;
    private JPanel panel;
    private JTextField myPublicKeyTx;
    private Wallet myWallet = new Wallet();
    private TreeSet<String> listOfPublicAddresses = new TreeSet<>(); //public addresses of other clients
    private DefaultListModel DLM = new DefaultListModel();
    private Blockchain chain = new Blockchain(myWallet.getPublicKey());

    public ServerBitcoinGUI() {
        listOfPublicAddresses.add(myWallet.getPublicKeyString());
        myPublicKeyTx.setText(myWallet.getPublicKeyString());
        thread.start();
        myWallet.setChain(chain);
        refresh();
        sendPublicAddress();
        sendButton.addActionListener(new ActionListener() { // create transaction
            @Override
            public void actionPerformed(ActionEvent e) {
                int iSelected = publicAddressesList.getSelectedIndex();
                if(iSelected == -1){
                    JOptionPane.showMessageDialog(null, "Proszę wybrać adresata.");
                }
                Transaction tempTransaction = myWallet.generateTransaction(Integer.parseInt(moneyToSendTx.getText()),publicAddressesList.getSelectedValue().toString());
                if (tempTransaction == null){
                    JOptionPane.showMessageDialog(null, "Błąd. Tranzakcja nie została utworzona.");
                }else{
                    Communication.sendingInformation(2,myWallet.getPublicKeyString());
                    chain.addUnprocessedTransaction(tempTransaction);
                    JOptionPane.showMessageDialog(null, "Zlecenie tranzakcji zostało wysłane.");
                    refresh();
                }

            }
        });
        addNextBlockButton.addActionListener(new ActionListener() {//add new block to blokchain and sent it
            @Override
            public void actionPerformed(ActionEvent e) {
                chain.addBlock();
                Communication.sendingInformation(3,chain);
                myWallet.setChain(chain);
                refresh();
            }
        });
    }

    Thread thread = new Thread(){
        public void run(){ //receive and process packets
            ArrayList list;
            while (true){
                list = Communication.receivingInformation();
                System.out.println("Typ wiadomości : " + list.get(0));
                switch ((Integer)list.get(0)){
                    case 1:
                        System.out.println("Odebrano nową tranzakcję.");
                        chain.addUnprocessedTransaction((Transaction) list.get(1));
                        break;
                    case 2:

                        if (!listOfPublicAddresses.contains(list.get(1))){
                            DLM.addElement(list.get(1));
                            listOfPublicAddresses.add((String) list.get(1));
                            publicAddressesList.setModel(DLM);
                        }
                        break;
                    default:
                        //
                }
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private void refresh() {
        walletBalanceTx.setText(Integer.toString(myWallet.getTempWalletBalance()));
    }
    public void sendPublicAddress(){
        Communication.sendingInformation(2,myWallet.getPublicKeyString());

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ServerBitcoinGUI");
        frame.setContentPane(new ServerBitcoinGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1070, 300);
        frame.setVisible(true);
    }
}
