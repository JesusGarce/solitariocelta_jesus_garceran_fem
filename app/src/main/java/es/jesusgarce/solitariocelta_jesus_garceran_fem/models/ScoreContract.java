package es.jesusgarce.solitariocelta_jesus_garceran_fem.models;

import android.provider.BaseColumns;

public class ScoreContract {

    private ScoreContract() {
    }

    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";

        public static final String COL_NAME_ID = BaseColumns._ID;
        public static final String COL_NAME_NOMBRE_USUARIO = "nombre_usuario";
        public static final String COL_NAME_TIEMPO = "tiempo";
        public static final String COL_NAME_FECHA = "fecha";
        public static final String COL_NAME_FICHAS_RESTANTES = "fichas_restantes";
    }
}
