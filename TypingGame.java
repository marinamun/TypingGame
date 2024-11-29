import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
public class TypingGame {
    public static void main(String[] args)throws Exception {
        // Start a dummy HTTP server to satisfy Render's port requirement
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> {
            String response = "Typing Game is running!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        });
        server.start();

        System.out.println("Dummy HTTP server running on port 8080");
        System.out.println("Welcome to the Typing Game!");

        JFrame frame = new JFrame("Typing game");
        frame.setSize(1000,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        GameLogic gameLogic = new GameLogic();
        frame.add(gameLogic);
    }
}