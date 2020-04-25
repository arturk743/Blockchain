import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ClientBitcoinGUI{
    private Wallet myWallet = new Wallet();
    private JList publicAddressesList;
    private JButton loadButton;
    private JPanel panel;
    private JButton sendMoney;
    private JTextField moneyToSendTx;
    private JTextField walletBalanceTx;
    private JTextField myPublicKeyTx;
    private JTextField selectedPublicAddressTx;
    private Set<String> listOfPublicAddresses = new TreeSet<>();
    private DefaultListModel DLM = new DefaultListModel();
    public ClientBitcoinGUI() {
        listOfPublicAddresses.add(myWallet.getPublicKeyString());
        myPublicKeyTx.setText(myWallet.getPublicKeyString());
        thread.start();
        refresh();
        sendPublicAddress();

        sendMoney.addActionListener(new ActionListener() { // create transaction and send it
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
                    Thread thread1 = new Thread(() -> {
                        Communication.sendingInformation(2,myWallet.getPublicKeyString());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    System.out.println("jak sie masz");
                        Communication.sendingInformation(1, tempTransaction);
                    });
                    thread1.start();
                    System.out.println("taktak");
                    JOptionPane.showMessageDialog(null, "Zlecenie tranzakcji zostało wysłane.");
                    refresh();
                }

            }
        });
    }
    Thread thread = new Thread(){ //receive and process packets
        public void run(){
            ArrayList list;
            while (true){
                list = Communication.receivingInformation();
                System.out.println("Typ wiadomości : " + list.get(0));
                switch ((Integer)list.get(0)){
                    case 2:

                        if (!listOfPublicAddresses.contains(list.get(1))){
                            DLM.addElement(list.get(1));
                            listOfPublicAddresses.add((String) list.get(1));
                            publicAddressesList.setModel(DLM);
                        }
                        break;
                    case 3:
                        myWallet.setChain((Blockchain) list.get(1));
                        refresh();
                    default:
                }
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public void refresh(){
        walletBalanceTx.setText(Integer.toString(myWallet.getTempWalletBalance()));
    }
    public void sendPublicAddress(){
        Communication.sendingInformation(2,myWallet.getPublicKeyString());

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientBitcoinGUI");
        frame.setContentPane(new ClientBitcoinGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1070, 300);
        frame.setVisible(true);
    }
}
