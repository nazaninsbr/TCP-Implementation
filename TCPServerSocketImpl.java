import java.io.*;
import java.net.*;

public class TCPServerSocketImpl extends TCPServerSocket {
    public int someOneIsConnected; 
    public EnhancedDatagramSocket socket;
    int seq_No;
    public TCPServerSocketImpl(int port) throws Exception {
        super(port);
        this.someOneIsConnected = 0;
        this.socket = new EnhancedDatagramSocket(port);
        this.seq_No = 0;
        this.ack_No = 0;
    }

    @Override
    public TCPSocket accept() throws Exception {
        // throw new RuntimeException("Not implemented!");
        //needs to have handshaking
        int port;
        while(this.someOneIsConnected==0){
            System.out.println("Listening");
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
            this.socket.receive(receivePacket1);
            //this.seq_No +=1;
            String sentence = new String(receivePacket1.getData());
            System.out.println("RECEIVED: " + sentence);
            InetAddress IPAddress = receivePacket1.getAddress();
            port = receivePacket1.getPort();
            String[] splited = sentence.split("\\s+");
            new_seq_No = Integer.parseInt(splited[1]);
            if(splited[0]=="SYN")
            {
                this.ack_No=new_seq_No+1;
                String ackNoString = Integer.toString(this.ack_No);
                String seqNoString = Integer.toString(this.seq_No);
                String sentence_for_send = "SYN-ACK" + " "+seqNoString+" "+ackNoString;
                sendData = sentence_for_send.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                this.socket.send(sendPacket);

            }

            //**************/
            DatagramPacket receivePacket2 = new DatagramPacket(receiveData, receiveData.length);
            this.socket.receive(receivePacket2);
            String sentence = new String(receivePacket2.getData());
            System.out.println("RECEIVED: " + sentence);
            String[] splited = sentence.split("\\s+");

            if(split[0]=="ACK")

            {
                this.seq_No = Integer.parseInt(splited[1])+1;
                this.ack_No = Integer.parseInt(splited[2])+1;

            }
            someOneIsConnected = 0;
        }
        EnhancedDatagramSocket tcp_server_socket = new EnhancedDatagramSocket(port);
    }

    @Override
    public void close() throws Exception {
        // throw new RuntimeException("Not implemented!");
        this.socket.close();
    }
}
