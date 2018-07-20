package es.uniovi.uo252406.talkingfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class GridTest extends AppCompatActivity {

    private Intent intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_grid);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
               startMain();
            }
        });
    }


    /**
     * Pasa del MainActivity al FreeSelectionActivity y le pasa como parámetro la lista de audios
     */
    private void startMain() {
        intent = new Intent(GridTest.this, MainActivity.class);
        //Aquí hay que reeemplazar fer por la persona que se selecciona
        intent.putExtra("person", "fer");
        startActivity(intent);
    }

}
