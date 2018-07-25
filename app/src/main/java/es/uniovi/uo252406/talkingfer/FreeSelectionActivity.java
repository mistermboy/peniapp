package es.uniovi.uo252406.talkingfer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.util.ArrayList;

import es.uniovi.uo252406.talkingfer.Entities.Player;

public class FreeSelectionActivity extends AppCompatActivity {

    static int numBotones = 20;

    ArrayList<String> audios;
    String person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selection);

        //Obtenemos los objetos enviados desde el MainActivity
        Bundle b = getIntent().getExtras();
        audios = b.getStringArrayList("audios");
        person = b.getString("person");

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
            //Nos quedamos solo con el texto que nos interesa
            String buttonText = String.valueOf(audios.get(i)).replace("_"," ").replace(person,"").replace("0","ñ"); //Los audios no pueden llevar "ñ" así que ponemos un 0 y luego sustituimos
            //Asignamos Texto al botón
            button.setText(buttonText);
            //Asignamos el listener
            button.setOnClickListener(new ButtonsOnClickListener(audios.get(i)));


            //Creamos el botón de fav
            Button star = new Button(this);

            int drawID = getResources().getIdentifier("btn_star_big_on","drawable","android");
            star.setBackground(getResources().getDrawable(drawID));

            //Añadimos los botones a la botonera
            lScroll.addView(button);

            /*
            if(lScroll.getParent()!=null)
                ((ScrollView)lScroll.getParent()).removeView(lScroll); // <- fix

            lScroll.addView(star);

            */


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
