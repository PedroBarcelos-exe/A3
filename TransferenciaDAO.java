import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; 
import java.sql.Types; 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TransferenciaDAO {

    public void insert(Transferencia transferencia) throws SQLException {
        String sql = "INSERT INTO transferencia (id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transferencia.getIdVeiculo());
            stmt.setInt(2, transferencia.getIdPessoaCompra());
            
            if (transferencia.getIdPessoaVenda() != null) {
                stmt.setInt(3, transferencia.getIdPessoaVenda());
            } else {
                stmt.setNull(3, Types.INTEGER); 
            }
            stmt.setString(4, transferencia.getPlacaAnterior());
            stmt.setString(5, transferencia.getPlacaAtual());
            stmt.setDate(6, Date.valueOf(transferencia.getData())); 
            stmt.setString(7, transferencia.getStatusTransferencia());
            stmt.executeUpdate(); 
        }
    }

    
    public void updateStatusOfCurrentTransfer(int idVeiculo, String newStatus) throws SQLException {
        String sql = "UPDATE transferencia SET status_transferencia = ? WHERE id_veiculo = ? AND status_transferencia = 'N'";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, idVeiculo);
            stmt.executeUpdate(); 
        }
    }

    
    public Transferencia findLastTransferencia(int idVeiculo) throws SQLException {
        String sql = "SELECT id, id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia " +
                     "FROM transferencia WHERE id_veiculo = ? AND status_transferencia = 'N'";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeiculo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Integer idPessoaVenda = rs.getObject("id_pessoa_venda", Integer.class);
                    return new Transferencia(
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getInt("id_pessoa_compra"),
                            idPessoaVenda,
                            rs.getString("placa_anterior"),
                            rs.getString("placa_atual"),
                            rs.getString("status_transferencia"),
                            rs.getDate("data").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    public List<Transferencia> findHistoryByVehicle(int idVeiculo) throws SQLException {
        List<Transferencia> history = new ArrayList<>();
        
        String sql = "SELECT id, id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia " +
                     "FROM transferencia WHERE id_veiculo = ? ORDER BY data DESC";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeiculo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Integer idPessoaVenda = rs.getObject("id_pessoa_venda", Integer.class);
                    history.add(new Transferencia(
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getInt("id_pessoa_compra"),
                            idPessoaVenda,
                            rs.getString("placa_anterior"),
                            rs.getString("placa_atual"),
                            rs.getString("status_transferencia"),
                            rs.getDate("data").toLocalDate()
                    ));
                }
            }
        }
        return history;
    }

    public List<Transferencia> findByPeriod(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Transferencia> transfers = new ArrayList<>();
        String sql = "SELECT id, id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_anterior, placa_atual, data, status_transferencia " +
                     "FROM transferencia WHERE data BETWEEN ? AND ? ORDER BY data ASC";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Integer idPessoaVenda = rs.getObject("id_pessoa_venda", Integer.class);
                    transfers.add(new Transferencia(
                            rs.getInt("id"),
                            rs.getInt("id_veiculo"),
                            rs.getInt("id_pessoa_compra"),
                            idPessoaVenda,
                            rs.getString("placa_anterior"),
                            rs.getString("placa_atual"),
                            rs.getString("status_transferencia"),
                            rs.getDate("data").toLocalDate()
                    ));
                }
            }
        }
        return transfers;
    }

    public boolean deleteByVehicle(int idVeiculo) throws SQLException {
        String sql = "DELETE FROM transferencia WHERE id_veiculo = ?";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeiculo);
            return stmt.executeUpdate() > 0; 
        }
    }
}