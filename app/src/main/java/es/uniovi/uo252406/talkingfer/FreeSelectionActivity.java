package es.uniovi.uo252406.talkingfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FreeSelectionActivity extends AppCompatActivity {

    static int numBotones = 20;

    ArrayList<String> audios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selection);

        //Obtenemos los audios enviados desde el MainActivity
        audios = (ArrayList<String>) getIntent().getExtras().getSerializable("audios");

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

            String buttonText = String.valueOf(audios.get(i)).replace("_"," ");

            //Asignamos Texto al botón
            button.setText(buttonText);
            //Asignamos el listener
            button.setOnClickListener(new ButtonsOnClickListener(audios.get(i)));
            //Añadimos el botón a la botonera
            lScroll.addView(button);


        }

    }


    class ButtonsOnClickListener implements View.OnClickListener
    {

        private String name;

        public ButtonsOnClickListener(String name){
            this.name = name;
        }

        @Override
        public void onClick(View v)
        {
            if(Player.getInstance() != null)
                Player.getInstance().mpNull();
            Player.getInstance().selectAudio(getBaseContext(), name);
            try{
                Player.getInstance().start();
            }catch(IllegalStateException e){
                Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
            }
        }

    };

}
