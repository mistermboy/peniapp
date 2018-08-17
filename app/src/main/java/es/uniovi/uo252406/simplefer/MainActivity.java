package es.uniovi.uo252406.simplefer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private static int aux = 0;

    private static void increment(){
        aux++;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(position %2==0)
                    startMain("fer");
                else
                    startMain("berto");
            }
        });
    }


    /**
     * Pasa al Main
     */
    private void startMain(String person) {
        intent = new Intent(getApplicationContext(), MenuActivity.class);

      /*  Bundle bundle = new Bundle();
        bundle.putString("person", person);

        intent.putExtras(bundle);

        */
        startActivity(intent);

    }

}
