package dao;

import database.DatabaseManager;
import model.Prodotto;

import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO implements BaseDAO<Prodotto, Integer> {

    DatabaseManager db = new DatabaseManager();

    @Override
    public Prodotto findById(Integer id) {
        String sql = "SELECT * FROM prodotti WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Prodotto(
                            rs.getInt("ID"),
                            rs.getString("NOME"),
                            rs.getString("CATEGORIA"),
                            rs.getDouble("PREZZO")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Prodotto> findAll() {
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        String sql = "SELECT * FROM prodotti";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                prodotti.add(new Prodotto(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("CATEGORIA"),
                        rs.getDouble("PREZZO")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prodotti;
    }

    @Override
    public void insert(Prodotto prodotto) {
        String sql = "INSERT INTO prodotti (NOME, CATEGORIA, PREZZO) VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getCategoria());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM prodotti WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prodotto con ID " + id + " eliminato con successo.");
            } else {
                System.out.println("Nessun prodotto trovato con ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo transazionale richiesto
    public void aggiornaQuantità(int idProdotto, int nuovaQuantità, Connection conn) throws SQLException {
        String sql = "UPDATE prodotti SET quantita = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuovaQuantità);
            ps.setInt(2, idProdotto);
            ps.executeUpdate();
        }
    }
}
