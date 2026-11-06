package dao;

import database.DatabaseManager;
import model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

public class ClienteDAOTest {

    @Test
    @DisplayName("Inserimento di un nuovo cliente")
    public void testInsert() {
        ClienteDAO dao = new ClienteDAO();
        Cliente cliente = new Cliente(0, "Mario", "Rossi", "mario.rossi@email.com", "Roma", 150.0);
        Assertions.assertDoesNotThrow(() -> dao.insert(cliente), "L'inserimento non dovrebbe sollevare eccezioni");
    }

    @Test
    @DisplayName("Ricerca cliente per ID esistente")
    public void testFindById() {
        ClienteDAO dao = new ClienteDAO();
        int idDaCercare = 4;
        Cliente cliente = dao.findById(idDaCercare);
        Assertions.assertNotNull(cliente, "Il cliente trovato non deve essere null");
        Assertions.assertEquals(idDaCercare, cliente.getId(), "L'ID del cliente deve corrispondere");
    }

    @Test
    @DisplayName("Ricerca di tutti i clienti")
    public void testFindAll() {
        ClienteDAO dao = new ClienteDAO();
        ArrayList<Cliente> clienti = dao.findAll();
        Assertions.assertNotNull(clienti, "La lista dei clienti non deve essere null");
        Assertions.assertTrue(clienti.size() >= 0, "La lista deve contenere elementi o essere vuota");
    }

    @Test
    @DisplayName("Aggiornamento saldo del cliente")
    public void testAggiornaSaldo() {
        ClienteDAO dao = new ClienteDAO();
        DatabaseManager dbManager = new DatabaseManager();

        int idCliente = 1;
        double nuovoSaldo = 500.0;

        Assertions.assertDoesNotThrow(() -> {
            try (Connection conn = dbManager.getConnection()) {
                dao.aggiornaSaldo(idCliente, nuovoSaldo, conn);
            }
        }, "L'aggiornamento del saldo non deve sollevare eccezioni");
    }

    @Test
    @DisplayName("Eliminazione cliente per ID")
    public void testDelete() {
        ClienteDAO dao = new ClienteDAO();
        int idDaEliminare = 300;
        Assertions.assertDoesNotThrow(() -> dao.delete(idDaEliminare), "L'eliminazione non deve sollevare eccezioni");
    }
}