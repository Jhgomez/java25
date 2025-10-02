package java25;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static void main(String[] args) throws IOException, InterruptedException {
        Thread.startVirtualThread(() -> {
            try {
                Server.main();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread.sleep(5000);

        try (Socket socket = new Socket("localhost", 12346)) {

            // Setting up input and output streams
            var out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to handle incoming messages
            Thread.startVirtualThread(() -> {
                try {
                    var message = "";
                    while ((message = in.readLine()) != null) {
                        System.out.println("\nS- " + message);
                        System.out.print("c> ");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // Read messages from the console and send to the server
            Scanner scanner = new Scanner(System.in);
            String userInput = "";
            while (true) {
//                System.out.print("Waiting for client input: ");
                System.out.print("c> ");

                userInput = scanner.nextLine();

                out.println(userInput);

//                System.out.println("Input sent to server: " + userInput);
            }
        }
    }
}