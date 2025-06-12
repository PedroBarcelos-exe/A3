import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; 
import java.sql.Types; // Necessário para setar valores NULL para tipos específicos
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para a classe Transferencia.
 * Responsável por todas as operações de persistência relacionadas a transferências de veículos
 * no banco de dados.
 */
public class TransferenciaDAO {

    /**
     * Insere uma nova transferência no banco de dados.
     * @param transferencia O objeto Transferencia a ser inserido.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public void insert(Transferencia transferencia) throws SQLException {
        String sql = "INSERT INTO transferencia (id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, transferencia.getIdVeiculo()); // Define o ID do veículo
            stmt.setInt(2, transferencia.getIdPessoaCompra()); // Define o ID da pessoa compradora
            
            // Verifica se o ID da pessoa vendedora é nulo para definir o parâmetro corretamente
            if (transferencia.getIdPessoaVenda() != null) {
                stmt.setInt(3, transferencia.getIdPessoaVenda());
            } else {
                stmt.setNull(3, Types.INTEGER); // Define como NULL se não houver vendedor
            }
            stmt.setString(4, transferencia.getPlacaAnterior()); // Define a placa anterior
            stmt.setString(5, transferencia.getPlacaAtual()); // Define a placa atual
            stmt.setDate(6, Date.valueOf(transferencia.getData())); // Define a data, convertendo LocalDate para java.sql.Date
            stmt.setString(7, transferencia.getStatusTransferencia()); // Define o status da transferência
            stmt.executeUpdate(); // Executa a atualização no banco de dados
        }
    }

    /**
     * Atualiza o status da transferência "atual" (status 'N') de um determinado veículo.
     * Normalmente usada para marcar uma transferência como "antiga" ('A') quando uma nova ocorre.
     * @param idVeiculo O ID do veículo cuja transferência será atualizada.
     * @param newStatus O novo status a ser atribuído (ex: 'A' para Antiga).
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public void updateStatusOfCurrentTransfer(int idVeiculo, String newStatus) throws SQLException {
        String sql = "UPDATE transferencia SET status_transferencia = ? WHERE id_veiculo = ? AND status_transferencia = 'N'";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setString(1, newStatus); // Define o novo status
            stmt.setInt(2, idVeiculo); // Define o ID do veículo
            stmt.executeUpdate(); // Executa a atualização no banco de dados
        }
    }

    /**
     * Busca a última transferência ativa (status 'N') de um determinado veículo.
     * @param idVeiculo O ID do veículo para o qual buscar a última transferência.
     * @return O objeto Transferencia correspondente à última transferência ativa, ou null se não houver.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public Transferencia findLastTransferencia(int idVeiculo) throws SQLException {
        String sql = "SELECT id, id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia " +
                     "FROM transferencia WHERE id_veiculo = ? AND status_transferencia = 'N'";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, idVeiculo); // Define o ID do veículo
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                if (rs.next()) { // Se encontrar um resultado
                    // Obtém o ID da pessoa vendedora, tratando a possibilidade de ser nulo
                    Integer idPessoaVenda = rs.getObject("id_pessoa_venda", Integer.class);
                    return new Transferencia( // Retorna um novo objeto Transferencia com os dados recuperados
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getInt("id_pessoa_compra"),
                            idPessoaVenda,
                            rs.getString("placa_anterior"),
                            rs.getString("placa_atual"),
                            rs.getString("status_transferencia"),
                            rs.getDate("data").toLocalDate() // Converte java.sql.Date para LocalDate
                    );
                }
            }
        }
        return null; // Retorna null se nenhuma transferência ativa for encontrada
    }

    /**
     * Busca o histórico completo de transferências de um determinado veículo.
     * @param idVeiculo O ID do veículo para o qual buscar o histórico.
     * @return Uma lista de objetos Transferencia representando o histórico do veículo, ordenada por data.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public List<Transferencia> findHistoryByVehicle(int idVeiculo) throws SQLException {
        List<Transferencia> history = new ArrayList<>();
        
        String sql = "SELECT id, id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia " +
                     "FROM transferencia WHERE id_veiculo = ? ORDER BY data DESC"; // Ordena do mais recente para o mais antigo
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, idVeiculo); // Define o ID do veículo
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                while (rs.next()) { // Itera sobre os resultados
                    // Obtém o ID da pessoa vendedora, tratando a possibilidade de ser nulo
                    Integer idPessoaVenda = rs.getObject("id_pessoa_venda", Integer.class);
                    history.add(new Transferencia( // Adiciona um novo objeto Transferencia à lista
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getInt("id_pessoa_compra"),
                            idPessoaVenda,
                            rs.getString("placa_anterior"),
                            rs.getString("placa_atual"),
                            rs.getString("status_transferencia"),
                            rs.getDate("data").toLocalDate() // Converte java.sql.Date para LocalDate
                    ));
                }
            }
        }
        return history; // Retorna a lista de histórico
    }

    /**
     * Busca todas as transferências que ocorreram dentro de um período de datas específico.
     * @param startDate A data de início do período.
     * @param endDate A data de fim do período.
     * @return Uma lista de objetos Transferencia que ocorreram no período especificado.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public List<Transferencia> findByPeriod(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Transferencia> transfers = new ArrayList<>();
        String sql = "SELECT id, id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia " +
                     "FROM transferencia WHERE data BETWEEN ? AND ? ORDER BY data ASC"; // Ordena do mais antigo para o mais recente
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setDate(1, Date.valueOf(startDate)); // Define a data de início
            stmt.setDate(2, Date.valueOf(endDate)); // Define a data de fim
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                while (rs.next()) { // Itera sobre os resultados
                    // Obtém o ID da pessoa vendedora, tratando a possibilidade de ser nulo
                    Integer idPessoaVenda = rs.getObject("id_pessoa_venda", Integer.class);
                    transfers.add(new Transferencia( // Adiciona um novo objeto Transferencia à lista
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getInt("id_pessoa_compra"),
                            idPessoaVenda,
                            rs.getString("placa_anterior"),
                            rs.getString("placa_atual"),
                            rs.getString("status_transferencia"),
                            rs.getDate("data").toLocalDate() // Converte java.sql.Date para LocalDate
                    ));
                }
            }
        }
        return transfers; // Retorna a lista de transferências no período
    }

    /**
     * Exclui todas as transferências associadas a um determinado veículo.
     * Usado para dar baixa em um veículo, removendo todo o seu histórico de transferências.
     * @param idVeiculo O ID do veículo cujas transferências serão excluídas.
     * @return true se uma ou mais transferências foram excluídas, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a operação no banco de dados.
     */
    public boolean deleteByVehicle(int idVeiculo) throws SQLException {
        String sql = "DELETE FROM transferencia WHERE id_veiculo = ?";
        try (Connection conn = mysql.getConnect(); // Obtém uma conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a declaração SQL
            stmt.setInt(1, idVeiculo); // Define o ID do veículo
            return stmt.executeUpdate() > 0; // Retorna true se alguma linha foi afetada (excluída)
        }
    }
}