package es.uniovi.uo252406.talkingfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FreeSelectionActivity extends AppCompatActivity {

    static int numBotones = 20;

    ArrayList<Integer> audios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selection);

        //Obtenemos los audios enviados desde el MainActivity
        audios = (ArrayList<Integer>)getIntent().getExtras().getSerializable("audios");

        //Obtenemos el linear layout del scroll
        LinearLayout lScroll = (LinearLayout) findViewById(R.id.lScroll);

        //Propiedades para los botones
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creaación de los botones
        for (int i=0; i<audios.size(); i++){

            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(audios.get(i).toString());
            //Añadimos el botón a la botonera
            lScroll.addView(button);
        }
    }

}
