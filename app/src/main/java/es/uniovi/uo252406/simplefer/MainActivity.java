package es.uniovi.uo252406.simplefer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import es.uniovi.uo252406.simplefer.Logical.Player;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private GridView gridview;
    private ProgressBar progressMenu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressMenu = findViewById(R.id.progressMenu);
        progressMenu.setVisibility(View.INVISIBLE);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (position % 2 != 0 && position!=0) {

                    new Charger().execute("fer");

                } else if(position % 2 == 0 && position!=0){

                    new Charger().execute("berto");

                }
            }
        });

        //Crea todos los audios de todos los personajes
        Player.getInstance().createAllAudios();

    }


    @Override
    public void onResume(){
        super.onResume();

        progressMenu.setVisibility(View.INVISIBLE);
        gridview.setVisibility(View.VISIBLE);
    }



    private class Charger extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {

            ProgressBar progressMenu = findViewById(R.id.progressMenu);
            progressMenu.setVisibility(View.VISIBLE);

            gridview.setVisibility(View.INVISIBLE);

        }


        @Override
        protected String doInBackground(String... strings) {


            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return strings[0];
        }

        @Override
        protected void onPostExecute(String result) {

            startMenu(result);

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



}
