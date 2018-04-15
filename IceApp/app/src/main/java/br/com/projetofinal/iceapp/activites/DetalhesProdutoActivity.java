package br.com.projetofinal.iceapp.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.manager.GerenciadorCarrinho;
import br.com.projetofinal.iceapp.model.Produto;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Marcelo on 25/10/2016.
 */
public class DetalhesProdutoActivity extends AppCompatActivity {

    @BindView(R.id.detalhe_img)
    ImageView imageView;
    @BindView(R.id.detalhe_tv_descricao)
    TextView tvDetalheDescricao;
    @BindView(R.id.detalhe_obs)
    TextView detalheObs;
    @BindView(R.id.tv_qtd)
    TextView tvQtd;
    @BindView(R.id.ed_qtd)
    EditText edQtd;
    @BindView(R.id.tv_preco)
    TextView preco;

    Produto produto;
    String qtd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_produto_activity);
        ButterKnife.bind(this);
        Typeface font = Typeface.createFromAsset(getAssets(), "font3.ttf");
        tvDetalheDescricao.setTypeface(font);
        detalheObs.setTypeface(font);
        tvQtd.setTypeface(font);
        edQtd.setTypeface(font);
        preco.setTypeface(font);

        Intent intent = getIntent();
        produto = (Produto)intent.getSerializableExtra("Produto");

        imageView.setImageResource(produto.getImage());
        tvDetalheDescricao.setText(produto.getDescricao());
        detalheObs.setText(produto.getObservacao());
        preco.setText("Preço: R$ "+String.valueOf(produto.getValor()));

    }

    @OnClick(R.id.ib_add_carrinho)
    public void addCarrinho(){

        qtd = edQtd.getText().toString();
        if(qtd.isEmpty()){
            Toast.makeText(DetalhesProdutoActivity.this, "Você deve informar a quantidade =(", Toast.LENGTH_SHORT).show();

        }else if(verificaCarrinho()==1) {
            Toast.makeText(DetalhesProdutoActivity.this, "V'oce já possui este item no carrinho =(", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesProdutoActivity.this)
                    .setTitle("Adicionar item")
                    .setMessage("Tem certeza que deseja adicionar o picolé de " + produto.getDescricao() + " ao carrinho?")
                    .setNegativeButton("Não", null)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int qtdInt = Integer.parseInt(edQtd.getText().toString());

                            produto.setQuantidade(qtdInt);
                            GerenciadorCarrinho.adicionar(produto);
                            finish();
                            Toast.makeText(DetalhesProdutoActivity.this, "Produto adicionado no carrinho =)", Toast.LENGTH_SHORT).show();

                        }
                    });
            alerta = builder.create();
            alerta.show();
        }

    }

    public int verificaCarrinho(){
        int aux=0;
        for(int i=0; i<GerenciadorCarrinho.getListaProdutos().size(); i++){
            if(GerenciadorCarrinho.getListaProdutos().get(i).getDescricao().equals(produto.getDescricao()) ) {
                aux = 1;
            }
        }
        return aux;
    }

}
