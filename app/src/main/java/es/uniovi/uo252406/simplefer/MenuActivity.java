package es.uniovi.uo252406.simplefer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.uniovi.uo252406.simplefer.Entities.Player;
import es.uniovi.uo252406.simplefer.Fragments.FreeSelectionFragment;
import es.uniovi.uo252406.simplefer.Fragments.PrincipalPersonFragment;
import es.uniovi.uo252406.simplefer.Fragments.QuizFragment;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String person;

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

        //Recogemos los datos
        Bundle b = getIntent().getExtras();
        person = (String) b.getString("person");

        asignaInfo(navigationView);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.escenario,new PrincipalPersonFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        Player.getInstance().pause();
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

        Player.getInstance().pause();

        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.perfil) {
            fm.beginTransaction().replace(R.id.escenario,new PrincipalPersonFragment()).commit();
        } else if (id == R.id.audios) {
            fm.beginTransaction().replace(R.id.escenario,new FreeSelectionFragment()).commit();
        }
        else if (id == R.id.quiz) {
            fm.beginTransaction().replace(R.id.escenario,new QuizFragment()).commit();
        }
        else if (id == R.id.inicio) {
            finish();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Asigna información del usuario elegido en el menú lateral
     * @param navigationView
     */
    private void asignaInfo(NavigationView navigationView) {


        View headView = navigationView.getHeaderView(0);

        ImageView perfil = (ImageView) headView.findViewById(R.id.iPerfil);
        TextView name = (TextView) headView.findViewById(R.id.textName);
        TextView description = (TextView) headView.findViewById(R.id.textDescript);

        int rawID = getResources().getIdentifier(person+"cara","drawable",getPackageName());
        perfil.setImageResource(rawID);

        if(person.equals("fer")) {
            name.setText("Fernando Amado");
            name.setTextColor(getResources().getColor(R.color.white));

            description.setText("Español ante todo. La patría siempre por delante");
            description.setTextColor(getResources().getColor(R.color.white));

        }else if(person.equals("berto")){
            name.setText("Alberto García");
            name.setTextColor(getResources().getColor(R.color.white));

            description.setText("Drogopropulsado. Subo videos de mi gato a insta");
            description.setTextColor(getResources().getColor(R.color.white));
        }


    }


}
