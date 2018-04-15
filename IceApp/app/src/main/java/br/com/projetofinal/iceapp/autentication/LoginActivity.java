package br.com.projetofinal.iceapp.autentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.regex.Pattern;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.activites.AdmActivity;
import br.com.projetofinal.iceapp.activites.HomeActivity;
import br.com.projetofinal.iceapp.api.ApiWeb;
import br.com.projetofinal.iceapp.manager.GerenciadorUsuario;
import br.com.projetofinal.iceapp.model.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.et_pw)
    EditText pw;
    @BindView(R.id.btn_entrar)
    Button entrar;
    @BindView(R.id.tv_register)
    TextView register;
    @BindView(R.id.tv_register2)
    TextView register2;
    @BindView(R.id.chkSalvar)
    CheckBox chkSalvar;


    public static final String PREFS_NAME = "Preferencias";
    public static final String PERMISSION_USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Typeface font = Typeface.createFromAsset(getAssets(), "font3.ttf");
        tvName.setTypeface(font);
        edEmail.setTypeface(font);
        pw.setTypeface(font);
        entrar.setTypeface(font);
        register.setTypeface(font);
        register2.setTypeface(null, Typeface.BOLD_ITALIC);
        register2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        chkSalvar.setTypeface(font);
        chkSalvar.setChecked(true);

        //Restaura as preferencias gravadas
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        edEmail.setText(settings.getString("PrefUsuario", ""));
        pw.setText(settings.getString("PrefSenha", ""));

    }

    @OnClick(R.id.tv_register)
    public void telaCadastroUsuario2(){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.tv_register2)
    public void telaCadastroUsuario(){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_entrar)
    public void logar(){

        if(!validaEmail(edEmail.getText().toString()) || edEmail.getText().toString().isEmpty()) {
            edEmail.setError("Email inválido!");
            edEmail.requestFocus();
        }else if (pw.getText().toString().isEmpty() || pw.length()<6){
            pw.setError("Senha inválida!");
            pw.requestFocus();
        }else{
            Credenciais credencial = new Credenciais();

            credencial.setEmail(edEmail.getText().toString());
            credencial.setSenha(pw.getText().toString());

            Call<Usuario> requestCredenciais = ApiWeb.getRotas().autenticarUsuario(credencial);
            requestCredenciais.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Response<Usuario> response) {
                    if (response.isSuccess()){
                        Usuario usuarioAutenticado = response.body();
                        GerenciadorUsuario.adicionarUsuario(usuarioAutenticado);
                        if(usuarioAutenticado.getPermission().equals(PERMISSION_USER)){

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Você entrou como Usuario!", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        else{
                            Intent intent = new Intent(LoginActivity.this, AdmActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Você entrou como Administrador!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, "Usuário ou senha inválida!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(LoginActivity.this, "Falha ao autenticar", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public boolean validaEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Caso o checkbox esteja marcado gravamos o usuário
        if (chkSalvar.isChecked()){
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("PrefUsuario", edEmail.getText().toString());
            editor.putString("PrefSenha", pw.getText().toString());

            //Confirma a gravação dos dados
            editor.commit();
        }
    }

}
