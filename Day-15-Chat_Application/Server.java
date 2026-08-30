import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Server started...");
        System.out.println("Waiting for client...");

        while (true) {
            Socket socket = serverSocket.accept();

            System.out.println("Client connected!");
        }
    }
}