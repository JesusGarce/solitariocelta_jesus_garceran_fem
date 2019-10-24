package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.viewmodel.SCeltaViewModel;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.viewmodel.TimeViewModel;

import static android.content.Context.MODE_PRIVATE;

public class SavedGamesManager {
    private static String NAME_FILE_SAVED_GAME_LIST = "listGamesSaved.txt";
    public final String LOG_KEY = "JGS";
    FileOutputStream fileOutputStream;
    Context context;
    SCeltaViewModel juegoCelta;
    TimeViewModel timeViewModel;

    public SavedGamesManager(Context context, SCeltaViewModel juegoCelta, TimeViewModel timeViewModel) {
        this.context = context;
        this.juegoCelta = juegoCelta;
        this.timeViewModel = timeViewModel;
    }

    public List<String> getGamesSaved() {
        List<String> gameSaveds = new LinkedList<>();

        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(context.openFileInput(NAME_FILE_SAVED_GAME_LIST)));
            String linea = fin.readLine();
            while (linea != null) {
                gameSaveds.add(linea);
                linea = fin.readLine();
            }
            fin.close();
            return gameSaveds;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Guardar la partida
     */
    public boolean saveGame(String nameSavedGame) {
        String fichero = nameSavedGame + ".txt";

        try {
            fileOutputStream = context.openFileOutput(fichero, MODE_PRIVATE);
            fileOutputStream.write(juegoCelta.serializaTablero().getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write(String.valueOf(timeViewModel.getMinutes().getValue()).getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write(String.valueOf(timeViewModel.getSeconds().getValue()).getBytes());
            fileOutputStream.close();
            Log.i(LOG_KEY, "Partida guardada en el fichero " + fichero);
            fileOutputStream = context.openFileOutput(NAME_FILE_SAVED_GAME_LIST, Context.MODE_APPEND);
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write(nameSavedGame.getBytes());
            fileOutputStream.close();
            Log.i(LOG_KEY, "Partida " + nameSavedGame + " guardada en el fichero " + NAME_FILE_SAVED_GAME_LIST);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteGameSaved(String fichero) {
        try {
            List<String> listGameSaved = getGamesSaved();
            int index = listGameSaved.indexOf(fichero);
            listGameSaved.remove(index);

            fileOutputStream = context.openFileOutput(fichero + ".txt", MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            Log.i(LOG_KEY, "Partida borrada del fichero " + fichero + ".txt");

            List<String> gameSavedList = getGamesSaved();
            gameSavedList.remove(fichero);

            fileOutputStream = context.openFileOutput(NAME_FILE_SAVED_GAME_LIST, MODE_PRIVATE);
            for (String gameSaved : gameSavedList) {
                fileOutputStream.write(gameSaved.getBytes());
                fileOutputStream.write("\n".getBytes());
            }
            fileOutputStream.close();
            Log.i(LOG_KEY, "Partidas guardadas en el fichero " + NAME_FILE_SAVED_GAME_LIST);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameSaved restoreGame(String fichero) {
        Log.i(LOG_KEY, "Recuperada partida " + fichero + ".txt");
        GameSaved gameSaved = new GameSaved();

        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(context.openFileInput(fichero + ".txt")));
            String linea = fin.readLine();
            if (linea != null) {
                gameSaved.setTablero(linea);
                linea = fin.readLine();
                gameSaved.setMinutes(Integer.decode(linea));
                linea = fin.readLine();
                gameSaved.setSeconds(Integer.decode(linea));
            }
            fin.close();
            deleteGameSaved(fichero);
            return gameSaved;
        } catch (IOException e) {
            e.printStackTrace();
            return gameSaved;
        }
    }

}
