package br.com.projetofinal.iceapp.activites.adm;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.activites.DetalhesPedidoActivity;
import br.com.projetofinal.iceapp.activites.DetalhesProdutoActivity;
import br.com.projetofinal.iceapp.adapter.PedidoListAdapter;
import br.com.projetofinal.iceapp.adapter.PedidoListAdmAdapter;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorPedido;
import br.com.projetofinal.iceapp.manager.GerenciadorProduto;
import br.com.projetofinal.iceapp.manager.GerenciadorProdutoAdm;
import br.com.projetofinal.iceapp.manager.GerenciadorUsuario;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Produto;
import br.com.projetofinal.iceapp.model.ProdutoAdm;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Marcelo on 16/11/2016.
 */
public class ListaPedidosFragment extends Fragment{

    @BindView(R.id.lv_todos_pedidos)
    ListView listView;
    List<Pedido> listTodosPedido;
    PedidoListAdmAdapter adapter;
    View myView;

    private final String TAG = "TCCFRAGMENT";

    @Override
    public void onStart() {
        Log.i(TAG, "CHAMOU ONSTART - " + TAG);
        super.onStart();
    }


    @Override
    public void onPause() {
        Log.i(TAG, "CHAMOU ONRESUME - " + TAG);
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "CHAMOU ONSTOP - " +TAG);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "CHAMOU ONDESTROY - " +TAG);
        super.onDestroy();
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "CHAMOU ONRESUME - " +TAG);
        Call<List<Pedido>> responseListPedido = ApiWeb.getRotas().listarTodosPedido();
        responseListPedido.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Response<List<Pedido>> response) {
                if(response.isSuccess()){
                    listTodosPedido = new ArrayList<>(response.body());
                    for (int i = 0; i < listTodosPedido.size(); i++) {
                        GerenciadorPedido.adicionar(listTodosPedido.get(i));
                    }
                    adapter = new PedidoListAdmAdapter(getActivity(), GerenciadorPedido.getListaPedidos());
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        GerenciadorPedido.removerTodos();

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pedidos_layout, container, false);
        ButterKnife.bind(this, myView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pedido pedido = (Pedido) parent.getItemAtPosition(position);
                Produto produtoComQtd;
                GerenciadorProdutoAdm.removerTodos();

                for (int i = 0; i < pedido.getListaProduto().size(); i++) {

                    produtoComQtd = pedido.getListaProduto().get(i);
                    int cont = 0;

                    for (int j = 0; j < pedido.getListaProduto().size(); j++) {

                        if (produtoComQtd.getIdProduto() == pedido.getListaProduto().get(j).getIdProduto()) {
                            cont++;
                            produtoComQtd.setQuantidade(cont);
                        }

                    }

                    if ( GerenciadorProdutoAdm.getListaProdutos().isEmpty()) {
                        GerenciadorProdutoAdm.add(produtoComQtd);
                    } else {

                        if ((GerenciadorProdutoAdm.getListaProdutos().get(GerenciadorProdutoAdm.getListaProdutos().size() - 1).getIdProduto() != produtoComQtd.getIdProduto())) {
                            GerenciadorProdutoAdm.add(produtoComQtd);
                        }

                    }

                }

                Intent intent = new Intent(getActivity(), DetalhesPedidoActivity.class);
                intent.putExtra("pedido", pedido);

                startActivity(intent);
            }
        });

        return myView;
    }
}
