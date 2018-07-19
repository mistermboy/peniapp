package es.uniovi.uo252406.talkingfer;

import android.content.Intent;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp = new MediaPlayer();
    ArrayList<String> audios;

    Button btnPrincipal;
    int valor = 0;
    final int maxValor = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPrincipal = findViewById(R.id.btnPrincipal);
        createAudios();


        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mp != null)
                    mpNull();
                changeAudio();
                try{
                    mp.start();
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
     *   Cambia el audio respecto al anteriormente reproducido.
     *   Este método es llamado cada vez que el usuario presiona el botón principal.
     */
    private void changeAudio(){
        if(valor > maxValor){
            valor = 1;
            mpNull();
        }

        //  Le pasamos el nombre del audio, de la carpeta y el paquete y nos devuelve un índice para acceder al recurso que deseamos.
        int rawID = getResources().getIdentifier(audios.get(valor++),"raw",getPackageName());
        mp = MediaPlayer.create(this,rawID);

    }

    /**
     *  Se encarga de parar el reproductor por si algún audio está sonando.
     *  Este método es llamado cada vez que el usuario presiona el botón principal para evitar que se solapen los audios.
     */
    private void mpNull(){
        try{
            mp.stop();
            mp.release();
        }catch(IllegalStateException e){
            Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
        }catch(Exception e){
            Log.e("Exception", "General Exception: " + e.getMessage());
        }

    }

    /**
     *  Pasa del MainActivity al FreeSelectionActivity y le pasa como parámetro la lista de audios
     */
    private void startFreeSelectionActivity(){
        Intent intent = new Intent(MainActivity.this, FreeSelectionActivity.class);
        intent.putExtra("audios", audios);
        startActivity(intent);
    }


}
