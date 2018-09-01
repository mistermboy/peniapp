package es.uniovi.uo252406.simplefer.Logical;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.uniovi.uo252406.simplefer.R;

public class Player {

    private static Player instance;

    private android.media.MediaPlayer mp;

    int valor = 0;
    int maxValor;


    private Map<String,ArrayList<String>> allAudios;

    private Player(){

    }

    public static Player getInstance(){
        if(instance == null)
            instance = new Player();
        return instance;
    }


    /**
     *   Cambia el audio respecto al anteriormente reproducido.
     *   Este método es llamado cada vez que el usuario presiona el botón principal de reproducción.
     */
    public void changeAudio(Context context,ArrayList<String> audios){
        setMaxValor(audios.size());
        if(valor >= maxValor){
            valor = 0;
            mpNull();
        }
        selectAudio(context,audios.get(valor++));
    }

    /**
     * Selecciona un audio concreto
     * @param context
     * @param name
     */
    public void selectAudio(Context context,String name){

        //  Le pasamos el nombre del audio, de la carpeta y el paquete y nos devuelve un índice para acceder al recurso que deseamos.
        int rawID = context.getResources().getIdentifier(name,"raw",context.getPackageName());
        mp = android.media.MediaPlayer.create(context,rawID);

    }

    /**
     *  Se encarga de parar el reproductor por si algún audio está sonando.
     *  Este método es llamado cada vez que el usuario presiona el botón principal  de reproducción para evitar que se solapen los audios.
     */
    public void mpNull(){
        try{
            mp.reset();
            mp.release();
            mp = null;
        }catch(IllegalStateException e){
            Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
        }catch(Exception e){
            Log.e("Exception", "General Exception: " + e.getMessage());
        }

    }

    /**
     *  Reproduce el audio
     */
    public void start(){
        mp.start();
    }



    public void createAllAudios(){
        allAudios = new HashMap<>();

        allAudios.put("fer",getAudiosFromRaw("fer"));
        allAudios.put("berto",getAudiosFromRaw("berto"));

    }



    private ArrayList<String> getAudiosFromRaw(String person) {
        ArrayList<String> audios = new ArrayList<>();
        int numAudios = 0;
        for (Field f : R.raw.class.getFields()) {
            //Es  un audio de la persona que estamos buscando?
            if (f.getName().split("_")[0].equals(person)) {
                audios.add(f.getName());
                numAudios++;
            }

        }
        return audios;
    }



    /**
     * Devuelve los audios de la persona que se le pasa por parámetro
     * @param person
     * @return
     */
    public ArrayList<String> getAudios(String person){
        return allAudios.get(person);
    }



    //Getters y Setters

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getMaxValor() {
        return maxValor;
    }

    public void setMaxValor(int maxValor) {
        this.maxValor = maxValor;
    }

    public boolean isPlaying() {
        return  mp.isPlaying();
    }

    public void pause() {

        if(mp!=null)
            mp.pause();
        mpNull();
    }
}
