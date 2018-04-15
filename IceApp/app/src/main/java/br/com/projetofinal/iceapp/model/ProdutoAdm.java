package br.com.projetofinal.iceapp.model;

import java.io.Serializable;

/**
 * Created by Marcelo on 27/11/2016.
 */
public class ProdutoAdm implements Serializable {

    private int idProduto;
    private String descricao;
    private double valor;
    private int image;
    private String observacao;
    private Integer quantidade;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public ProdutoAdm(String descricao, int image) {
        this.descricao = descricao;
        this.image = image;
    }

    public ProdutoAdm(){
    }

    public ProdutoAdm(Produto produto){
        this.setIdProduto(produto.getIdProduto());
        this.setDescricao(produto.getDescricao());
        this.setValor(produto.getValor());
        this.setImage(produto.getImage());
        this.setObservacao(produto.getObservacao());
    }


    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getImage() { return image;  }

    public void setImage(int image) { this.image = image; }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


}
