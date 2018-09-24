package es.uniovi.uo252406.peniapp.Fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Collections;

import es.uniovi.uo252406.peniapp.Logical.Player;
import es.uniovi.uo252406.simplefer.R;


public class FaceFragment extends Fragment{


    static ArrayList<String> audios;
    Button btnPrincipal;
    VideoView vView;
    Uri uri;

    String person;

    View view;

    public FaceFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_face, container, false);

        //Recogemos los datos
        Bundle b = getActivity().getIntent().getExtras();
        person = (String) b.getString("person");

        new FaceCharger().execute();

        // Inflate the layout for this fragment
        return view;
    }



    private class FaceCharger extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            vView = (VideoView)  view.findViewById(R.id.videoView);

            int rawID = getContext().getResources().getIdentifier(person+"video","raw",getContext().getPackageName());

            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + rawID);

            vView.setVideoURI(uri);
            vView.seekTo(vView.getDuration());



        }

        @Override
        protected Void doInBackground(Void... voids) {
            audios = Player.getInstance().getAudios(person);
            Collections.shuffle(audios);
            asignaFuncionalidades();
            return null;
        }
    }

    /**
     * Asigna funcionalidad a los botones
     */
    private void asignaFuncionalidades() {

        btnPrincipal = view.findViewById(R.id.btnPrincipal);


        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if (Player.getInstance() != null)
                    Player.getInstance().mpNull();
                Player.getInstance().changeAudio(getActivity().getBaseContext(), audios);
                try {

                    vView.setVideoURI(uri);
                    vView.start();

                    Player.getInstance().start();

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
