package br.com.projetofinal.iceapp.activites.adm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorProduto;
import br.com.projetofinal.iceapp.model.Produto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Marcelo on 16/11/2016.
 */
public class CadastroProdutoFragment extends Fragment{

    List<Produto> produtoListCadastrados = new ArrayList<>();
    boolean aux = true;
    View myView;
    @BindView(R.id.et_cadastro_produto_nome)
    EditText etNome;
    @BindView(R.id.et_cadastro_produto_preco)
    EditText etPreco;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cadastro_produto_layout, container, false);
        ButterKnife.bind(this, myView);




        return myView;
    }

    @OnClick(R.id.btn_cadastrar_produto)
    public void cadastrarProduto(){

        if(etNome.getText().toString().isEmpty())  {
            etNome.setError("Informe o Produto!");
            etNome.requestFocus();
        }else if (etPreco.getText().toString().isEmpty()) {
            etPreco.setError("Informe o Preço!");
            etPreco.requestFocus();
        }else{

            Call<List<Produto>> requestProduto = ApiWeb.getRotas().listarProduto();
            requestProduto.enqueue(new Callback<List<Produto>>() {
                @Override
                public void onResponse(Response<List<Produto>> response) {
                    produtoListCadastrados = response.body();
                }

                @Override
                public void onFailure(Throwable t) {
                }

            });

            for (int i = 0; i < produtoListCadastrados.size(); i++) {
                if (produtoListCadastrados.get(i).getDescricao().equals(etNome.getText().toString())) {
                    aux = false;
                }
            }

            String obs = null;
            final Produto produto = new Produto();
            produto.setDescricao(etNome.getText().toString());
            produto.setValor(Double.parseDouble(etPreco.getText().toString()));
            obs = GerenciadorProduto.verificaNomeToObs(etNome.getText().toString());
            if(obs!=null){
                produto.setObservacao(obs);

                if(aux==false){
                    Toast.makeText(getActivity(), "Este produto já está cadastrado!", Toast.LENGTH_SHORT).show();
                }else{
                    Call<Produto> responseProduto = ApiWeb.getRotas().cadastrarProduto(produto);
                    responseProduto.enqueue(new Callback<Produto>() {
                        @Override
                        public void onResponse(Response<Produto> response) {
                            if (response.isSuccess()) {
                                Toast.makeText(getActivity(), "Cadastro Realizado Sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getActivity(), "Falha ao Cadastrar.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }else{
                Toast.makeText(getActivity(), "Este produto não existe.", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
