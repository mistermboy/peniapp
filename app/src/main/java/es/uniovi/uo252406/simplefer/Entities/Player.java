package es.uniovi.uo252406.simplefer.Entities;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Player {

    private static Player instance;

    private android.media.MediaPlayer mp;

    int valor = 0;
    int maxValor;

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
            mp.stop();
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
    public void start() {
        mp.start();
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
}
