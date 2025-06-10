import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VeiculoDAO {
    public int insert(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculo(marca, modelo, ano, cor) VALUES (?, ?, ?, ?)";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setString(4, veiculo.getCor());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter ID do veículo, nenhum ID gerado.");
                }
            }
        }
    }

   
    public Veiculo findByPlacaAtual(String placa) throws SQLException {
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE t.status_transferencia = 'N' AND t.placa_atual = ?";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Veiculo(
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
        return null;
    }

    
    public Veiculo findById(int id) throws SQLException {
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE v.id = ? AND t.status_transferencia = 'N'";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Veiculo(
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
        return null;
    }

    
    public List<Veiculo> findByProprietario(int idPessoa) throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE t.status_transferencia = 'N' AND t.id_pessoa_compra = ?";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPessoa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Itera sobre todos os resultados.
                    veiculos.add(new Veiculo(
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
        return veiculos;
    }

   
    public List<Veiculo> findVehiclesWithOldPlateFormat() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT v.id, v.marca, v.modelo, v.ano, v.cor, t.placa_atual " +
                     "FROM veiculo v JOIN transferencia t ON v.id = t.id_veiculo " +
                     "WHERE t.status_transferencia = 'N' AND t.placa_atual REGEXP '^[A-Z]{3}-[0-9]{4}$'";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    veiculos.add(new Veiculo(
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
        return veiculos;
    }

    public Map<String, Integer> countByMarca() throws SQLException {
        Map<String, Integer> counts = new HashMap<>();
        String sql = "SELECT UPPER(v.marca) AS marca, COUNT(*) AS qtd " +
                     "FROM veiculo v GROUP BY v.marca ORDER BY qtd DESC";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                counts.put(rs.getString("marca"), rs.getInt("qtd"));
            }
        }
        return counts;
    }

    public boolean delete(int idVeiculo) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE id = ?";
        try (Connection conn = mysql.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeiculo);
            return stmt.executeUpdate() > 0; 
        }
    }
}