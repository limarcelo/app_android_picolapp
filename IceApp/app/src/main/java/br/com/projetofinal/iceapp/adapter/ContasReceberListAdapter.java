package br.com.projetofinal.iceapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Marcelo on 28/11/2016.
 */
public class ContasReceberListAdapter extends ArrayAdapter<Usuario> {

    public ContasReceberListAdapter(Context context, List<Usuario> usuarioList) {
        super(context, R.layout.activity_todos_usuarios, usuarioList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;

        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.activity_todos_usuarios, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Usuario usuario = getItem(position);
        if(usuario!=null){
            holder.tvNomeCliente.setText(usuario.getNome().toString());
            holder.tvSetor.setText(usuario.getSetor().toString());
            holder.tvEmail.setText(usuario.getEmail().toString());
        }

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.tv_nome_cliente_crData)
        TextView tvNomeCliente;
        @BindView(R.id.tv_setor_crData)
        TextView tvSetor;
        @BindView(R.id.tv_email_crData)
        TextView tvEmail;

    }
}
