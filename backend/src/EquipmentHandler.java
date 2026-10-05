import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentHandler implements HttpHandler {

    private EquipmentDAO equipmentDAO =
            new EquipmentDAO();


    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin", "*"
        );

        String method =
                exchange.getRequestMethod();


        if (method.equalsIgnoreCase("POST")) {

            addEquipment(exchange);

        } else if (method.equalsIgnoreCase("GET")) {

            getEquipment(exchange);

        } else if (method.equalsIgnoreCase("PUT")) {

            updateEquipment(exchange);

        } else if (method.equalsIgnoreCase("DELETE")) {

            deleteEquipment(exchange);

        } else {

            sendResponse(
                    exchange,
                    405,
                    "Method not allowed"
            );
        }
    }


    // =====================================================
    // ADD EQUIPMENT
    // =====================================================

    private void addEquipment(
            HttpExchange exchange)
            throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> data =
                parseFormData(body);

        try {

            String title =
                    data.get("title");

            String description =
                    data.get("description");

            String photo =
                    data.get("photo");

            int quantity =
                    Integer.parseInt(
                            data.get("quantity")
                    );

            String startDate =
                    data.get("startDate");

            String endDate =
                    data.get("endDate");

            String category =
                    data.get("category");

            int ownerId =
                    Integer.parseInt(
                            data.get("ownerId")
                    );


            Equipment equipment =
                    new Equipment(
                            title,
                            description,
                            photo,
                            quantity,
                            startDate,
                            endDate,
                            category,
                            ownerId
                    );


            boolean success =
                    equipmentDAO.addEquipment(
                            equipment
                    );


            if (success) {

                sendResponse(
                        exchange,
                        201,
                        "Equipment added successfully"
                );

            } else {

                sendResponse(
                        exchange,
                        500,
                        "Failed to add equipment"
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    400,
                    "Invalid equipment data"
            );
        }
    }


    // =====================================================
    // GET EQUIPMENT
    // =====================================================

    private void getEquipment(
            HttpExchange exchange)
            throws IOException {

        String query =
                exchange.getRequestURI()
                        .getRawQuery();


        Map<String, String> params =
                parseFormData(
                        query == null
                                ? ""
                                : query
                );


        String search =
                params.get("search");

        String category =
                params.get("category");

        String owner =
                params.get("owner");


        List<Equipment> equipmentList =
                equipmentDAO.getAllEquipment(
                        search,
                        category,
                        owner
                );


        StringBuilder json =
                new StringBuilder("[");


        for (int i = 0;
             i < equipmentList.size();
             i++) {

            Equipment e =
                    equipmentList.get(i);


            json.append("{");


            json.append("\"id\":")
                    .append(e.getId())
                    .append(",");


            json.append("\"title\":\"")
                    .append(
                            escape(e.getTitle())
                    )
                    .append("\",");


            json.append("\"description\":\"")
                    .append(
                            escape(
                                    e.getDescription()
                            )
                    )
                    .append("\",");


            json.append("\"photo\":\"")
                    .append(
                            escape(e.getPhoto())
                    )
                    .append("\",");


            json.append("\"totalQuantity\":")
                    .append(
                            e.getTotalQuantity()
                    )
                    .append(",");


            json.append("\"availableQuantity\":")
                    .append(
                            e.getAvailableQuantity()
                    )
                    .append(",");


            json.append("\"startDate\":\"")
                    .append(e.getStartDate())
                    .append("\",");


            json.append("\"endDate\":\"")
                    .append(e.getEndDate())
                    .append("\",");


            json.append("\"category\":\"")
                    .append(
                            escape(e.getCategory())
                    )
                    .append("\",");


            json.append("\"ownerId\":")
                    .append(e.getOwnerId())
                    .append(",");


            json.append("\"ownerName\":\"")
                    .append(
                            escape(
                                    e.getOwnerName()
                            )
                    )
                    .append("\"");


            json.append("}");


            if (i < equipmentList.size() - 1) {

                json.append(",");

            }
        }


        json.append("]");


        exchange.getResponseHeaders().add(
                "Content-Type",
                "application/json"
        );


        sendResponse(
                exchange,
                200,
                json.toString()
        );
    }


    // =====================================================
    // UPDATE EQUIPMENT
    // =====================================================

    private void updateEquipment(
            HttpExchange exchange)
            throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );


        Map<String, String> data =
                parseFormData(body);


        try {

            int id =
                    Integer.parseInt(
                            data.get("id")
                    );


            String title =
                    data.get("title");


            String description =
                    data.get("description");


            String photo =
                    data.get("photo");


            int quantity =
                    Integer.parseInt(
                            data.get("quantity")
                    );


            String startDate =
                    data.get("startDate");


            String endDate =
                    data.get("endDate");


            String category =
                    data.get("category");


            int ownerId =
                    Integer.parseInt(
                            data.get("ownerId")
                    );


            boolean success =
                    equipmentDAO.updateEquipment(
                            id,
                            title,
                            description,
                            photo,
                            quantity,
                            startDate,
                            endDate,
                            category,
                            ownerId
                    );


            if (success) {

                sendResponse(
                        exchange,
                        200,
                        "Equipment updated successfully"
                );

            } else {

                sendResponse(
                        exchange,
                        404,
                        "Equipment not found or you are not the owner"
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    400,
                    "Invalid equipment data"
            );
        }
    }


    // =====================================================
    // DELETE EQUIPMENT
    // =====================================================

    private void deleteEquipment(
            HttpExchange exchange)
            throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );


        Map<String, String> data =
                parseFormData(body);


        try {

            int id =
                    Integer.parseInt(
                            data.get("id")
                    );


            int ownerId =
                    Integer.parseInt(
                            data.get("ownerId")
                    );


            boolean success =
                    equipmentDAO.deleteEquipment(
                            id,
                            ownerId
                    );


            if (success) {

                sendResponse(
                        exchange,
                        200,
                        "Equipment deleted successfully"
                );

            } else {

                sendResponse(
                        exchange,
                        404,
                        "Equipment not found or you are not the owner"
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    400,
                    "Invalid delete request"
            );
        }
    }


    // =====================================================
    // FORM DATA PARSER
    // =====================================================

    private Map<String, String> parseFormData(
            String body) {

        Map<String, String> data =
                new HashMap<>();


        if (body == null ||
                body.isEmpty()) {

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


                data.put(
                        key,
                        value
                );
            }
        }


        return data;
    }


    // =====================================================
    // JSON ESCAPE
    // =====================================================

    private String escape(
            String text) {

        if (text == null) {

            return "";
        }


        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }


    // =====================================================
    // RESPONSE
    // =====================================================

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