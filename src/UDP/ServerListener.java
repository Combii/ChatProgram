package UDP; /**
 * Created by David Stovlbaek
 * 16 February 2017.
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ServerListener implements Runnable{

                        //Ip and port
    private HashMap<InetAddress,Integer> users = new HashMap<>();

    @Override
    public void run() {
        try{
        /** Destination ports and ip */
        int port = 1234;
        //InetAddress ip = InetAddress.getByName("localhost");

        DatagramSocket socket = new DatagramSocket(port);
            
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
            socket.receive(request);

            if(!users.containsKey(request.getAddress())) {
                users.put(request.getAddress(), request.getPort());
            }

            for (Map.Entry<InetAddress, Integer> entry : users.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }

            System.out.println("Message received!");

            String text = printData(request);
            System.out.println(text);

            RunProgram.getController().textReceiving.setText(text);
        }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String printData(DatagramPacket request) throws Exception
    {
        // Obtain references to the packet's array of bytes.
        byte[] buf = request.getData();
        // Wrap the bytes in a byte array input stream,
        // so that you can read the data as a stream of bytes.
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        // Wrap the byte array output stream in an input stream reader,
        // so you can read the data as a stream of characters.
        InputStreamReader isr = new InputStreamReader(bais);
        // Wrap the input stream reader in a bufferred reader,
        // so you can read the character data a line at a time.
        // (A line is a sequence of chars terminated by any combination of \r and \n.)
        BufferedReader br = new BufferedReader(isr);
        // The message data is contained in a single line, so read this line.
        String line = br.readLine();
        // Print host address and data received from it.

        /*System.out.println(
                "Received from " +
                        request.getAddress().getHostAddress() +
                        ": " +
                        new String(line) );
        */
        return line;
    }


}











   /*
            InetAddress clientHost = request.getAddress();
            int clientPort = request.getPort();

            String msgBack = "Hello back to you!";

            DatagramPacket reply = new DatagramPacket(msgBack.getBytes(), msgBack.length(), clientHost, clientPort);
            socket.send(reply);

            System.out.println(" Reply sent.");
     */