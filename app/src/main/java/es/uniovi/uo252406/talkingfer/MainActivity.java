package es.uniovi.uo252406.talkingfer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.util.ArrayList;

import es.uniovi.uo252406.talkingfer.Entities.Player;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> audios;
    Button btnPrincipal;
    Button btnShowAll;
    Intent intent;

    String person;

    public final static int FREE_ACTIVITY=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recogemos los datos
        Bundle b = getIntent().getExtras();
        person = (String) b.getString("person");


        createButton(person);
        createAudios(person);
        asignaFuncionalidades();


    }

    /**
     * Asigna funcionalidad a los botones
     */
    private void asignaFuncionalidades() {
        btnPrincipal = findViewById(R.id.btnPrincipal);

        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

        btnShowAll = findViewById(R.id.btnShowAll);

        btnShowAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               startFreeSelectionActivity();
            }
        });


    }

    /**
     * Asigna la imagen correcta al botón
     */
    private void createButton(String img) {

        Button btn = (Button) findViewById(R.id.btnPrincipal);
        int drawID = getResources().getIdentifier(img,"drawable",getPackageName());
        btn.setBackground(getResources().getDrawable(drawID));

    }

    /**
     * Crea el ArrayList con el nombre de los audios.
     * Escoge solo los audios que pertenezcan a la persona que se le pasa.
     * Establece un límite en el reproductor
     * Se llama cuando se crea el activity.
     */
    private void createAudios(String person) {
        audios = new ArrayList<>();
        int numAudios=0;
        for (Field f : R.raw.class.getFields()) {
            //Es  un audio de la persona que estamos buscando?
           if(f.getName().split("_")[0].equals(person)){
               audios.add(f.getName());
               numAudios++;
           }

        }
        Player.getInstance().setMaxValor(numAudios);
    }


    
    /**
     * Pasa del MainActivity al FreeSelectionActivity y le pasa como parámetro la lista de audios
     */
    private void startFreeSelectionActivity() {
        intent = new Intent(MainActivity.this, FreeSelectionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("audios", audios);
        bundle.putString("person", person);

        intent.putExtras(bundle);
        startActivityForResult(intent,FREE_ACTIVITY);

    }

}
