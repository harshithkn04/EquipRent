import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RentalHandler implements HttpHandler {

    private RentalDAO rentalDAO =
            new RentalDAO();

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin",
                "*"
        );

        if (!exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {

            sendResponse(
                    exchange,
                    405,
                    "Only POST method is allowed"
            );

            return;
        }

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> data =
                parseFormData(body);

        try {

            int equipmentId =
                    Integer.parseInt(
                            data.get("equipmentId")
                    );

            int renterId =
                    Integer.parseInt(
                            data.get("renterId")
                    );

            int quantity =
                    Integer.parseInt(
                            data.get("quantity")
                    );

            String result =
                    rentalDAO.rentEquipment(
                            equipmentId,
                            renterId,
                            quantity
                    );

            if (result.equals("Rental successful")) {

                sendResponse(
                        exchange,
                        201,
                        result
                );

            } else {

                sendResponse(
                        exchange,
                        400,
                        result
                );
            }

        } catch (Exception e) {

            sendResponse(
                    exchange,
                    400,
                    "Invalid rental data"
            );
        }
    }


    private Map<String, String> parseFormData(
            String body) {

        Map<String, String> data =
                new HashMap<>();

        String[] pairs =
                body.split("&");

        for (String pair : pairs) {

            String[] keyValue =
                    pair.split("=", 2);

            if (keyValue.length == 2) {

                String key =
                        URLDecoder.decode(
                                keyValue[0],
                                StandardCharsets.UTF_8
                        );

                String value =
                        URLDecoder.decode(
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
            String message)
            throws IOException {

        byte[] bytes =
                message.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.getResponseHeaders().add(
                "Content-Type",
                "text/plain"
        );

        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );

        OutputStream output =
                exchange.getResponseBody();

        output.write(bytes);
        output.close();
    }
}