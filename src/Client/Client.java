package Client;

import java.net.*;

/**
 * Created by David Stovlbaek
 * 21 February 2017.
 */
public class Client {

    private String username;
    private int serverPort;
    private InetAddress serverIP;


    public Client(String username, String serverIp, int serverPort) throws UnknownHostException, SocketException {
        this.username = username;
        this.serverIP = InetAddress.getByName(serverIp);
        this.serverPort = serverPort;
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", serverPort=" + serverPort +
                ", serverIP=" + serverIP +
                '}';
    }

}
