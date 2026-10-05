import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentalTrackingDAO {

    public List<String[]> getOwnerRentals(int ownerId) {

        List<String[]> rentals = new ArrayList<>();

        String sql = """
                SELECT
                    r.id,
                    e.title,
                    u.name AS renter_name,
                    r.quantity,
                    r.rental_date,
                    r.status,
                    r.pickup_status,
                    r.return_status,
                    r.return_condition,
                    r.damage_report
                FROM rentals r
                JOIN equipment e
                    ON r.equipment_id = e.id
                JOIN users u
                    ON r.renter_id = u.id
                WHERE e.owner_id = ?
                ORDER BY r.rental_date DESC
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, ownerId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                String[] rental = {
                        String.valueOf(result.getInt("id")),
                        result.getString("title"),
                        result.getString("renter_name"),
                        String.valueOf(result.getInt("quantity")),
                        String.valueOf(result.getDate("rental_date")),
                        result.getString("status"),
                        result.getString("pickup_status"),
                        result.getString("return_status"),
                        result.getString("return_condition"),
                        result.getString("damage_report")
                };

                rentals.add(rental);
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rentals;
    }


    public boolean updateRental(
            int rentalId,
            String pickupStatus,
            String returnStatus,
            String condition,
            String damageReport) {

        String sql = """
                UPDATE rentals
                SET pickup_status = ?,
                    return_status = ?,
                    return_condition = ?,
                    damage_report = ?
                WHERE id = ?
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, pickupStatus);
            statement.setString(2, returnStatus);
            statement.setString(3, condition);
            statement.setString(4, damageReport);
            statement.setInt(5, rentalId);

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
}