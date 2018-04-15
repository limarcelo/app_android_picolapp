package br.com.projetofinal.iceapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.adapter.ProdutoListAdapter;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorProduto;
import br.com.projetofinal.iceapp.model.Produto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


/**
 * Created by Marcelo on 02/06/2016.
 */
public class OneFragment extends Fragment {

    @BindView(R.id.lv_produto)
    ListView listView;

    List<Produto> produtoList;
    private Unbinder unbinder;

    ProdutoListAdapter adapter;

    public OneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(getActivity(), view);

        Call<List<Produto>> requestProduto = ApiWeb.getRotas().listarProduto();
        requestProduto.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Response<List<Produto>> response) {
                GerenciadorProduto.removerTodos();
                produtoList = new ArrayList<>(response.body());

                for (int i = 0; i < produtoList.size(); i++) {
                    Produto produto = new Produto();
                    produto.setDescricao(produtoList.get(i).getDescricao());
                    produto.setValor(produtoList.get(i).getValor());
                    produto.setIdProduto(produtoList.get(i).getIdProduto());
                    produto.setObservacao(produtoList.get(i).getObservacao());

                    if (produto.getDescricao().equals("Mousse de Maracujá")) {
                        produto.setImage(R.drawable.maracuja);
                    }
                    if (produto.getDescricao().equals("Brigadeiro")) {
                        produto.setImage(R.drawable.brigadeiro);
                    }
                    if (produto.getDescricao().equals("Dalito")) {
                        produto.setImage(R.drawable.dalito);
                    }
                    if (produto.getDescricao().equals("Fruty")) {
                        produto.setImage(R.drawable.fruty);
                    }
                    if (produto.getDescricao().equals("Morango com Leite Condensado")) {
                        produto.setImage(R.drawable.morango_leite_condenado);
                    }
                    if (produto.getDescricao().equals("Ninho com Nutella")) {
                        produto.setImage(R.drawable.ninho_nutela);
                    }

                    GerenciadorProduto.adicionar(produto);
                }
                adapter = new ProdutoListAdapter(getContext(), GerenciadorProduto.getListaProdutos());
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Produto produto = (Produto) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalhesProdutoActivity.class);
                intent.putExtra("Produto", produto);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
