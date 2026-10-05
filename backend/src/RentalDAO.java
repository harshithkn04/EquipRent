import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RentalDAO {

    public String rentEquipment(
            int equipmentId,
            int renterId,
            int quantity) {

        Connection connection = null;

        try {

            connection = DBConnection.getConnection();

            // Check current available quantity
            String checkSql =
                    "SELECT available_quantity FROM equipment WHERE id = ?";

            PreparedStatement checkStatement =
                    connection.prepareStatement(checkSql);

            checkStatement.setInt(1, equipmentId);

            ResultSet result =
                    checkStatement.executeQuery();

            if (!result.next()) {

                result.close();
                checkStatement.close();

                return "Equipment not found";
            }

            int availableQuantity =
                    result.getInt("available_quantity");

            result.close();
            checkStatement.close();

            // Check availability
            if (quantity <= 0) {

                return "Quantity must be greater than zero";
            }

            if (quantity > availableQuantity) {

                return "Not enough equipment available";
            }

            // Create rental
            String rentalSql =
                    """
                    INSERT INTO rentals
                    (equipment_id, renter_id, quantity,
                     rental_date, status)
                    VALUES (?, ?, ?, CURDATE(), 'ACTIVE')
                    """;

            PreparedStatement rentalStatement =
                    connection.prepareStatement(rentalSql);

            rentalStatement.setInt(1, equipmentId);
            rentalStatement.setInt(2, renterId);
            rentalStatement.setInt(3, quantity);

            rentalStatement.executeUpdate();

            rentalStatement.close();

            // Reduce available quantity
            String updateSql =
                    """
                    UPDATE equipment
                    SET available_quantity =
                        available_quantity - ?
                    WHERE id = ?
                    """;

            PreparedStatement updateStatement =
                    connection.prepareStatement(updateSql);

            updateStatement.setInt(1, quantity);
            updateStatement.setInt(2, equipmentId);

            updateStatement.executeUpdate();

            updateStatement.close();

            return "Rental successful";

        } catch (Exception e) {

            e.printStackTrace();

            return "Rental failed";

        } finally {

            try {

                if (connection != null) {
                    connection.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}