import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        // Test endpoint
        server.createContext("/api/hello", exchange -> {

            String response =
                    "Equipment Rental API is running!";

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            OutputStream output =
                    exchange.getResponseBody();

            output.write(response.getBytes());

            output.close();
        });

        // Registration endpoint
        server.createContext(
                "/api/register",
                new RegisterHandler()
        );
        
        //login endpoint
        server.createContext(
        "/api/login",
        new LoginHandler()
        );

        server.createContext(
        "/api/equipment",
        new EquipmentHandler()
        );

        server.createContext(
        "/api/rent",
        new RentalHandler()
        );

        server.createContext(
        "/api/reviews",
        new ReviewHandler()
        );

        server.createContext(
        "/api/rental-tracking",
        new RentalTrackingHandler()
        );

        server.start();

        System.out.println(
                "Server started on port 8080"
        );
    }
}