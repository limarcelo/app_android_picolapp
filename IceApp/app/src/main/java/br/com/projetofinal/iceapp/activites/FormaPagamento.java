package br.com.projetofinal.iceapp.activites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.style.TtsSpan;
import android.widget.Toast;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorCarrinho;
import br.com.projetofinal.iceapp.manager.GerenciadorProduto;
import br.com.projetofinal.iceapp.manager.GerenciadorUsuario;
import br.com.projetofinal.iceapp.model.Pedido;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


/**
 * Created by Marcelo on 31/10/2016.
 */
public class FormaPagamento extends DialogFragment {


    final CharSequence[] itens = {"Pagar agora", "Pagar depois"};
    String selecao;
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seu pedido totalizou R$ "+ GerenciadorCarrinho.calculaTotalCarrinho(GerenciadorCarrinho.getListaProdutos())+"        " +
                "      Como deseja efetuar o pagamento?").setSingleChoiceItems(itens, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        selecao = (String) itens[which];
                        break;
                    case 1:
                        selecao = (String) itens[which];
                        break;

                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(selecao==null) {
                    Toast.makeText(getActivity(), "Você deve selecionar a forma de pagamento", Toast.LENGTH_SHORT).show();
                }else{
                    Pedido pedido = new Pedido();
                    pedido.setStatus("Novo");
                    pedido.setDataPedido(new Date(System.currentTimeMillis()));
                    pedido.setUsuario(GerenciadorUsuario.getUsuario());

                    for(int i=0; i<GerenciadorCarrinho.getListaProdutos().size(); i++){
                        for(int j=0;j<GerenciadorCarrinho.getListaProdutos().get(i).getQuantidade(); j++){
                            GerenciadorProduto.adicionarComQtd(GerenciadorCarrinho.getListaProdutos().get(i));
                        }
                    }

                    pedido.setListaProduto(GerenciadorProduto.getListaComQtd());
                    pedido.setTotal(GerenciadorCarrinho.calculaTotalCarrinho(GerenciadorCarrinho.getListaProdutos()));
                    pedido.setTipoPagamento(selecao);

                    Call<Pedido> listPedido = ApiWeb.getRotas().adicionarPedido(pedido);
                    listPedido.enqueue(new Callback<Pedido>() {
                        @Override
                        public void onResponse(Response<Pedido> response) {
                        }

                        @Override
                        public void onFailure(Throwable t) {
                        }
                    });
                    GerenciadorProduto.removerTodosQtd();
                    GerenciadorCarrinho.removerTodos();
                    Toast.makeText(getActivity(), "Pedido Realizado! Verifique seus Pedidos", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        return builder.create();
    }
}
