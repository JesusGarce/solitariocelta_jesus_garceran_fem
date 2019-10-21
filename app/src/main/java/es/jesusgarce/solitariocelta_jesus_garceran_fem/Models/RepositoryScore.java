package es.jesusgarce.solitariocelta_jesus_garceran_fem.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;
import java.util.LinkedList;

public class RepositoryScore extends SQLiteOpenHelper {
    private static final String DB_NAME = ScoreContract.ScoreEntry.TABLE_NAME + ".db";
    private static final int DB_VERSION = 1;

    public RepositoryScore(@Nullable Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String consultaSQL = "CREATE TABLE " + ScoreContract.ScoreEntry.TABLE_NAME + " ("
                + ScoreContract.ScoreEntry.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ScoreContract.ScoreEntry.COL_NAME_NOMBRE_USUARIO      + " TEXT, "
                + ScoreContract.ScoreEntry.COL_NAME_TIEMPO + " TEXT,"
                + ScoreContract.ScoreEntry.COL_NAME_FECHA + " DATE,"
                + ScoreContract.ScoreEntry.COL_NAME_FICHAS_RESTANTES + " INTEGER) ";

        db.execSQL(consultaSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String consultaSQL = "DROP TABLE IF EXISTS " + ScoreContract.ScoreEntry.TABLE_NAME;
        db.execSQL(consultaSQL);
        onCreate(db);
    }

    public long count() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, ScoreContract.ScoreEntry.TABLE_NAME);
    }

    public long add(String userName, String tiempo, String fecha, int fichasRestantes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoreContract.ScoreEntry.COL_NAME_NOMBRE_USUARIO, userName);
        values.put(ScoreContract.ScoreEntry.COL_NAME_TIEMPO, tiempo);
        values.put(ScoreContract.ScoreEntry.COL_NAME_FECHA, fecha);
        values.put(ScoreContract.ScoreEntry.COL_NAME_FICHAS_RESTANTES, fichasRestantes);

        return db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
    }

    public LinkedList<Score> getAll() {
        String consultaSQL = "SELECT * FROM " + ScoreContract.ScoreEntry.TABLE_NAME;
        LinkedList<Score> scoreList = new LinkedList<>();

        // Accedo a la DB en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Score score = new Score(
                        cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_NOMBRE_USUARIO)),
                        cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_TIEMPO)),
                        cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_FECHA)),
                        cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_FICHAS_RESTANTES))
                );

                scoreList.add(score);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return scoreList;
    }

    public LinkedList<Score> getAllByFichasRestantes(){
        String consultaSQL =
                "SELECT * FROM " + ScoreContract.ScoreEntry.TABLE_NAME +
                " ORDER BY "+ ScoreContract.ScoreEntry.COL_NAME_FICHAS_RESTANTES + " ASC, "+ ScoreContract.ScoreEntry.COL_NAME_TIEMPO+ " ASC";
        LinkedList<Score> scoreList = new LinkedList<>();

        // Accedo a la DB en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Score score = new Score(
                        cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_NOMBRE_USUARIO)),
                        cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_TIEMPO)),
                        cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_FECHA)),
                        cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COL_NAME_FICHAS_RESTANTES))
                );

                scoreList.add(score);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return scoreList;
    }

    public void deleteAll(){
        String consultaSQL = "DELETE FROM " + ScoreContract.ScoreEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(consultaSQL);
    }
}
