package br.com.projetofinal.iceapp.manager;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.model.Produto;

/**
 * Created by Marcelo on 25/10/2016.
 */
public class GerenciadorCarrinho {
    private static List<Produto> listaProdutos = new ArrayList<>();
    private static Double totalCarrinho = 0.00;

    public static void adicionar(Produto produto) {
        listaProdutos.add(produto);
    }

    public static List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public static void removerTodos() {
        listaProdutos.clear();
    }

    public static Double totalItem(Produto produto) {
        Double totalItem;
        totalItem = produto.getValor() * produto.getQuantidade();
        return totalItem;

    }

    public static Double calculaTotalCarrinho(List<Produto> listaProdutosQuantidade) {
        totalCarrinho=0.0;
        for (int i = 0; i < listaProdutosQuantidade.size(); i++) {
            totalCarrinho = totalCarrinho + (listaProdutosQuantidade.get(i).getValor()*listaProdutosQuantidade.get(i).getQuantidade());
        }
        return totalCarrinho;

    }
}
