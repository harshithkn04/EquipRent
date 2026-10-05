import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    public boolean addEquipment(Equipment equipment) {

        String sql = """
                INSERT INTO equipment
                (title, description, photo, total_quantity,
                 available_quantity, start_date, end_date,
                 category, owner_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, equipment.getTitle());
            statement.setString(2, equipment.getDescription());
            statement.setString(3, equipment.getPhoto());
            statement.setInt(4, equipment.getTotalQuantity());
            statement.setInt(5, equipment.getTotalQuantity());
            statement.setString(6, equipment.getStartDate());
            statement.setString(7, equipment.getEndDate());
            statement.setString(8, equipment.getCategory());
            statement.setInt(9, equipment.getOwnerId());

            int rows = statement.executeUpdate();

            statement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    public List<Equipment> getAllEquipment(
            String search,
            String category,
            String owner) {

        List<Equipment> equipmentList =
                new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                """
                SELECT e.*, u.name AS owner_name
                FROM equipment e
                JOIN users u ON e.owner_id = u.id
                WHERE 1=1
                """
        );

        List<String> parameters =
                new ArrayList<>();

        if (search != null && !search.isEmpty()) {

            sql.append(
                    " AND LOWER(e.title) LIKE LOWER(?)"
            );

            parameters.add("%" + search + "%");
        }

        if (category != null && !category.isEmpty()) {

            sql.append(
                    " AND e.category = ?"
            );

            parameters.add(category);
        }

        if (owner != null && !owner.isEmpty()) {

            sql.append(
                    " AND LOWER(u.name) LIKE LOWER(?)"
            );

            parameters.add("%" + owner + "%");
        }

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(
                            sql.toString()
                    );

            for (int i = 0;
                 i < parameters.size();
                 i++) {

                statement.setString(
                        i + 1,
                        parameters.get(i)
                );
            }

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Equipment equipment =
                        new Equipment(
                                result.getInt("id"),
                                result.getString("title"),
                                result.getString("description"),
                                result.getString("photo"),
                                result.getInt("total_quantity"),
                                result.getInt("available_quantity"),
                                result.getString("start_date"),
                                result.getString("end_date"),
                                result.getString("category"),
                                result.getInt("owner_id"),
                                result.getString("owner_name")
                        );

                equipmentList.add(equipment);
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return equipmentList;
    }


    // UPDATE EQUIPMENT
    public boolean updateEquipment(
            int id,
            String title,
            String description,
            String photo,
            int totalQuantity,
            String startDate,
            String endDate,
            String category,
            int ownerId) {

        String sql = """
                UPDATE equipment
                SET title = ?,
                    description = ?,
                    photo = ?,
                    total_quantity = ?,
                    available_quantity = ?,
                    start_date = ?,
                    end_date = ?,
                    category = ?
                WHERE id = ?
                AND owner_id = ?
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, photo);
            statement.setInt(4, totalQuantity);

            /*
             * For now, available quantity is reset
             * to total quantity when equipment is edited.
             *
             * We will improve this later so existing
             * rentals are preserved.
             */
            statement.setInt(5, totalQuantity);

            statement.setString(6, startDate);
            statement.setString(7, endDate);
            statement.setString(8, category);

            statement.setInt(9, id);
            statement.setInt(10, ownerId);

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


    // DELETE EQUIPMENT
    public boolean deleteEquipment(
            int id,
            int ownerId) {

        String sql = """
                DELETE FROM equipment
                WHERE id = ?
                AND owner_id = ?
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, id);
            statement.setInt(2, ownerId);

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