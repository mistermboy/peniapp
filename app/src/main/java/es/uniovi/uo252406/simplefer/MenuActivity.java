package es.uniovi.uo252406.simplefer;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Entities.Player;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<String> audios;
    Button btnPrincipal;
    VideoView vView;
    Uri uri;

    public final static int FREE_ACTIVITY=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Crea los audios correspondientes y asigna la funcionalidad al boton
        createAudios("fer");
        asignaFuncionalidades();

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Crea el ArrayList con el nombre de los audios.
     * Escoge solo los audios que pertenezcan a la persona que se le pasa.
     * Establece un límite en el reproductor
     * Se llama cuando se crea el activity.
     */
    private void createAudios(String person) {
        audios = new ArrayList<>();
        int numAudios = 0;
        for (Field f : R.raw.class.getFields()) {
            //Es  un audio de la persona que estamos buscando?
            if (f.getName().split("_")[0].equals(person)) {
                audios.add(f.getName());
                numAudios++;
            }

        }
        Player.getInstance().setMaxValor(numAudios);
    }


    /**
     * Asigna funcionalidad a los botones
     */
    private void asignaFuncionalidades() {
        btnPrincipal = findViewById(R.id.btnPrincipal);

        vView = (VideoView) findViewById(R.id.videoView);
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fervideo);


        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                vView.setVideoURI(uri);

                vView.start();

                if (Player.getInstance() != null)
                    Player.getInstance().mpNull();
                Player.getInstance().changeAudio(getBaseContext(), audios);
                try {
                    Player.getInstance().start();
                } catch (IllegalStateException e) {
                    Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
                }
            }
        });


    }
}
