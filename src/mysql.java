import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet; 
import java.sql.Statement; 


public class mysql {
    private static final String URL = "jdbc:mysql://localhost:3306/a3_transfere_veiculo?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public static Connection getConnect() throws SQLException {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch(ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado. Certifique-se de que o JAR do MySQL Connector esteja no classpath.", e);
        }
    }

    public static void testConnection() {
        try(Connection conn = getConnect()) {
            System.out.println("Conexão estabelecida com sucesso!");
        } catch(SQLException e) {
            System.out.println("Erro na conexão com o banco de dados:");
            e.printStackTrace();
        }
    }

    public static void listarPessoas() {
        String query = "SELECT id, nome, cpf FROM pessoa";

        try (Connection conn = getConnect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Lista de pessoas:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");

                System.out.println("ID: " + id + " | Nome: " + nome + " | cpf: " + cpf);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar banco de dados:");
            e.printStackTrace();
        }
    }

    public static boolean isTableEmpty(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (Connection conn = getConnect(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) { 
            
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }
}