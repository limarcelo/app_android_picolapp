package br.com.projetofinal.iceapp.manager;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.model.Produto;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Marcelo on 23/10/2016.
 */
public class GerenciadorProduto {

    private static List<Produto> listaProdutos = new ArrayList<>();
    private static List<Produto> listaQuantidadeDisponivel = new ArrayList<>();
    private static List<String> produtoNomelist = Arrays.asList("Mousse de Maracujá", "Brigadeiro", "Dalito", "Fruty", "Morango com Leite Condensado", "Ninho com Nutella");
    private static List<String> produtoObslist = Arrays.asList("Picolé com sabor de mousse de maracujá e sementes da fruta", "Recheio de brigadeiro com cobertura crocante de chocolate granulado", "Picolé com recheio de doce de leite com cobertura de chocolate", "Picolé de groselha, o sabor da infância", "Picolé com recheio de lente condensado e cobertura de morango", "Recheio de Nutella com cobertura de Leite Ninho");
    private static List<Produto> produtoListCadastrados = new ArrayList<>();

    static boolean aux = true;

    public static void adicionar(Produto produto) {
        listaProdutos.add(produto);
    }

    public static List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public static void removerTodos() {
        listaProdutos.clear();
    }

    public static void adicionarComQtd(Produto produto) {
        listaQuantidadeDisponivel.add(produto);
    }

    public static List<Produto> getListaComQtd() {
        return listaQuantidadeDisponivel;
    }

    public static void removerTodosQtd() {
        listaQuantidadeDisponivel.clear();
    }

    public static String verificaNomeToObs(String nome) {
        String obs = null;
        for (int i = 0; i < produtoNomelist.size(); i++) {
            if (produtoNomelist.get(i).equals(nome)) {
                obs = produtoObslist.get(i);
            }
        }
        return obs;
    }

    public static boolean verificaProdutosCadastrados(final String nome) {


        return aux;
    }

}

