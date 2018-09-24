package es.uniovi.uo252406.peniapp.Logical.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


/**
     * Ejemplo <b>SQLite</b>. Ejemplo de uso de SQLite.
     *
     * DAO para la tabla de valoracion.
     * Se encarga de abrir y cerrar la conexion, asi como hacer las consultas relacionadas con la tabla valoracion
     *

     */
    public class FavouritesDataSource {
        /**
         * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
         * y nos proporciona metodos para hacer operaciones
         * CRUD (create, read, update and delete)
         */
        private SQLiteDatabase database;
        /**
         * Referencia al helper que se encarga de crear y actualizar la base de datos.
         */
        private MyDBHelper dbHelper;
        /**
         * Columnas de la tabla
         */
        private final String[] allColumns = { MyDBHelper.COLUMN_ID, MyDBHelper.COLUMN_AUDIO};
        /**
         * Constructor.
         *
         * @param context
         */
        public FavouritesDataSource(Context context) {
            dbHelper = new MyDBHelper(context);
        }

        /**
         * Abre una conexion para escritura con la base de datos.
         * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
         * datos no esta creada, el helper se encargara de llamar a onCreate
         *
         * @throws SQLException
         */
        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        /**
         * Cierra la conexion con la base de datos
         */
        public void close() {
            dbHelper.close();
        }

    /**
     * Método que añade el audio a favoritos
     * @param audio
     * @param person
     * @return
     */
        public long addFavorite(String audio,String person) {
            ContentValues values = new ContentValues();
            values.put(MyDBHelper.COLUMN_AUDIO,audio);
            values.put(MyDBHelper.COLUMN_PERSON,person);

            // Insertamos el audio
            long insertId = database.insert(MyDBHelper.TABLE_FAVORITES, null, values);

            return insertId;
        }

        /**
         *Método que elimina la audio de favoritos
         * @return
         */
        public long removeFavorite(String pressed, String person){

            // Borramos la audio
            long removeId = database.delete(MyDBHelper.TABLE_FAVORITES, MyDBHelper.COLUMN_AUDIO+"= '"+pressed+"'",null);

            return removeId;

        }


        /**
         * Obtiene todas las audios marcados como favoritas
         *
         * @return Lista de objetos de tipo Valoration
         * @param person
         */
        public ArrayList<String> getAllFavorites(String person) {
            // Lista que almacenara el resultado
            ArrayList<String> favoritesList = new ArrayList<String>();
            //hacemos una query porque queremos devolver un cursor
            String[] args = new String[] {person};

            Cursor cursor = database.query(MyDBHelper.TABLE_FAVORITES, allColumns,
                    "person=?", args, null, null,null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                final String favorite = cursor.getString(1);

                favoritesList.add(favorite);
                cursor.moveToNext();
            }

            cursor.close();

            return favoritesList;
        }


    /**
     * Devuelve si el audio es favorito o no
     * @param audio
     * @param person
     * @return
     */
    public boolean isFavourite(String audio,String person){
            ArrayList<String> favs = getAllFavorites(person);
            for(String s:favs){
                if(s.equals(audio))
                    return true;
            }
            return false;
        }

    }

