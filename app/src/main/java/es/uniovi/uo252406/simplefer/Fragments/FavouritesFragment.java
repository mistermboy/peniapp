package es.uniovi.uo252406.simplefer.Fragments;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Persistence.FavouritesDataSource;
import es.uniovi.uo252406.simplefer.R;



public class FavouritesFragment extends Fragment {


    private View view;
    private String person;

    private ArrayList<String> audios;


    public FavouritesFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favourites, container, false);


        //Obtenemos los objetos enviados desde el MainActivity
        Bundle b = getActivity().getIntent().getExtras();
        person = (String) b.getString("person");


        selectAndDraw();

        return  view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void selectAndDraw() {


        FavouritesDataSource fD = new FavouritesDataSource(getContext());
        fD.open();
        audios = fD.getAllFavorites(person);
        fD.close();

        //Obtenemos el linear layout del scroll
        LinearLayout lScroll = (LinearLayout) view.findViewById(R.id.lScroll);


        //Propiedades para los botones
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        ArrayList<String> elementos = new ArrayList<>();

        elementos.add("Guadar como favorito");
        elementos.add("Compartir en whatsapp");
        elementos.add("Establecer como tono de llamada");


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);

        //Creaación de los botones

        for (int i = 0; i < audios.size(); i++) {


            Button button = new Button(view.getContext());
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Nos quedamos solo con el texto que nos interesa
            String buttonText = String.valueOf(audios.get(i)).replace("_", " ").replace(person, "").replace("0", "ñ"); //Los audios no pueden llevar "ñ" así que ponemos un 0 y luego sustituimos
            //Asignamos Texto al botón
            button.setText(buttonText);
            //Asignamos la fuente
            Typeface typeface = getResources().getFont(R.font.indieflower);
            button.setTypeface(typeface);
            //Aumentamos el tamaño de la letra
            button.setTextSize(16);
            //Asignamos los listener

          //  button.setOnClickListener(new AudiosFragment.ButtonsOnClickListener(audios.get(i)));
           // button.setOnLongClickListener(new AudiosFragment.ButtonsOnLongClickListener(audios.get(i)));


            //Añadimos los botones a la botonera
            lScroll.addView(button);

        }
    }



}
