package Server;

import java.net.InetAddress;

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
