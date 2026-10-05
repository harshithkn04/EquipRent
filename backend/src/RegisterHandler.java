import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Allow requests from our frontend
        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin", "*"
        );

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            String response = "Only POST method is allowed";

            exchange.sendResponseHeaders(405, response.length());

            OutputStream output = exchange.getResponseBody();
            output.write(response.getBytes());
            output.close();

            return;
        }

        // Read data sent by the frontend
        String requestBody = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> data = parseFormData(requestBody);

        String name = data.get("name");
        String email = data.get("email");
        String password = data.get("password");
        String role = data.get("role");

        // Basic validation
        if (name == null || email == null ||
                password == null || role == null ||
                name.isEmpty() || email.isEmpty() ||
                password.isEmpty() || role.isEmpty()) {

            sendResponse(
                    exchange,
                    400,
                    "All fields are required"
            );

            return;
        }

        User user = new User(
                name,
                email,
                password,
                role
        );

        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.registerUser(user);

        if (success) {

            sendResponse(
                    exchange,
                    201,
                    "Registration successful"
            );

        } else {

            sendResponse(
                    exchange,
                    500,
                    "Registration failed"
            );
        }
    }

    private Map<String, String> parseFormData(String body) {

        Map<String, String> data = new HashMap<>();

        String[] pairs = body.split("&");

        for (String pair : pairs) {

            String[] keyValue = pair.split("=", 2);

            if (keyValue.length == 2) {

                String key = URLDecoder.decode(
                        keyValue[0],
                        StandardCharsets.UTF_8
                );

                String value = URLDecoder.decode(
                        keyValue[1],
                        StandardCharsets.UTF_8
                );

                data.put(key, value);
            }
        }

        return data;
    }

    private void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String message
    ) throws IOException {

        exchange.getResponseHeaders().add(
                "Content-Type",
                "text/plain"
        );

        exchange.sendResponseHeaders(
                statusCode,
                message.getBytes().length
        );

        OutputStream output = exchange.getResponseBody();

        output.write(message.getBytes());

        output.close();
    }
}