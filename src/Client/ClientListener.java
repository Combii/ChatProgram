package Client;

import com.sun.tools.javac.comp.Enter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by David Stovlbaek
 * 21 February 2017.
 */
public class ClientListener implements Runnable {

   static DatagramSocket socket;

    public ClientListener() throws SocketException {
        socket = new DatagramSocket();
    }

    @Override
    public void run() {
        try{
            while (true) {
                System.out.println("Socket PORT: " + socket.getLocalPort());
                DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                socket.receive(request);

                System.out.println("Message received!");

                String text = printData(request);
                System.out.println(text);

                EnterUsernameController.getController().chatBox.appendText(text + "\n");
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
