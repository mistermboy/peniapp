package es.uniovi.uo252406.simplefer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import es.uniovi.uo252406.simplefer.Logical.Player;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (position % 2 != 0 && position!=0) {

                    startMenu("fer");

                } else if(position % 2 == 0 && position!=0){

                    startMenu("berto");

                }
            }
        });


        //Crea todos los audios de todos los personajes
        Player.getInstance().createAllAudios();


    }

    /**
     * Pasa al Menu
     */
    private void startMenu(String person) {

        intent = new Intent(getApplicationContext(), MenuActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("person", person);

        intent.putExtras(bundle);

        startActivity(intent);

    }


}
