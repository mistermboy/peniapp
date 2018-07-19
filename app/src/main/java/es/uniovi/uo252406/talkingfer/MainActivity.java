package es.uniovi.uo252406.talkingfer;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    ArrayList<String> audios;
    Button btnPrincipal;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPrincipal = findViewById(R.id.btnPrincipal);
        createAudios();

        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Player.getInstance() != null)
                    Player.getInstance().mpNull();
                Player.getInstance().changeAudio(getBaseContext(),audios);
                try{
                    Player.getInstance().start();
                }catch(IllegalStateException e){
                    Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
                }
            }
        });

    }

    /**
     *  Crea el ArrayList con el nombre de los audios.
     *  Se llama cuando se crea el activity.
     */
    private void createAudios() {
        audios = new ArrayList<>();
        for(Field f: R.raw.class.getFields()){
            audios.add(f.getName());
        }
    }


    /**
     * Crea el menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Detecta la opción del menu seleccionada
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_freeSelect) {
            startFreeSelectionActivity();
            return true;
        }
        if (id == R.id.action_fin) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     *  Pasa del MainActivity al FreeSelectionActivity y le pasa como parámetro la lista de audios
     */
    private void startFreeSelectionActivity(){
        intent = new Intent(MainActivity.this, FreeSelectionActivity.class);
        intent.putExtra("audios", audios);
        startActivity(intent);
    }


}
