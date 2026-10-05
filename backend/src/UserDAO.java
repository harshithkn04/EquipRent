import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            int rows = statement.executeUpdate();

            statement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    public User loginUser(String email, String password) {

        String sql =
                "SELECT * FROM users WHERE email = ? AND password = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                User user = new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("role")
                );

                result.close();
                statement.close();
                connection.close();

                return user;
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}