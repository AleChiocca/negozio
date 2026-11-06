package dao;

import database.DatabaseManager;
import model.Ordine;

import java.sql.*;
import java.util.ArrayList;

public class OrdineDAO implements BaseDAO<Ordine, Integer> {

    DatabaseManager db = new DatabaseManager();

    @Override
    public Ordine findById(Integer id) {
        String sql = "SELECT * FROM ordini WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ordine(
                            rs.getInt("id"),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_prodotto"),
                            rs.getInt("quantita"),
                            rs.getDate("data_ordine")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Ordine> findAll() {
        ArrayList<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT * FROM ordini";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ordini.add(new Ordine(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_prodotto"),
                        rs.getInt("quantita"),
                        rs.getDate("data_ordine")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordini;
    }

    @Override
    public void insert(Ordine ordine) {
        String sql = "INSERT INTO ordini (id_cliente, id_prodotto, quantita, data_ordine) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordine.getIdCliente());
            ps.setInt(2, ordine.getIdProdotto());
            ps.setInt(3, ordine.getQuantita());
            ps.setDate(4, ordine.getDataOrdine());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aggiornaQuantitaProdotti(int id, int quantita, Connection conn) throws SQLException {
        String sql = "UPDATE ordini SET quantita = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantita);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}