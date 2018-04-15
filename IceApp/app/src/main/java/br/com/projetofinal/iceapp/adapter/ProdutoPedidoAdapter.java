package br.com.projetofinal.iceapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Produto;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Marcelo on 25/11/2016.
 */
public class ProdutoPedidoAdapter extends ArrayAdapter<Produto>{

    public ProdutoPedidoAdapter(Context context, List<Produto> produtoList){
        super(context, R.layout.item_produto_pedido, produtoList);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final  ViewHolder holder;

        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.item_produto_pedido, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Produto produto = getItem(position);
        if(produto!=null) {

            setImage(produto);
            holder.descricao.setText(produto.getDescricao());
            holder.imagem.setImageResource(produto.getImage());
            holder.quantidade.setText(produto.getQuantidade().toString());
        }

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.img_detalhe_produto)
        ImageView imagem;

        @BindView(R.id.tv_detalhe_descricao)
        TextView descricao;

        @BindView(R.id.tv_detalhe_quantidade)
        TextView quantidade;

    }

    public void setImage(Produto produto){
        if (produto.getDescricao().equals("Mousse de Maracujá")) {
            produto.setImage(R.drawable.maracuja);
        }
        if (produto.getDescricao().equals("Brigadeiro")) {
            produto.setImage(R.drawable.brigadeiro);
        }
        if (produto.getDescricao().equals("Dalito")) {
            produto.setImage(R.drawable.dalito);
        }
        if (produto.getDescricao().equals("Fruty")) {
            produto.setImage(R.drawable.fruty);
        }
        if (produto.getDescricao().equals("Morango com Leite Condensado")) {
            produto.setImage(R.drawable.morango_leite_condenado);
        }
        if (produto.getDescricao().equals("Ninho com Nutella")) {
            produto.setImage(R.drawable.ninho_nutela);
        }

    }
}
