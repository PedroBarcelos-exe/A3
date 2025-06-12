import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date; 


public class PessoaDAO {

    /**
     * Insere uma nova pessoa no banco de dados.
     * @param pessoa O objeto Pessoa a ser inserido.
     * @return O ID gerado para a pessoa inserida.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public int insert(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, cpf, dataNascimento, genero) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Prepara a declaração SQL para inserção, solicitando as chaves geradas
            stmt.setString(1, pessoa.getNome()); // Define o parâmetro 1 (nome)
            stmt.setString(2, pessoa.getCpf()); // Define o parâmetro 2 (cpf)
            
            stmt.setDate(3, Date.valueOf(pessoa.getDataNascimento())); // Define o parâmetro 3 (data de nascimento), convertendo LocalDate para java.sql.Date
            stmt.setString(4, String.valueOf(pessoa.getGenero())); // Define o parâmetro 4 (gênero), convertendo char para String
            stmt.executeUpdate(); // Executa a atualização no banco de dados

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) { // Obtém as chaves geradas (IDs)
                if (generatedKeys.next()) { // Se houver chaves geradas
                    return generatedKeys.getInt(1); // Retorna o primeiro ID gerado
                } else {
                    throw new SQLException("Falha ao obter ID da pessoa, nenhum ID gerado."); // Lança exceção se nenhum ID for gerado
                }
            }
        }
    }

    /**
     * Busca uma pessoa no banco de dados pelo seu CPF.
     * @param cpf O CPF da pessoa a ser buscada.
     * @return O objeto Pessoa encontrado, ou null se nenhuma pessoa for encontrada com o CPF fornecido.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public Pessoa findByCpf(String cpf) throws SQLException {
        String sql = "SELECT id, nome, cpf, dataNascimento, genero FROM pessoa WHERE cpf = ?";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setString(1, cpf); // Define o parâmetro 1 (CPF)
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                if (rs.next()) { // Se encontrar um resultado
                    return new Pessoa( // Retorna um novo objeto Pessoa com os dados recuperados
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getDate("dataNascimento").toLocalDate(), // Converte java.sql.Date para LocalDate.
                            rs.getString("genero").charAt(0) // Pega o primeiro caractere da String.
                    );
                }
            }
        }
        return null; // Retorna null se nenhuma pessoa for encontrada
    }

    /**
     * Busca uma pessoa no banco de dados pelo seu ID.
     * @param id O ID da pessoa a ser buscada.
     * @return O objeto Pessoa encontrado, ou null se nenhuma pessoa for encontrada com o ID fornecido.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public Pessoa findById(int id) throws SQLException {
        String sql = "SELECT id, nome, cpf, dataNascimento, genero FROM pessoa WHERE id = ?";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, id); // Define o parâmetro 1 (ID)
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                if (rs.next()) { // Se encontrar um resultado
                    return new Pessoa( // Retorna um novo objeto Pessoa com os dados recuperados
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getDate("dataNascimento").toLocalDate(),
                            rs.getString("genero").charAt(0)
                    );
                }
            }
        }
        return null; // Retorna null se nenhuma pessoa for encontrada
    }
}