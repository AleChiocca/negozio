package org.example;

import dao.*;
import database.DatabaseService;
import model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        IO.println("=== INIZIO TEST DEL SISTEMA DAO & TRANSAZIONI ===");

        // Inizializzazione dei DAO e dei Servizi
        ClienteDAO clienteDAO = new ClienteDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        OrdineDAO ordineDAO = new OrdineDAO();
        DatabaseService dbService = new DatabaseService();

        IO.println();
        IO.println("==============TEST CLIENTE=================");
        ArrayList<Cliente> clienti = clienteDAO.findAll();
        IO.println("Elenco clienti nel database (" + clienti.size() + "):");
        for (Cliente c : clienti) {
            IO.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " " + c.getCognome() + " | Città: " + c.getCitta() + " | Saldo: €" + c.getSaldo());
        }



        IO.println();
        IO.println("==============TEST PRODOTTO=================");
        ArrayList<Prodotto> prodotti = prodottoDAO.findAll();
        IO.println("Elenco prodotti nel database (" + prodotti.size() + "):");
        for (Prodotto p : prodotti) {
            IO.println("ID: " + p.getId() + " | Prodotto: " + p.getNome() + " | Categoria: " + p.getCategoria() + " | Prezzo: €" + p.getPrezzo());
        }


        IO.println();
        IO.println("==============TEST ORDINE=================");
        ArrayList<Ordine> ordini = ordineDAO.findAll();
        IO.println("Elenco ordini nel database: " + ordini.size());
        for (Ordine o : ordini) {
            IO.println("ID Ordine: " + o.getId() + " | ID Cliente: " + o.getIdCliente() + " | ID Prodotto: " + o.getIdProdotto() + " | Quantità: " + o.getQuantita() + " | Data: " + o.getDataOrdine());
        }



        IO.println();
        IO.println("=================TEST ACQUISTA PRODOTTI (TRANSAZIONE)========================");

        try {
            // Esempio: Cliente con ID 1 acquista il Prodotto con ID 3, quantità 2
            // Esegue in automatico le 5 query atomiche (ricerca cliente, ricerca prodotto, inserimento ordine, update giacenza, update saldo) con commit/rollback
            dbService.acquistaProdotti(1, 3, 2);

            IO.println("ACQUISTATO CON SUCCESSO! Transazione confermata.");

        } catch (SQLException e) {
            IO.println("Errore nell'esecuzione dell'acquisto (Rollback eseguito): " + e.getMessage());
        }

        IO.println("===================================================");
        IO.println("==============FINE DI TUTTI I TEST=================");
    }
}