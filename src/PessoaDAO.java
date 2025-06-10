import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date; 


public class PessoaDAO {

    public int insert(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, cpf, dataNascimento, genero) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            
            stmt.setDate(3, Date.valueOf(pessoa.getDataNascimento()));
            stmt.setString(4, String.valueOf(pessoa.getGenero()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter ID da pessoa, nenhum ID gerado.");
                }
            }
        }
    }

    public Pessoa findByCpf(String cpf) throws SQLException {
        String sql = "SELECT id, nome, cpf, dataNascimento, genero FROM pessoa WHERE cpf = ?";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) { 
                    return new Pessoa(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getDate("dataNascimento").toLocalDate(), // Converte java.sql.Date para LocalDate.
                            rs.getString("genero").charAt(0) // Pega o primeiro caractere da String.
                    );
                }
            }
        }
        return null; 
    }

    
    public Pessoa findById(int id) throws SQLException {
        String sql = "SELECT id, nome, cpf, dataNascimento, genero FROM pessoa WHERE id = ?";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getDate("dataNascimento").toLocalDate(),
                            rs.getString("genero").charAt(0)
                    );
                }
            }
        }
        return null; 
    }
}