import java.net.Socket;
import java.io.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);

        System.out.println("=================================");
        System.out.println("       CONNECTED TO SERVER");
        System.out.println("=================================");

        Scanner sc = new Scanner(System.in);

        PrintWriter writer = new PrintWriter(
                socket.getOutputStream(),
                true
        );

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );

        // Thread for receiving messages
        Thread receiver = new Thread(() -> {

            try {

                String message;

                while ((message = reader.readLine()) != null) {

                    System.out.println("\n" + message);
                    System.out.print("You: ");
                }

            } catch (IOException e) {

                System.out.println("Disconnected from server.");
            }
        });

        receiver.start();

        // Main thread sends messages
        while (true) {

            System.out.print("You: ");

            String message = sc.nextLine();

            writer.println(message);

            if (message.equalsIgnoreCase("bye")) {
                break;
            }
        }

        socket.close();
        sc.close();
    }
}