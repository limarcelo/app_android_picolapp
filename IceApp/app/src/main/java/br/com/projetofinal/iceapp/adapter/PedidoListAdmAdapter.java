package br.com.projetofinal.iceapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.model.Pedido;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Marcelo on 10/11/2016.
 */
public class PedidoListAdmAdapter extends ArrayAdapter<Pedido> {

    int aux=0;

    public PedidoListAdmAdapter(Context context, List<Pedido> pedidoList) {
        super(context, R.layout.item_pedido_adm, pedidoList);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;

        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.item_pedido_adm, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Pedido pedido = getItem(position);
            if(pedido!=null) {
                holder.tvCodigoPedido.setText(pedido.getIdPedido().toString());
                holder.tvStatusPedido.setText(pedido.getStatus());

                for(int i = 0; i < pedido.getListaProduto().size(); i++){
                    aux++;
                }

                holder.tvQtdPedido.setText(""+aux);
                aux=0;
                holder.tvTotalPedido.setText(""+pedido.getTotal());
                holder.tvTipoPagamento.setText(pedido.getTipoPagamento());
                holder.tvCliente.setText(pedido.getUsuario().getNome());
                holder.tvSetor.setText(pedido.getUsuario().getSetor());

                if(holder.tvTipoPagamento.getText().toString().equals("Pagar depois")){
                    holder.card.setCardBackgroundColor(Color.parseColor("#FFCCBC"));
                }else{
                    holder.card.setCardBackgroundColor(Color.parseColor("#BBDEFB"));
                }
            }



        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.tv_codigo_pedidoData_adm)
        TextView tvCodigoPedido;
        @BindView(R.id.tv_status_pedidoData_adm)
        TextView tvStatusPedido;
        @BindView(R.id.tv_qtd_pedidoData_adm)
        TextView tvQtdPedido;
        @BindView(R.id.tv_total_pedidoData_adm)
        TextView tvTotalPedido;
        @BindView(R.id.tv_tipo_pagamentoData_adm)
        TextView tvTipoPagamento;
        @BindView(R.id.tv_clienteData_adm)
        TextView tvCliente;
        @BindView(R.id.tv_setorData_adm)
        TextView tvSetor;
        @BindView(R.id.card_view_pedido_adm)
        CardView card;

    }

}
