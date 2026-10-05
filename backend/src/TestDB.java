import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("Connection test successful!");

            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}