package br.com.projetofinal.iceapp.activites.adm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorPedido;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Marcelo on 28/11/2016.
 */
public class ContasReceberDetalhe extends AppCompatActivity {

    Usuario usuario;
    @BindView(R.id.tv_cr_cliente)
    TextView tvCliente;
    @BindView(R.id.tv_cr_total)
    TextView tvTotal;
    double total;
    Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_contas_receber);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        usuario = (Usuario)intent.getSerializableExtra("Usuario");
        tvCliente.setText(usuario.getNome());
        total = 0;

        for(int i=0; i<GerenciadorPedido.getListaPedidos().size();i++){
            total = total+GerenciadorPedido.getListaPedidos().get(i).getTotal();
        }
        tvTotal.setText("" + total);

    }

    @OnClick(R.id.btn_receber)
    public void receberContas(){

        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(ContasReceberDetalhe.this)
                .setTitle("Contas a receber")
                .setMessage("Tem certeza que deseja receber do cliente?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<GerenciadorPedido.getListaPedidos().size(); i++){
                           GerenciadorPedido.getListaPedidos().get(i).setTipoPagamento("Pago");
                            Call<Pedido> responsePedido = ApiWeb.getRotas().editarStatus(GerenciadorPedido.getListaPedidos().get(i));
                            responsePedido.enqueue(new Callback<Pedido>() {
                                @Override
                                public void onResponse(Response<Pedido> response) {
                                    if (response.isSuccess()) {
                                        GerenciadorPedido.removerTodos();
                                        finish();
                                        Toast.makeText(ContasReceberDetalhe.this, "Valores Recebidos", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    Toast.makeText(ContasReceberDetalhe.this, "Falhou", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
        alerta = builder.create();
        alerta.show();


    }

    @OnClick(R.id.btn_enviar_email)
    public void enviarCobranca(){

        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(ContasReceberDetalhe.this)
                .setTitle("Enviar Cobrança")
                .setMessage("Tem certeza que deseja enviar cobrança")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GerenciadorPedido.getListaPedidos().get(0).setTotal(total);

                        Call<Usuario> responseUsuario = ApiWeb.getRotas().enviarCobranca(GerenciadorPedido.getListaPedidos().get(0));
                            responseUsuario.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Response<Usuario> response) {
                                if (response.isSuccess()) {
                                    //GerenciadorPedido.removerTodos();
                                    Toast.makeText(ContasReceberDetalhe.this, "Cobrança enviada para o email do cliente", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(ContasReceberDetalhe.this, "Falhou", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
        alerta = builder.create();
        alerta.show();


    }
}
