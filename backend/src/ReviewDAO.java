import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewDAO {

    public boolean addReview(
            int equipmentId,
            int renterId,
            int rating,
            String comment) {

        String sql =
                "INSERT INTO reviews " +
                "(equipment_id, renter_id, rating, comment) " +
                "VALUES (?, ?, ?, ?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, equipmentId);
            statement.setInt(2, renterId);
            statement.setInt(3, rating);
            statement.setString(4, comment);

            int rows =
                    statement.executeUpdate();

            statement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    public String getReviews(int equipmentId) {

        String sql =
                """
                SELECT r.rating, r.comment,
                       u.name AS renter_name
                FROM reviews r
                JOIN users u ON r.renter_id = u.id
                WHERE r.equipment_id = ?
                ORDER BY r.created_at DESC
                """;

        StringBuilder json =
                new StringBuilder("[");

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, equipmentId);

            ResultSet result =
                    statement.executeQuery();

            boolean first = true;

            while (result.next()) {

                if (!first) {
                    json.append(",");
                }

                json.append("{");

                json.append("\"rating\":")
                        .append(result.getInt("rating"))
                        .append(",");

                json.append("\"comment\":\"")
                        .append(
                                escape(
                                        result.getString("comment")
                                )
                        )
                        .append("\",");

                json.append("\"renterName\":\"")
                        .append(
                                escape(
                                        result.getString("renter_name")
                                )
                        )
                        .append("\"");

                json.append("}");

                first = false;
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        json.append("]");

        return json.toString();
    }


    private String escape(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}