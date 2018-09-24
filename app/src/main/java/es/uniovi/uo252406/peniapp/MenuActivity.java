package es.uniovi.uo252406.peniapp;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import es.uniovi.uo252406.peniapp.Fragments.AudiosFragment;
import es.uniovi.uo252406.peniapp.Logical.Entities.PersonalData;
import es.uniovi.uo252406.peniapp.Logical.Parser;
import es.uniovi.uo252406.peniapp.Logical.Player;
import es.uniovi.uo252406.peniapp.Fragments.FaceFragment;
import es.uniovi.uo252406.peniapp.Fragments.QuizFragment;
import es.uniovi.uo252406.peniapp.Logical.Persistence.FavouritesDataSource;
import es.uniovi.uo252406.simplefer.R;

public class MenuActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener {


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

        fm.beginTransaction().replace(R.id.escenario, new FaceFragment()).commit();



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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.perfil) {
            fm.beginTransaction().replace(R.id.escenario,new FaceFragment()).commit();
        } else if (id == R.id.audios) {

            AudiosFragment af = new AudiosFragment();
            Bundle args = new Bundle();
            args.putBoolean("favourite",false);
            af.setArguments(args);

            fm.beginTransaction().replace(R.id.escenario,af).commit();


        } else if (id == R.id.fav) {

            FavouritesDataSource bd = new FavouritesDataSource(getApplicationContext());
            bd.open();

            if(bd.getAllFavorites(person).size()>0){

                AudiosFragment af = new AudiosFragment();
                Bundle args = new Bundle();
                args.putBoolean("favourite",true);
                af.setArguments(args);

                fm.beginTransaction().replace(R.id.escenario,af).commit();


            }else{

                Toast toast = Toast.makeText(getApplicationContext(), "Aún no hay favoritos", Toast.LENGTH_SHORT);
                toast.show();

            }



        }
        else if (id == R.id.quiz) {
            fm.beginTransaction().replace(R.id.escenario,new QuizFragment()).commit();
        }
        else if (id == R.id.inicio) {
            finish();
        } else if (id == R.id.aboutUs) {
            final Dialog info = new Dialog(this);
            info.setContentView(R.layout.aboutus_dialog);
            info.show();

            Button ok = info.findViewById(R.id.btnOkAboutUs);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    info.dismiss();
                }
            });

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


        try {

            PersonalData personalData = Parser.getInstance().getData(headView,person);

            name.setText(personalData.getName());
            name.setTextColor(getResources().getColor(R.color.white));

            description.setText(personalData.getDescription());
            description.setTextColor(getResources().getColor(R.color.white));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

}
