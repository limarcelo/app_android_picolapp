package br.com.projetofinal.iceapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.model.Produto;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Marcelo on 22/10/2016.
 */
public class ProdutoListAdapter extends ArrayAdapter<Produto> {

    public ProdutoListAdapter(Context context, List<Produto> produtoList){
        super(context, R.layout.item_produto, produtoList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;

        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.item_produto, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Produto produto = getItem(position);
        if(produto!=null) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font3.ttf");
            holder.descricao.setTypeface(font);
            holder.tvPreco.setTypeface(font);
            holder.descricao.setText(produto.getDescricao());
            holder.imagem.setImageResource(produto.getImage());
            holder.tvPreco.setText("R$ "+produto.getValor());
        }
        return convertView;
    }


    class ViewHolder {

        @BindView(R.id.img)
        ImageView imagem;

        @BindView(R.id.tv_descricao)
        TextView descricao;

        @BindView(R.id.tv_preco)
        TextView tvPreco;
    }
}
