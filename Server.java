import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Kiki
 * @author kingkev
 */

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        int count = 0;
        while (greeting.equals("sensor")) {
            System.out.println("Success");
            if ("sensor".equals(greeting) && count < 100) {
                count++;
                out.println("1");
            } else {
                out.println("unrecognised greeting");
            }
            greeting = in.readLine();
            while (true) {
                out.println("1");
                String input = in.readLine();
                if (input == null) {
                    break;
                }
                System.out.println(input);
            }
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        Server server = new Server();
        server.start(8080);

        /* int temp = query_state( ); */

        server.stop();
    }

}