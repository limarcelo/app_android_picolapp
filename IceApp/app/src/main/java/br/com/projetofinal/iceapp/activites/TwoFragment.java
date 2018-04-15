package br.com.projetofinal.iceapp.activites;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.manager.GerenciadorCarrinho;


/**
 * Created by Marcelo on 02/06/2016.
 */
public class TwoFragment extends Fragment {

    TextView txtTotal;
    TextView txtCar;

    public TwoFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        //Log.i(TAG, "CHAMOU ONRESUME - " + TAG);
        super.onResume();
        txtTotal.setText("Total: R$ "+GerenciadorCarrinho.calculaTotalCarrinho(GerenciadorCarrinho.getListaProdutos()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        txtTotal = (TextView) view.findViewById(R.id.text_total);
        txtCar = (TextView) view.findViewById(R.id.text_car);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font3.ttf");
        txtTotal.setTypeface(font);
        txtCar.setTypeface(font);

        ImageButton btnCar = (ImageButton) view.findViewById(R.id.btnCar);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GerenciadorCarrinho.getListaProdutos().isEmpty()) {
                    Toast.makeText(getActivity(), "Não há itens no carrinho", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), CarrinhoItemActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }




}

