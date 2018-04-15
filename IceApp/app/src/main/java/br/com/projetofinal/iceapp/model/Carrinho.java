package br.com.projetofinal.iceapp.model;

import java.io.Serializable;

/**
 * Created by Marcelo on 27/08/2016.
 */
public class Carrinho implements Serializable{

    private Integer idCarrinho;
    private Produto item;
    private int quantidadeItem;
    private double total;

    public Integer getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Integer idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Produto getItem() {
        return item;
    }

    public void setItem(Produto item) {
        this.item = item;
    }

    public int getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(int quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
