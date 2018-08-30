package es.uniovi.uo252406.simplefer.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Logical.Player;
import es.uniovi.uo252406.simplefer.R;


public class AudiosFragment extends android.support.v4.app.Fragment {

    View view;

    ArrayList<String> audios;
    String person;

    String exStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    String path=(exStoragePath +"/media/ringtones/");

    String filename;
    String pressed = "";

    private final int REQUEST_ACCESS_FINE =0;


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

            Player.getInstance().pause();
            pressed = name;

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.custom_dialog);
            dialog.show();


            Button bntFav = dialog.findViewById(R.id.btnFav);
            Button btnRingtone = dialog.findViewById(R.id.btnLlamada);
            Button btnShare = dialog.findViewById(R.id.btnCompartir);
            Button btnCancel = dialog.findViewById(R.id.btnCancelar);
            Button btnAlarm = dialog.findViewById(R.id.btnAlarm);
            Button btnNotification = dialog.findViewById(R.id.btnNotification);

            bntFav.setTextSize(14);
            btnRingtone.setTextSize(14);
            btnShare.setTextSize(14);
            btnCancel.setTextSize(14);
            btnAlarm.setTextSize(14);
            btnNotification.setTextSize(14);


            bntFav.setTextColor(getResources().getColor(R.color.black));
            btnRingtone.setTextColor(getResources().getColor(R.color.black));
            btnShare.setTextColor(getResources().getColor(R.color.black));
            btnCancel.setTextColor(getResources().getColor(R.color.black));
            btnAlarm.setTextColor(getResources().getColor(R.color.black));
            btnNotification.setTextColor(getResources().getColor(R.color.black));



            Typeface typeface = getResources().getFont(R.font.indieflower);

            bntFav.setTypeface(typeface);
            btnRingtone.setTypeface(typeface);
            btnShare.setTypeface(typeface);
            btnCancel.setTypeface(typeface);
            btnAlarm.setTypeface(typeface);
            btnNotification.setTypeface(typeface);


            bntFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            btnRingtone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(!havePermissons()) {

                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ACCESS_FINE);

                    }else{
                        copyFile();


                        RingtoneManager.setActualDefaultRingtoneUri(
                                getActivity(),
                                RingtoneManager.TYPE_RINGTONE,
                                writeDB()
                        );

                        Toast toast = Toast.makeText(getContext(),"Se ha establecido un nuevo tono de llamada",Toast.LENGTH_SHORT);
                        toast.show();

                        dialog.dismiss();
                    }
                }
            });


            btnAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!havePermissons()) {

                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ACCESS_FINE);

                    }else{
                        copyFile();


                        RingtoneManager.setActualDefaultRingtoneUri(
                                getActivity(),
                                RingtoneManager.TYPE_ALARM,
                                writeDB()
                        );

                        Toast toast = Toast.makeText(getContext(),"Se ha establecido un nuevo tono de alarma",Toast.LENGTH_SHORT);
                        toast.show();

                        dialog.dismiss();
                    }

                }
            });

            btnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!havePermissons()) {

                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ACCESS_FINE);

                    }else{
                        copyFile();


                        RingtoneManager.setActualDefaultRingtoneUri(
                                getActivity(),
                                RingtoneManager.TYPE_NOTIFICATION,
                                writeDB()
                        );

                        Toast toast = Toast.makeText(getContext(),"Se ha establecido un nuevo tono de notificación",Toast.LENGTH_SHORT);
                        toast.show();

                        dialog.dismiss();
                    }

                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    copyFile();
                    writeDB();

                    String sharePath = path+filename;
                    Uri uri = Uri.parse(sharePath);
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("audio/mp3");
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(share, "Share Sound File"));

                    dialog.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });




            return true;
        }

    }

    private void copyFile(){


        byte[] buffer = null;
         int rawID = getContext().getResources().getIdentifier(pressed, "raw", getContext().getPackageName());

        InputStream fIn = getActivity().getBaseContext().getResources().openRawResource(
                rawID);
        int size = 0;

        try {
            size = fIn.available();
            buffer = new byte[size];
            fIn.read(buffer);
            fIn.close();
        } catch (IOException e) {
            return ;
        }

        filename = pressed+Math.random()+".mp3";

        boolean exists = (new File(path)).exists();
        if (!exists) {
            new File(path).mkdirs();
        }

        FileOutputStream save;
        try {
            save = new FileOutputStream(path + filename);
            save.write(buffer);
            save.flush();
            save.close();
        } catch (FileNotFoundException e) {
            return ;
        } catch (IOException e) {
            return ;
        }
    }


    private Uri writeDB(){

        File k = new File(path,filename);


        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.SIZE, 215454);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.DURATION, 230);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        //Insert it into the database
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
        return getActivity().getContentResolver().insert(uri, values);

    }

    private boolean havePermissons(){
        if(!Settings.System.canWrite(getContext())){
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(intent);

            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ) {
                return false;
            }

        }
        return true;
    }



}