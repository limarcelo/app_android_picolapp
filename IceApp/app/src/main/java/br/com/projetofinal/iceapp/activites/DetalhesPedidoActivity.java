package br.com.projetofinal.iceapp.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.adapter.ProdutoPedidoAdapter;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorProdutoAdm;
import br.com.projetofinal.iceapp.model.Pedido;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Marcelo on 25/11/2016.
 */
public class DetalhesPedidoActivity extends AppCompatActivity {

    Pedido pedido;

    @BindView(R.id.tv_detalhe_codigo_pedidoData)
    TextView tvCodigoPedido;
    @BindView(R.id.tv_detalhe_cliente_pedidoData)
    TextView tvCliente;
    @BindView(R.id.tv_detalhe_setor_pedidoData)
    TextView tvSetor;
    @BindView(R.id.tv_total_pedidoData)
    TextView tvTotal;
    @BindView(R.id.tv_forma_pagamento_pedidoData)
    TextView tvFormaPagamento;
    @BindView(R.id.card_view_detalhe_pedido_adm)
    CardView card;
    @BindView(R.id.lv_produto_pedido)
    ListView lvProdutoPedido;
    ProdutoPedidoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedido);
        ButterKnife.bind(this);

        adapter = new ProdutoPedidoAdapter(this, GerenciadorProdutoAdm.getListaProdutos());
        lvProdutoPedido.setAdapter(adapter);

        Intent intent = getIntent();
        pedido = (Pedido) intent.getSerializableExtra("pedido");

        tvCodigoPedido.setText(pedido.getIdPedido().toString());
        tvCliente.setText(pedido.getUsuario().getNome().toString());
        tvSetor.setText(pedido.getUsuario().getSetor().toString());
        tvTotal.setText(""+pedido.getTotal());
        tvFormaPagamento.setText(pedido.getTipoPagamento().toString());
        if(pedido.getTipoPagamento().equals("Pagar depois")){
            card.setCardBackgroundColor(Color.parseColor("#FFCCBC"));
        }else{
            card.setCardBackgroundColor(Color.parseColor("#BBDEFB"));
        }
    }


    @OnClick(R.id.btn_entrega)
    public void realizaEntrega(){

        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesPedidoActivity.this)
                .setTitle("Realizar Entrega")
                .setMessage("Tem certeza que deseja Realizar entrega dos produtos?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        pedido.setStatus("Entregue");
                        if(pedido.getTipoPagamento().equals("Pagar agora")){
                            pedido.setTipoPagamento("Pago");
                        }

                        Call<Pedido> responsePedido = ApiWeb.getRotas().editarStatus(pedido);
                        responsePedido.enqueue(new Callback<Pedido>() {
                            @Override
                            public void onResponse(Response<Pedido> response) {
                                if (response.isSuccess()) {
                                    finish();
                                    Toast.makeText(DetalhesPedidoActivity.this, "Pedido Finalizado", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(DetalhesPedidoActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
        alerta = builder.create();
        alerta.show();

    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}
