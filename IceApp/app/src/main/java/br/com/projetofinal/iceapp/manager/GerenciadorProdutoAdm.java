package br.com.projetofinal.iceapp.manager;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.model.Produto;
import br.com.projetofinal.iceapp.model.ProdutoAdm;

/**
 * Created by Marcelo on 27/11/2016.
 */
public class GerenciadorProdutoAdm {

    private static List<Produto>listaProdutos = new ArrayList<>();

    public static List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public static void add(Produto produto) {
        GerenciadorProdutoAdm.listaProdutos.add(produto);
    }

    public static void removerTodos() {
        listaProdutos.clear();
    }
}
