import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalTrackingHandler implements HttpHandler {

    private RentalTrackingDAO dao =
            new RentalTrackingDAO();

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin", "*"
        );

        String method =
                exchange.getRequestMethod();

        if (method.equalsIgnoreCase("GET")) {

            getRentals(exchange);

        } else if (method.equalsIgnoreCase("POST")) {

            updateRental(exchange);

        } else {

            sendResponse(
                    exchange,
                    405,
                    "Method not allowed"
            );
        }
    }


    private void getRentals(
            HttpExchange exchange)
            throws IOException {

        String query =
                exchange.getRequestURI()
                        .getRawQuery();

        Map<String, String> params =
                parseFormData(
                        query == null ? "" : query
                );

        try {

            int ownerId =
                    Integer.parseInt(
                            params.get("ownerId")
                    );

            List<String[]> rentals =
                    dao.getOwnerRentals(ownerId);

            StringBuilder json =
                    new StringBuilder("[");

            for (int i = 0;
                 i < rentals.size();
                 i++) {

                String[] r = rentals.get(i);

                json.append("{");

                json.append("\"id\":")
                        .append(r[0])
                        .append(",");

                json.append("\"equipment\":\"")
                        .append(escape(r[1]))
                        .append("\",");

                json.append("\"renter\":\"")
                        .append(escape(r[2]))
                        .append("\",");

                json.append("\"quantity\":")
                        .append(r[3])
                        .append(",");

                json.append("\"rentalDate\":\"")
                        .append(r[4])
                        .append("\",");

                json.append("\"status\":\"")
                        .append(r[5])
                        .append("\",");

                json.append("\"pickupStatus\":\"")
                        .append(r[6])
                        .append("\",");

                json.append("\"returnStatus\":\"")
                        .append(r[7])
                        .append("\",");

                json.append("\"condition\":\"")
                        .append(escape(r[8]))
                        .append("\",");

                json.append("\"damageReport\":\"")
                        .append(escape(r[9]))
                        .append("\"");

                json.append("}");

                if (i < rentals.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");

            sendJson(
                    exchange,
                    200,
                    json.toString()
            );

        } catch (Exception e) {

            sendResponse(
                    exchange,
                    400,
                    "Invalid owner ID"
            );
        }
    }


    private void updateRental(
            HttpExchange exchange)
            throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> data =
                parseFormData(body);

        try {

            int rentalId =
                    Integer.parseInt(
                            data.get("rentalId")
                    );

            String pickupStatus =
                    data.get("pickupStatus");

            String returnStatus =
                    data.get("returnStatus");

            String condition =
                    data.get("condition");

            String damageReport =
                    data.get("damageReport");

            boolean success =
                    dao.updateRental(
                            rentalId,
                            pickupStatus,
                            returnStatus,
                            condition,
                            damageReport
                    );

            if (success) {

                sendResponse(
                        exchange,
                        200,
                        "Rental tracking updated"
                );

            } else {

                sendResponse(
                        exchange,
                        500,
                        "Update failed"
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

        if (body == null || body.isEmpty()) {
            return data;
        }

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


    private String escape(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }


    private void sendJson(
            HttpExchange exchange,
            int status,
            String json)
            throws IOException {

        exchange.getResponseHeaders().add(
                "Content-Type",
                "application/json"
        );

        sendResponse(exchange, status, json);
    }


    private void sendResponse(
            HttpExchange exchange,
            int status,
            String message)
            throws IOException {

        byte[] bytes =
                message.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.sendResponseHeaders(
                status,
                bytes.length
        );

        OutputStream output =
                exchange.getResponseBody();

        output.write(bytes);
        output.close();
    }
}