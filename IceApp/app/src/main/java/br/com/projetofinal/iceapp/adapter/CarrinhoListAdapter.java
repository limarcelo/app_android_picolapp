package br.com.projetofinal.iceapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.activites.CarrinhoItemActivity;
import br.com.projetofinal.iceapp.activites.HomeActivity;
import br.com.projetofinal.iceapp.manager.GerenciadorCarrinho;
import br.com.projetofinal.iceapp.model.Carrinho;
import br.com.projetofinal.iceapp.model.Produto;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Marcelo on 26/10/2016.
 */
public class CarrinhoListAdapter extends ArrayAdapter<Produto> {



    public CarrinhoListAdapter(Context context, List<Produto> carrinhoList){
        super(context, R.layout.item_carrinho, carrinhoList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.item_carrinho, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);


        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Produto produto = getItem(position);
        if(produto!=null) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font3.ttf");
            holder.descricaoItemCarrinho.setTypeface(font);
            holder.qtdItemCarrinho.setTypeface(font);
            holder.valorItemCarrinho.setTypeface(font);
            holder.tvQtdItem.setTypeface(font);
            holder.tvValorItem.setTypeface(font);

            holder.descricaoItemCarrinho.setText(produto.getDescricao());
            holder.imagemItemCarrinho.setImageResource(produto.getImage());
            holder.qtdItemCarrinho.setText(produto.getQuantidade().toString());
            holder.valorItemCarrinho.setText(GerenciadorCarrinho.totalItem(produto).toString());
            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = new AlertDialog.Builder(getContext())
                            .setTitle("Excluir")
                            .setMessage("Deseja realmente excluir este item?")
                            .setNegativeButton("Não", null)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, final int i) {
                                    GerenciadorCarrinho.getListaProdutos().remove(produto);
                                    if( GerenciadorCarrinho.getListaProdutos().isEmpty()){
                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                        getContext().startActivity(intent);

                                    }
                                    notifyDataSetChanged();

                                }
                            }).show();

                }
            });
        }
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.img_item_carrinho)
        ImageView imagemItemCarrinho;

        @BindView(R.id.tv_descricao_item_carrinho)
        TextView descricaoItemCarrinho;

        @BindView(R.id.tv_qtd_item_carrinho)
        TextView qtdItemCarrinho;

        @BindView(R.id.tv_valor_item_carrinho)
        TextView valorItemCarrinho;

        @BindView(R.id.ib_delete)
        ImageButton ibDelete;

        @BindView(R.id.tv_quantidade_item)
        TextView tvQtdItem;

        @BindView(R.id.tv_valor_item)
        TextView tvValorItem;

    }

}
