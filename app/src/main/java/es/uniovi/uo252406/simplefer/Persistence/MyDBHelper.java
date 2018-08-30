package es.uniovi.uo252406.simplefer.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    /**
     * Nombre y version de la base de datos
     */
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * Nombre de la tabla favorites y sus columnas
     */
    public static final String TABLE_FAVORITES = "favorites";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AUDIO = "audio";
    public static final String COLUMN_PERSON = "person";
    public static final String COLUMN_POSITION = "position";



    /**
     * Script para crear la base datos
     */
    private static final String DATABASE_CREATE = "create table " + TABLE_FAVORITES
            + "( " + COLUMN_ID + " " +
            "integer primary key autoincrement, " + COLUMN_AUDIO
            + " text not null, "+ COLUMN_PERSON + " text not null, " + COLUMN_POSITION
            + " integer not null );";

    /**
     * Script para borrar la base de datos
     */
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_FAVORITES;

    public MyDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP);
        this.onCreate(db);

    }
}
