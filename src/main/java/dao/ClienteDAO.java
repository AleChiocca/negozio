package dao;

import database.DatabaseManager;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO implements BaseDAO<Cliente, Integer> {

    DatabaseManager db = new DatabaseManager();

    @Override
    public Cliente findById(Integer id) {
        String sql = "SELECT * FROM clienti WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("ID"),
                            rs.getString("NOME"),
                            rs.getString("COGNOME"),
                            rs.getString("EMAIL"),
                            rs.getString("CITTA"),
                            rs.getDouble("SALDO")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Cliente> findAll() {
        ArrayList<Cliente> clienti = new ArrayList<>();
        String sql = "SELECT * FROM clienti";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clienti.add(new Cliente(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("COGNOME"),
                        rs.getString("EMAIL"),
                        rs.getString("CITTA"),
                        rs.getDouble("SALDO")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienti;
    }

    @Override
    public void insert(Cliente cliente) {
        String sql = "INSERT INTO clienti (NOME, COGNOME, EMAIL, CITTA, SALDO) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getCitta());
            ps.setDouble(5, cliente.getSaldo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM clienti WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                IO.println("Cliente con ID " + id + " eliminato con successo.");
            } else {
                IO.println("Nessun cliente trovato con ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aggiornaSaldo(int idcliente, double nuovosaldo, Connection conn) throws SQLException {
        String sql = "UPDATE clienti SET SALDO = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, nuovosaldo);
            ps.setInt(2, idcliente);
            ps.executeUpdate();
        }
    }
}