package br.com.projetofinal.iceapp.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.adapter.CarrinhoListAdapter;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorCarrinho;

import br.com.projetofinal.iceapp.manager.GerenciadorUsuario;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Produto;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


/**
 * Created by Marcelo on 26/10/2016.
 */
public class CarrinhoItemActivity extends FragmentActivity  {

    @BindView(R.id.lv_carrinho)
    ListView lvCarrinho;
    CarrinhoListAdapter adapter;
    @BindView(R.id.btn_fazer_pedido)
    Button finalizarPedido;
    FormaPagamento choice = new FormaPagamento();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrinho_item_activity);
        ButterKnife.bind(this);
        Typeface font = Typeface.createFromAsset(getAssets(), "font3.ttf");
        finalizarPedido.setTypeface(font);

            adapter = new CarrinhoListAdapter(CarrinhoItemActivity.this, GerenciadorCarrinho.getListaProdutos());
            lvCarrinho.setAdapter(adapter);

    }

    @OnClick(R.id.btn_fazer_pedido)
    public void finalizarPedido(){
        choice.show(getFragmentManager(), "tag");
    }



    private final String TAG = "TCCMainActivity";

    @Override
    protected void onStart() {
        Log.i(TAG, "CHAMOU ONSTART - " +TAG );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "CHAMOU ONRESUME - " +TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "CHAMOU ONRESUME - " + TAG);
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "CHAMOU ONSTOP - " +TAG);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "CHAMOU ONDESTROY - " +TAG);
        super.onDestroy();
    }

}