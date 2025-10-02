package java25;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final List<PrintWriter> writters = new ArrayList<>();
    private static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private static final Deque<String> messagesToClients = new ArrayDeque<>();

    static void main(String... args) throws IOException {
        try (var server = new ServerSocket( 12346)) {

            executor.execute(() -> {
                var scanner = new Scanner(System.in);

                while (!server.isClosed()) {
                    System.out.print("s> ");

                    var userInput = scanner.nextLine();
                    messagesToClients.offer(userInput);

                    executor.execute(() -> {
                        broadcastMessages();
                    });
                }
                System.out.println("server is closed");
            });

            while (!server.isClosed()) {
                Socket client = server.accept();

                executor.submit(() -> {
                    try {
                        var out = new PrintWriter(new BufferedOutputStream(client.getOutputStream()), true);
                        writters.add(out);

                        var in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                        var message = "";

//                        System.out.println("Client connected");

                        executor.execute(() -> {
                            broadcastMessages();
                        });

                        while ((message = in.readLine()) != null) {
//                            System.out.println("Message received from client: " + message);
                            System.out.println("\nC- " + message);
                            System.out.print("s> ");
                        }

//                        System.out.println("Client disconnected");
                        writters.remove(out);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    private static void broadcastMessages() {
        while(!messagesToClients.isEmpty() && !writters.isEmpty()) {
//            System.out.println(messagesToClients);
            var message = messagesToClients.poll();
//            System.out.println("polling message " + message);

            for (var out : writters) {
                out.println(message);
//                System.out.println("Message sent to client ");
            }
        }
    }
}
