import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin",
                "*"
        );

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            sendResponse(
                    exchange,
                    405,
                    "Only POST method is allowed"
            );

            return;
        }

        String requestBody = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> data =
                parseFormData(requestBody);

        String email = data.get("email");
        String password = data.get("password");

        if (email == null || password == null ||
                email.isEmpty() || password.isEmpty()) {

            sendResponse(
                    exchange,
                    400,
                    "Email and password are required"
            );

            return;
        }

        UserDAO userDAO = new UserDAO();

        User user =
                userDAO.loginUser(email, password);

        if (user != null) {

            String response =
                    "Login successful|" +
                    user.getId() + "|" +
                    user.getName() + "|" +
                    user.getRole();

            sendResponse(
                    exchange,
                    200,
                    response
            );

        } else {

            sendResponse(
                    exchange,
                    401,
                    "Invalid email or password"
            );
        }
    }


    private Map<String, String> parseFormData(
            String body) {

        Map<String, String> data =
                new HashMap<>();

        String[] pairs = body.split("&");

        for (String pair : pairs) {

            String[] keyValue =
                    pair.split("=", 2);

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

        OutputStream output =
                exchange.getResponseBody();

        output.write(message.getBytes());

        output.close();
    }
}