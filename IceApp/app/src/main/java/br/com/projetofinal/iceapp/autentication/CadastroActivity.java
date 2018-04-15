package br.com.projetofinal.iceapp.autentication;

import android.content.Intent;

import android.graphics.Typeface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CadastroActivity extends AppCompatActivity {

    private String[] setores = new String[]{"T.I","Jurídico", "Financeiro", "A. Integrado", "Protocolo", "Contas Médicas", "Ambulatório", "Odontologia"};

    @BindView(R.id.et_nome_cadastro)
    EditText etNomeCadastro;
    @BindView(R.id.et_email_cadastro)
    EditText etEmailCadastro;
    @BindView(R.id.setor)
    Spinner ddSetor;
    @BindView(R.id.et_pwCadastro)
    EditText pwCadastro;
    @BindView(R.id.et_pwCadastro2)
    EditText pwCadastro2;

    @BindView(R.id.tv_titulo)
    TextView tvTituloCadastro;
    @BindView(R.id.tv_seguranca)
    TextView tvSeguranca;
    @BindView(R.id.tv_titulodd)
    TextView tvTituloDD;
    @BindView(R.id.btn_cadastrar)
    Button btnCadastrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);
        final Typeface font = Typeface.createFromAsset(getAssets(), "font3.ttf");
        etNomeCadastro.setTypeface(font);
        etEmailCadastro.setTypeface(font);
        pwCadastro.setTypeface(font);
        pwCadastro2.setTypeface(font);
        tvTituloCadastro.setTypeface(font);
        tvSeguranca.setTypeface(font);
        tvTituloDD.setTypeface(font);
        btnCadastrar.setTypeface(font);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,setores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddSetor.setAdapter(adapter);

        ddSetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ddSetor.getSelectedItem().toString().equals(null)) {
                    ((TextView) parent.getChildAt(0)).setText("Informe o setor:");
                }

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.windowBackground));
                ((TextView) parent.getChildAt(0)).setTypeface(font);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.btn_cadastrar)
    public void realizarCadastro(){

        if(etNomeCadastro.getText().toString().isEmpty() ||etNomeCadastro.length()<3) {
            etNomeCadastro.setError("Nome inválido!");
            etNomeCadastro.requestFocus();
        }else if(!validaEmail(etEmailCadastro.getText().toString()) || etEmailCadastro.getText().toString().isEmpty()){
            etEmailCadastro.setError("Email inválido!");
            etEmailCadastro.requestFocus();
        }else if(pwCadastro==null || pwCadastro.length()<6){
            pwCadastro.setError("Deve conter mais de 6 dígitos!");
            pwCadastro.requestFocus();
        }else if(!pwCadastro.getText().toString().equals(pwCadastro2.getText().toString())){
            pwCadastro2.setError("A senha não confere!");
            pwCadastro2.requestFocus();
        }else{


            Usuario usuario = new Usuario();
            usuario.setNome(etNomeCadastro.getText().toString());
            usuario.setEmail(etEmailCadastro.getText().toString());
            usuario.setSetor(ddSetor.getSelectedItem().toString());
            usuario.setSenha(pwCadastro.getText().toString());
            //enviaEmail(usuario);
            Call<Usuario> responseUsuario = ApiWeb.getRotas().cadastrarUsuario(usuario);
            responseUsuario.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Response<Usuario> response) {
                    if (response.isSuccess()) {
                        Toast.makeText(CadastroActivity.this, "Cadastro Realizado Sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(CadastroActivity.this, "Falha ao Salvar.", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


    public boolean validaEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();

    }

    public void enviaEmail(Usuario usuario){
        String body = "PICOLAP - CADASTRO REALIZADO";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{usuario.getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Cadastro Completo");
        intent.putExtra(Intent.EXTRA_TEXT, body );

        try{
            startActivity(intent.createChooser(intent, "Enviando email"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
