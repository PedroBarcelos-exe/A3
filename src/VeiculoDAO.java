import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object (DAO) para a classe Veiculo.
 * Responsável por todas as operações de persistência relacionadas a veículos
 * no banco de dados.
 */
public class VeiculoDAO {
    /**
     * Insere um novo veículo no banco de dados.
     * Após a inserção, recupera o ID gerado automaticamente pelo banco.
     * @param veiculo O objeto Veiculo a ser inserido.
     * @return O ID gerado para o veículo inserido.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public int insert(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculo(marca, modelo, ano, cor) VALUES (?, ?, ?, ?)";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             // Prepara a declaração SQL, indicando que as chaves geradas devem ser retornadas
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, veiculo.getMarca()); // Define a marca do veículo
            stmt.setString(2, veiculo.getModelo()); // Define o modelo do veículo
            stmt.setInt(3, veiculo.getAno()); // Define o ano do veículo
            stmt.setString(4, veiculo.getCor()); // Define a cor do veículo
            stmt.executeUpdate(); // Executa a atualização no banco de dados

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) { // Obtém as chaves geradas
                if (generatedKeys.next()) { // Se um ID foi gerado
                    return generatedKeys.getInt(1); // Retorna o ID gerado
                } else {
                    throw new SQLException("Falha ao obter ID do veículo, nenhum ID gerado.");
                }
            }
        }
    }

    /**
     * Busca um veículo no banco de dados pela sua placa atual.
     * A busca considera apenas transferências com status 'N' (Nova/Atual).
     * @param placa A placa atual do veículo a ser buscado.
     * @return O objeto Veiculo encontrado, ou null se nenhum veículo for encontrado com a placa.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public Veiculo findByPlacaAtual(String placa) throws SQLException {
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE t.status_transferencia = 'N' AND t.placa_atual = ?";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setString(1, placa); // Define o parâmetro da placa
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                if (rs.next()) { // Se encontrar um resultado
                    return new Veiculo( // Retorna um novo objeto Veiculo com os dados recuperados
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getString("cor"),
                            rs.getString("placa_atual")
                    );
                }
            }
        }
        return null; // Retorna null se nenhum veículo for encontrado
    }

    /**
     * Busca um veículo no banco de dados pelo seu ID.
     * Inclui a placa atual, buscando na tabela de transferências com status 'N'.
     * @param id O ID do veículo a ser buscado.
     * @return O objeto Veiculo encontrado, ou null se nenhum veículo for encontrado com o ID.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public Veiculo findById(int id) throws SQLException {
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE v.id = ? AND t.status_transferencia = 'N'";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, id); // Define o parâmetro do ID
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                if (rs.next()) { // Se encontrar um resultado
                    return new Veiculo( // Retorna um novo objeto Veiculo com os dados recuperados
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getString("cor"),
                            rs.getString("placa_atual")
                    );
                }
            }
        }
        return null; // Retorna null se nenhum veículo for encontrado
    }

    /**
     * Busca todos os veículos que pertencem a um determinado proprietário (pessoa).
     * Considera apenas a transferência atual (status 'N').
     * @param idPessoa O ID da pessoa proprietária.
     * @return Uma lista de objetos Veiculo que pertencem ao proprietário especificado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public List<Veiculo> findByProprietario(int idPessoa) throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE t.status_transferencia = 'N' AND t.id_pessoa_compra = ?";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, idPessoa); // Define o parâmetro do ID da pessoa
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                while (rs.next()) { // Itera sobre todos os resultados.
                    veiculos.add(new Veiculo( // Adiciona um novo objeto Veiculo à lista
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getString("cor"),
                            rs.getString("placa_atual")
                    ));
                }
            }
        }
        return veiculos; // Retorna a lista de veículos
    }

    /**
     * Busca todos os veículos que ainda possuem placa no formato antigo (LLL-NNNN).
     * Considera apenas a placa atual do veículo (status 'N').
     * @return Uma lista de objetos Veiculo com placas no formato antigo.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public List<Veiculo> findVehiclesWithOldPlateFormat() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        // Usa uma expressão regular (REGEXP) para identificar o formato de placa antiga
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE t.status_transferencia = 'N' AND t.placa_atual REGEXP '^[A-Z]{3}-[0-9]{4}$'";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                while (rs.next()) { // Itera sobre todos os resultados
                    veiculos.add(new Veiculo( // Adiciona um novo objeto Veiculo à lista
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getString("cor"),
                            rs.getString("placa_atual")
                    ));
                }
            }
        }
        return veiculos; // Retorna a lista de veículos com placas antigas
    }

    /**
     * Conta a quantidade de veículos por marca no banco de dados.
     * @return Um mapa onde a chave é a marca do veículo (em maiúsculas) e o valor é a quantidade de veículos.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public Map<String, Integer> countByMarca() throws SQLException {
        Map<String, Integer> counts = new HashMap<>();
        String sql = "SELECT UPPER(v.marca) AS marca, COUNT(*) AS qtd " +
                     "FROM veiculo v GROUP BY v.marca ORDER BY qtd DESC"; // Agrupa por marca e ordena pela quantidade
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql); // Prepara a declaração SQL
             ResultSet rs = stmt.executeQuery()) { // Executa a consulta
            while (rs.next()) { // Itera sobre os resultados
                counts.put(rs.getString("marca"), rs.getInt("qtd")); // Adiciona a marca e a contagem ao mapa
            }
        }
        return counts; // Retorna o mapa de contagens por marca
    }

    /**
     * Exclui um veículo do banco de dados pelo seu ID.
     * @param idVeiculo O ID do veículo a ser excluído.
     * @return true se o veículo foi excluído com sucesso, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public boolean delete(int idVeiculo) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE id = ?";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, idVeiculo); // Define o parâmetro do ID do veículo
            return stmt.executeUpdate() > 0; // Retorna true se alguma linha foi afetada (excluída)
        }
    }
}