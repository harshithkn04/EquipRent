import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ReviewHandler implements HttpHandler {

    private ReviewDAO reviewDAO =
            new ReviewDAO();

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin", "*"
        );

        if (exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {

            addReview(exchange);

        } else if (exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {

            getReviews(exchange);

        } else {

            sendResponse(
                    exchange,
                    405,
                    "Method not allowed"
            );
        }
    }


    private void addReview(
            HttpExchange exchange)
            throws IOException {

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

            int rating =
                    Integer.parseInt(
                            data.get("rating")
                    );

            String comment =
                    data.get("comment");

            if (rating < 1 || rating > 5) {

                sendResponse(
                        exchange,
                        400,
                        "Rating must be between 1 and 5"
                );

                return;
            }

            boolean success =
                    reviewDAO.addReview(
                            equipmentId,
                            renterId,
                            rating,
                            comment
                    );

            if (success) {

                sendResponse(
                        exchange,
                        201,
                        "Review added successfully"
                );

            } else {

                sendResponse(
                        exchange,
                        500,
                        "Failed to add review"
                );
            }

        } catch (Exception e) {

            sendResponse(
                    exchange,
                    400,
                    "Invalid review data"
            );
        }
    }


    private void getReviews(
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

            int equipmentId =
                    Integer.parseInt(
                            params.get("equipmentId")
                    );

            String reviews =
                    reviewDAO.getReviews(
                            equipmentId
                    );

            exchange.getResponseHeaders().add(
                    "Content-Type",
                    "application/json"
            );

            sendResponse(
                    exchange,
                    200,
                    reviews
            );

        } catch (Exception e) {

            sendResponse(
                    exchange,
                    400,
                    "Invalid equipment ID"
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


    private void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String message)
            throws IOException {

        byte[] bytes =
                message.getBytes(
                        StandardCharsets.UTF_8
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