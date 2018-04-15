package br.com.projetofinal.iceapp.activites;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.adapter.PedidoListAdapter;
import br.com.projetofinal.iceapp.adapter.ProdutoListAdapter;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorPedido;
import br.com.projetofinal.iceapp.manager.GerenciadorProduto;
import br.com.projetofinal.iceapp.manager.GerenciadorUsuario;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


/**
 * Created by Marcelo on 02/06/2016.
 */
public class ThreeFragment extends Fragment {

    @BindView(R.id.lv_pedido)
    ListView listView;

    List<Pedido> listPedido;
    PedidoListAdapter adapter;


    public ThreeFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this, view);



        Call<List<Pedido>> responseListPedido = ApiWeb.getRotas().listarPedidoPorUser(GerenciadorUsuario.getUsuario().getIdUsuario());
        responseListPedido.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Response<List<Pedido>> response) {
                if (response.isSuccess()) {
                    listPedido = new ArrayList<>(response.body());

                        for (int i = 0; i < listPedido.size(); i++) {
                            GerenciadorPedido.adicionar(listPedido.get(i));
                        }
                        adapter = new PedidoListAdapter(getContext(), GerenciadorPedido.getListaPedidos());
                        listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        GerenciadorPedido.removerTodos();
        return view;
    }

}
