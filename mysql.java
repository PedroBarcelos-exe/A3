package pacote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysql {
    private static final String URL = "jdbc:mysql://localhost:3306/a3_transfere_veiculo";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
    }

    public static void testConnection() {
        try(Connection conn = getConnect()) {
            System.out.println("Conexão estabelecida com sucesso!");
        } catch(SQLException e) {
            System.out.println("Erro na conexão:");
            e.printStackTrace();
        }
    }
}
