import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Communication {
    private static DatagramSocket Socket;

    public Communication(){
        //
    }
    public static void sendingInformation(int type, Object obj){// type 1 - Transaction, type 2 - public address, type 3 - blockchain
        try {

            Socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName("230.0.0.0");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            System.out.println("Przygotowuje obiekt");
            switch(type){
                case 1:
                    Transaction transaction = (Transaction) obj;
                    os.writeObject(type);
                    os.writeObject(transaction);
                    break;
                case 2:
                    String publicKey = (String) obj;
                    os.writeObject(type);
                    os.writeObject(publicKey);
                    break;
                case 3:
                    Blockchain blockchain = (Blockchain) obj;
                    os.writeObject(type);
                    os.writeObject(blockchain);
                    break;
                    default:

            }
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, group, 4321);
            Socket.send(sendPacket);
            System.out.println("Wysłałem obiekt");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList receivingInformation(){
            ArrayList temp = new ArrayList<>();
            try {
                MulticastSocket socket = new MulticastSocket(4321);
                InetAddress group = InetAddress.getByName("230.0.0.0");
                socket.joinGroup(group);
                byte[] incomingData = new byte[30000];

                System.out.println("Czekam na pakiet");
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                socket.receive(incomingPacket);
                System.out.println("Odebrałem pakiet");
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                try {
                    int type = (Integer) is.readObject();

                    switch (type) {
                        case 1:
                            Transaction transaction = (Transaction) is.readObject();
                            temp.add(type);
                            temp.add(transaction);

                            break;
                        case 2:
                            String publicKey = (String) is.readObject();
                            temp.add(type);
                            temp.add(publicKey);
                            break;
                        case 3:
                            Blockchain blockchain = (Blockchain) is.readObject();
                            temp.add(type);
                            temp.add(blockchain);
                            break;
                        default:
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return temp;
        }

}
