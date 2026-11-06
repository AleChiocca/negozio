package model;

import java.sql.Date;

public class Ordine {
    private int id;
    private int idCliente;
    private int idProdotto;
    private int quantita;
    private Date dataOrdine;

    public Ordine() {
    }

    public Ordine(int id, int idCliente, int idProdotto, int quantita, Date dataOrdine) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.dataOrdine = dataOrdine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }
}