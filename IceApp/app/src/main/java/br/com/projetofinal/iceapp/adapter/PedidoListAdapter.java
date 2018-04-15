package br.com.projetofinal.iceapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.model.Pedido;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Marcelo on 10/11/2016.
 */
public class PedidoListAdapter extends ArrayAdapter<Pedido> {

    int aux=0;



    public PedidoListAdapter(Context context, List<Pedido> pedidoList) {
        super(context, R.layout.item_pedido, pedidoList);



    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;




        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.item_pedido, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Pedido pedido = getItem(position);

            if(pedido!=null) {
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font3.ttf");
                holder.tvCodigoPedido.setTypeface(font, Typeface.BOLD);
                holder.tvStatusPedido.setTypeface(font);
                holder.tvQtdPedido.setTypeface(font);
                holder.tvTotalPedido.setTypeface(font);
                holder.tvTipoPagamento.setTypeface(font);

                holder.tvCodigoPedidoT.setTypeface(font);
                holder.tvStatusPedidoT.setTypeface(font);
                holder.tvQtdPedidoT.setTypeface(font);
                holder.tvTotalPedidoT.setTypeface(font);
                holder.tvTipoPagamentoT.setTypeface(font);

                holder.tvCodigoPedido.setText(pedido.getIdPedido().toString());
                holder.tvStatusPedido.setText(pedido.getStatus());

                for(int i = 0; i < pedido.getListaProduto().size(); i++){
                        aux++;
                }
                holder.tvQtdPedido.setText(""+aux);
                aux=0;
                holder.tvTotalPedido.setText(""+pedido.getTotal());
                holder.tvTipoPagamento.setText(pedido.getTipoPagamento());

                if(holder.tvTipoPagamento.getText().toString().equals("Pagar depois")){
                    holder.card.setCardBackgroundColor(Color.parseColor("#FFCCBC"));
                }else{
                    holder.card.setCardBackgroundColor(Color.parseColor("#BBDEFB"));
                }

            }


        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.tv_codigo_pedidoData)
        TextView tvCodigoPedido;
        @BindView(R.id.tv_status_pedidoData)
        TextView tvStatusPedido;
        @BindView(R.id.tv_qtd_pedidoData)
        TextView tvQtdPedido;
        @BindView(R.id.tv_total_pedidoData)
        TextView tvTotalPedido;
        @BindView(R.id.tv_tipo_pagamentoData)
        TextView tvTipoPagamento;


        @BindView(R.id.tv_codigo_pedido)
        TextView tvCodigoPedidoT;
        @BindView(R.id.tv_status_pedido)
        TextView tvStatusPedidoT;
        @BindView(R.id.tv_qtd_pedido)
        TextView tvQtdPedidoT;
        @BindView(R.id.tv_total_pedido)
        TextView tvTotalPedidoT;
        @BindView(R.id.tv_tipo_pagamento)
        TextView tvTipoPagamentoT;
        @BindView(R.id.card_view_pedido)
        CardView card;

    }

}
