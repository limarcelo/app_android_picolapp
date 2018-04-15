package br.com.projetofinal.iceapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.adapter.ProdutoListAdapter;
import br.com.projetofinal.iceapp.manager.GerenciadorProduto;
import br.com.projetofinal.iceapp.model.Produto;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Marcelo on 23/10/2016.
 */
public class ProdutoListaActivity extends AppCompatActivity {

    @BindView(R.id.lv_produto)
    ListView lista;
    String descricao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_lista_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<Produto> produtLista = (List<Produto>) bundle.getSerializable("produtos");

        for(int i=0; i<produtLista.size(); i++){
            descricao = produtLista.get(i).getDescricao();
        }

        ProdutoListAdapter adapter = new ProdutoListAdapter(this, GerenciadorProduto.getListaProdutos());
        lista.setAdapter(adapter);

    }

    @OnClick(R.id.bt_fechar_lista)
    public void fecharLista(){
        finish();
    }
}
