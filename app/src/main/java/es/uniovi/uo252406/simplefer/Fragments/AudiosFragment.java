package es.uniovi.uo252406.simplefer.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Logical.Player;
import es.uniovi.uo252406.simplefer.R;


public class AudiosFragment extends android.support.v4.app.Fragment {

    View view;

    ArrayList<String> audios;
    String person;


    public AudiosFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_audios, container, false);


        //Obtenemos los objetos enviados desde el MainActivity
        Bundle b = getActivity().getIntent().getExtras();
        person = (String) b.getString("person");


        selectAndDraw();

        return view;
    }


    /**
     * Selecciona los audios y crea todos los botones
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void selectAndDraw() {

        //audios = createAudios(person);
        audios = Player.getInstance().getAudios(person);

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
            button.setOnClickListener(new AudiosFragment.ButtonsOnClickListener(audios.get(i)));
             button.setOnLongClickListener(new AudiosFragment.ButtonsOnLongClickListener(audios.get(i)));


            //Añadimos los botones a la botonera
            lScroll.addView(button);

        }
    }



        class ButtonsOnClickListener implements View.OnClickListener {

            private String name;

            public ButtonsOnClickListener(String name) {
                this.name = name;
            }

            @Override
            public void onClick(View v) {
                if (Player.getInstance() != null)
                    Player.getInstance().mpNull();
                Player.getInstance().selectAudio(getActivity().getBaseContext(), name);
                try {
                    Player.getInstance().start();
                } catch (IllegalStateException e) {
                    Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
                }
            }


        }



    class ButtonsOnLongClickListener implements View.OnLongClickListener {

        private String name;

        public ButtonsOnLongClickListener(String name) {
            this.name = name;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onLongClick(View v) {

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.custom_dialog);
            dialog.show();


            Button b1 = dialog.findViewById(R.id.btnFav);
            Button b2 = dialog.findViewById(R.id.btnLlamada);
            Button b3 = dialog.findViewById(R.id.btnCompartir);
            Button b4 = dialog.findViewById(R.id.btnCancelar);

            b1.setTextSize(14);
            b2.setTextSize(14);
            b3.setTextSize(14);
            b4.setTextSize(14);


            b1.setTextColor(getResources().getColor(R.color.black));
            b2.setTextColor(getResources().getColor(R.color.black));
            b3.setTextColor(getResources().getColor(R.color.black));
            b4.setTextColor(getResources().getColor(R.color.black));



            Typeface typeface = getResources().getFont(R.font.indieflower);

            b1.setTypeface(typeface);
            b2.setTypeface(typeface);
            b3.setTypeface(typeface);
            b4.setTypeface(typeface);


            return true;
        }

    }






}
