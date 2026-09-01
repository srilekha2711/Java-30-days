import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class Server {

    static List<PrintWriter> clients =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("=================================");
        System.out.println("       CHAT SERVER STARTED");
        System.out.println("=================================");
        System.out.println("Waiting for clients...\n");

        while (true) {

            Socket socket = serverSocket.accept();

            System.out.println("Client connected!");

            Thread thread = new Thread(() -> handleClient(socket));

            thread.start();
        }
    }

    public static void handleClient(Socket socket) {

        try {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );

            PrintWriter writer = new PrintWriter(
                    socket.getOutputStream(),
                    true
            );

            // Add this client to the list
            clients.add(writer);

            String message;

            while ((message = reader.readLine()) != null) {

                System.out.println("Client says: " + message);

                // Send message to EVERY client
                synchronized (clients) {

                    for (PrintWriter client : clients) {

                        client.println(message);
                    }
                }

                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            clients.remove(writer);
            socket.close();

            System.out.println("Client disconnected.");

        } catch (IOException e) {

            System.out.println("Client disconnected.");

        }
    }
}