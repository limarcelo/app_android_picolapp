package br.com.projetofinal.iceapp.activites.adm;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.adapter.ContasReceberListAdapter;
import br.com.projetofinal.iceapp.adapter.PedidoListAdapter;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorPedido;
import br.com.projetofinal.iceapp.manager.GerenciadorUsuario;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Marcelo on 16/11/2016.
 */
public class ContasReceberFragment extends Fragment{

    View myView;
    @BindView(R.id.lv_contas_receber)
    ListView lvContasReceber;
    List<Usuario> listTodosUsuario = new ArrayList<>();
    List<Pedido> listTodosPedidos = new ArrayList<>();
    ContasReceberListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contas_receber_layout, container, false);
        ButterKnife.bind(this, myView);

        Call<List<Usuario>> requestUsuarios = ApiWeb.getRotas().buscarTodos();
        requestUsuarios.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Response<List<Usuario>> response) {
                if (response.isSuccess()) {
                    listTodosUsuario = response.body();
                    for (int i = 0; i < listTodosUsuario.size(); i++) {
                        GerenciadorUsuario.adicionar(listTodosUsuario.get(i));
                    }
                    adapter = new ContasReceberListAdapter(getActivity(), GerenciadorUsuario.getListaUsuarios());
                    lvContasReceber.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        GerenciadorUsuario.removerTodos();

        lvContasReceber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GerenciadorPedido.removerTodos();
                final Usuario usuario = (Usuario) parent.getItemAtPosition(position);

                Call<List<Pedido>> responseListPedido = ApiWeb.getRotas().listarPedidoContasPagar(usuario.getIdUsuario());
                responseListPedido.enqueue(new Callback<List<Pedido>>() {
                    @Override
                    public void onResponse(Response<List<Pedido>> response) {
                        if (response.isSuccess() && !(response.body().size() == 0)) {
                            listTodosPedidos = response.body();
                            for (int i = 0; i < listTodosPedidos.size(); i++) {
                                GerenciadorPedido.adicionar(listTodosPedidos.get(i));
                            }
                            Intent intent = new Intent(getActivity(), ContasReceberDetalhe.class);
                            intent.putExtra("Usuario", usuario);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Não há Contas a Receber para este cliente", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                //GerenciadorPedido.removerTodos();
            }

        });


        return myView;
    }
}
