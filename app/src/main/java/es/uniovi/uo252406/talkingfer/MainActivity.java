package es.uniovi.uo252406.talkingfer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    Button btnPrincipal;
    int valor = 1;
    final int maxValor = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPrincipal = findViewById(R.id.btnPrincipal);

        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mp != null)
                    mpNull();
                changeAudio();
                try{
                    mp.start();
                }catch(IllegalStateException e){
                    Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
                }
            }
        });


    }

    private void changeAudio(){
        if(valor > maxValor){
            valor = 1;
            mpNull();
        }

        if(valor == 1)
            mp = MediaPlayer.create(this,R.raw.fer1);
        if(valor == 2)
            mp = MediaPlayer.create(this,R.raw.fer2);
        if(valor == 3)
            mp = MediaPlayer.create(this,R.raw.fer3);
        if(valor == 4)
            mp = MediaPlayer.create(this,R.raw.fer4);
        if(valor == 5)
            mp = MediaPlayer.create(this,R.raw.fer5);
        if(valor == 6)
            mp = MediaPlayer.create(this,R.raw.fer6);
        if(valor == 7)
            mp = MediaPlayer.create(this,R.raw.fer7);
        if(valor == 8)
            mp = MediaPlayer.create(this,R.raw.fer8);
        if(valor == 9)
            mp = MediaPlayer.create(this,R.raw.fer9);
        if(valor == 10)
            mp = MediaPlayer.create(this,R.raw.fer10);
        if(valor == 11)
            mp = MediaPlayer.create(this,R.raw.fer11);
        if(valor == 12)
            mp = MediaPlayer.create(this,R.raw.fer12);
        if(valor == 13)
            mp = MediaPlayer.create(this,R.raw.fer13);
        if(valor == 14)
            mp = MediaPlayer.create(this,R.raw.fer14);
        if(valor == 15)
            mp = MediaPlayer.create(this,R.raw.fer15);
        if(valor == 16)
            mp = MediaPlayer.create(this,R.raw.fer16);
        if(valor == 17)
            mp = MediaPlayer.create(this,R.raw.fer17);
        valor++;

    }

    private void mpNull(){
        try{
            mp.stop();
            mp.release();
        }catch(IllegalStateException e){
            Log.e("IllegalStateException", "Illegal State Exception: " + e.getMessage());
        }catch(Exception e){
            Log.e("Exception", "General Exception: " + e.getMessage());
        }

    }

}
