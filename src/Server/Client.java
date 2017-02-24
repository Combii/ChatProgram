package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
public class Client {

    private String username;
    private InetAddress ip;
    private int port;

    public Client(InetAddress ip, int port, String username) {

            this.ip = ip;
            this.port = port;
            this.username = username;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort () {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public InetAddress getIp() {
        return ip;
    }




    @Override
    public String toString() {
        return "Client{" +
                ", username='" + username + '\'' +
                ", ip=" + ip +
                ", port=" + port +
                '}';
    }
}