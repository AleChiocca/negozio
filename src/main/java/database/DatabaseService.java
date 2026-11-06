package database;

import dao.ClienteDAO;
import dao.ProdottoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DatabaseService {

    private DatabaseManager db = new DatabaseManager();

    public void acquistaProdotti(int clienteId, int prodottoId, int quantitaRichiesta) throws SQLException {
        ClienteDAO clienteDAO = new ClienteDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        String sqlRicercaCliente = "SELECT * FROM clienti WHERE ID = ?";
        String sqlRicercaProdotto = "SELECT * FROM prodotti WHERE ID = ?";
        String sqlInserimentoOrdine = "INSERT INTO ordini (id_cliente, id_prodotto, quantita, data_ordine) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            try {
                double saldoCliente = 0.0;
                try (PreparedStatement ps = conn.prepareStatement(sqlRicercaCliente)) {
                    ps.setInt(1, clienteId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            saldoCliente = rs.getDouble("SALDO");
                        } else {
                            throw new SQLException("Cliente con ID " + clienteId + " non trovato.");
                        }
                    }
                }

                double prezzoProdotto = 0.0;
                int quantitaDisponibile = 0;
                try (PreparedStatement ps = conn.prepareStatement(sqlRicercaProdotto)) {
                    ps.setInt(1, prodottoId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            prezzoProdotto = rs.getDouble("PREZZO");
                            quantitaDisponibile = rs.getInt("quantita");
                        } else {
                            throw new SQLException("Prodotto con ID " + prodottoId + " non trovato.");
                        }
                    }
                }

                double costoTotale = prezzoProdotto * quantitaRichiesta;
                if (saldoCliente < costoTotale) {
                    throw new SQLException("Saldo insufficiente! Disponibile: €" + saldoCliente + ", Richiesto: €" + costoTotale);
                }
                if (quantitaDisponibile < quantitaRichiesta) {
                    throw new SQLException("Quantità in magazzino insufficiente! Disponibile: " + quantitaDisponibile);
                }

                try (PreparedStatement ps = conn.prepareStatement(sqlInserimentoOrdine)) {
                    ps.setInt(1, clienteId);
                    ps.setInt(2, prodottoId);
                    ps.setInt(3, quantitaRichiesta);
                    ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                    ps.executeUpdate();
                }

                int nuovaGiacenza = quantitaDisponibile - quantitaRichiesta;
                prodottoDAO.aggiornaQuantità(prodottoId, nuovaGiacenza, conn);

                double nuovoSaldo = saldoCliente - costoTotale;
                clienteDAO.aggiornaSaldo(clienteId, nuovoSaldo, conn);

                conn.commit();
                IO.println("Transazione completata con successo! Commit eseguito.");

            } catch (SQLException e) {
                conn.rollback();
                IO.println("Errore durante la transazione: rollback eseguito.");
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}