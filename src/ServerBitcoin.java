import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;

public class ServerBitcoin {

    public ServerBitcoin() {

    }

    public void createAndListenSocket() {
        try {
            MulticastSocket socket = new MulticastSocket(4321);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            byte[] incomingData = new byte[1024];

            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                socket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                try {
                    int type = (Integer) is.readObject();
                    System.out.println(type);
                    Transaction myWallet = (Transaction) is.readObject();
                    System.out.println("Student object received = "+myWallet.getTxIn().get(0).getPublicKeyString());
                    //System.out.println("Student object received = "+myWallet.getTxIn().get(0).getPublicKey());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                InetAddress IPAddress = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
                String reply = "Thank you for the message";
                byte[] replyBytea = reply.getBytes();
                DatagramPacket replyPacket =
                        new DatagramPacket(replyBytea, replyBytea.length, IPAddress, port);
                socket.send(replyPacket);
                //Thread.sleep(2000);
                //System.exit(0);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } //catch (InterruptedException e) {
            //e.printStackTrace();
        //}
    }
    public static void main(String[] args) {
        /*ServerBitcoin server1 = new ServerBitcoin();

        Thread thread1 = new Thread(){
            public void run(){
                server1.createAndListenSocket();
            }
        };


        thread1.start();*/
        Thread thread = new Thread(){
            public void run(){
                ArrayList list;
                while (true){
                    list = Communication.receivingInformation();
                    switch ((Integer)list.get(0)){
                        case 1:
                            System.out.println(list.get(1));
                            break;
                        case 2:
                            System.out.println(list.get(1));
                        case 3:
                            System.out.println(list.get(1));
                            default:
                    }
                }
            }
        };
        Thread thread2 = new Thread(){
            public void run(){
                ArrayList list;
                while (true){
                    list = Communication.receivingInformation();
                    switch ((Integer)list.get(0)){
                        case 1:
                            System.out.println(list.get(1));
                            break;
                        case 2:
                            System.out.println(list.get(1));
                        case 3:
                            System.out.println(list.get(1));
                        default:
                    }
                }
            }
        };

        thread.start();
        thread2.start();
    }
}
