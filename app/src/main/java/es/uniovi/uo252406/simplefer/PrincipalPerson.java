package es.uniovi.uo252406.simplefer;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Entities.Player;


public class PrincipalPerson extends Fragment{


    static ArrayList<String> audios;
    Button btnPrincipal;
    VideoView vView;
    Uri uri;

    String person;

    View view;

    public PrincipalPerson(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_principal_person, container, false);

        //Recogemos los datos
        Bundle b = getActivity().getIntent().getExtras();
        person = (String) b.getString("person");


        createAudios(person);
        asignaFuncionalidades();


        // Inflate the layout for this fragment
        return view;
    }


    /**
     * Crea el ArrayList con el nombre de los audios.
     * Escoge solo los audios que pertenezcan a la persona que se le pasa.
     * Establece un límite en el reproductor
     * Se llama cuando se crea el activity.
     */
    public static ArrayList<String> createAudios(String person) {
        audios = new ArrayList<>();
        int numAudios = 0;
        for (Field f : R.raw.class.getFields()) {
            //Es  un audio de la persona que estamos buscando?
            if (f.getName().split("_")[0].equals(person)) {
                audios.add(f.getName());
                numAudios++;
            }

        }
        Player.getInstance().setMaxValor(numAudios);
        return audios;
    }


    /**
     * Asigna funcionalidad a los botones
     */
    private void asignaFuncionalidades() {

        btnPrincipal = view.findViewById(R.id.btnPrincipal);

        vView = (VideoView)  view.findViewById(R.id.videoView);
        uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.fervideo);

        vView.setVideoURI(uri);
        vView.seekTo(vView.getDuration());

        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if (Player.getInstance() != null)
                    Player.getInstance().mpNull();
                Player.getInstance().changeAudio(getActivity().getBaseContext(), audios);
                try {
                    Player.getInstance().start();

                    vView.setVideoURI(uri);
                    vView.start();

                    new Thread(new Runnable() {
                        public void run() {
                            while (Player.getInstance().isPlaying()) {

                                if (!vView.isPlaying()) {
                                    vView.start();
                                }
                            }
                            vView.pause();
                            vView.seekTo(vView.getDuration());
                        }
                    }).start();




                } catch (IllegalStateException e) {
                    Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
                }
            }
        });


    }



}
