package br.com.projetofinal.iceapp.manager;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.model.Pedido;

/**
 * Created by Marcelo on 02/11/2016.
 */
public class GerenciadorPedido {

    private static List<Pedido> listaPedidos = new ArrayList<>();

    public static void adicionar(Pedido pedido){
        listaPedidos.add(pedido);
    }

    public static List<Pedido> getListaPedidos(){
        return listaPedidos;
    }
    public static void removerTodos() {
        listaPedidos.clear();
    }


}
