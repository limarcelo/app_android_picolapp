package br.com.projetofinal.iceapp.activites;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.projetofinal.iceapp.R;
import br.com.projetofinal.iceapp.activites.adm.CadastroProdutoFragment;
import br.com.projetofinal.iceapp.activites.adm.ContasReceberFragment;
import br.com.projetofinal.iceapp.activites.adm.ListaPedidosFragment;
import br.com.projetofinal.iceapp.autentication.LoginActivity;
import br.com.projetofinal.iceapp.manager.GerenciadorUsuario;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdmActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_adm, null);
        ((TextView) nav_header.findViewById(R.id.tv_emailAdm)).setText(GerenciadorUsuario.getUsuario().getEmail().toString());
        navigationView.addHeaderView(nav_header);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.adm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            GerenciadorUsuario.logoff();
            Intent intent = new Intent(AdmActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_cadastrar_produto) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new CadastroProdutoFragment())
                    .commit();
        } else if (id == R.id.nav_lista_pedidos) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ListaPedidosFragment())
                    .commit();
        } else if (id == R.id.nav_contas_receber) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ContasReceberFragment())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
