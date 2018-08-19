package es.uniovi.uo252406.simplefer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Entities.Player;

import static es.uniovi.uo252406.simplefer.PrincipalPerson.createAudios;

public class FreeSelectionFragment extends android.support.v4.app.Fragment {

    View view;

    ArrayList<String> audios;
    String person;


    public FreeSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_free_selection, container, false);


        //Obtenemos los objetos enviados desde el MainActivity
        Bundle b = getActivity().getIntent().getExtras();
       // audios = b.getStringArrayList("audios");
     //   person = b.getString("person");
        person = (String) b.getString("person");
        audios = createAudios(person);

        //Obtenemos el linear layout del scroll
        LinearLayout lScroll = (LinearLayout) view.findViewById(R.id.lScroll);



        //Propiedades para los botones
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );


        //Creaación de los botones

        for (int i=0; i<audios.size(); i++){

            Button button = new Button(view.getContext());
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Nos quedamos solo con el texto que nos interesa
            String buttonText = String.valueOf(audios.get(i)).replace("_"," ").replace(person,"").replace("0","ñ"); //Los audios no pueden llevar "ñ" así que ponemos un 0 y luego sustituimos
            //Asignamos Texto al botón
            button.setText(buttonText);
            //Asignamos el listener
            button.setOnClickListener(new FreeSelectionFragment.ButtonsOnClickListener(audios.get(i)));


            //Creamos el botón de fav
            Button star = new Button(view.getContext());

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


        return view;
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
            Player.getInstance().selectAudio(getActivity().getBaseContext(), name);
            try{
                Player.getInstance().start();
            }catch(IllegalStateException e){
                Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
            }
        }

    };

}
