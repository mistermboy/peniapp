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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPrincipal = (Button) findViewById(R.id.btnPrincipal);

        btnPrincipal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeAudio(); mp.start();
            }
        });


    }

    private void changeAudio(){


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

        valor++;
    }

}
