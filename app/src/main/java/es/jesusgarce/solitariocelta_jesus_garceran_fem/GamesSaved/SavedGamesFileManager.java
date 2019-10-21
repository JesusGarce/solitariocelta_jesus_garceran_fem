package es.jesusgarce.solitariocelta_jesus_garceran_fem.GamesSaved;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SavedGamesFileManager {

    public final String LOG_KEY = "JGS";
    private String fileStoreName;
    private FileOutputStream fileOutputStream;

    SavedGamesFileManager(String fileStoreName){
        this.fileStoreName = fileStoreName;
    }

    private boolean storeGame(GameSaved gameSaved){
        try {
            //fileOutputStream = openFileOutput(fileStoreName, MODE_PRIVATE);
            fileOutputStream.write(gameSaved.getTablero().getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write(String.valueOf(gameSaved.getMinutes()).getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write(String.valueOf(gameSaved.getSeconds()).getBytes());
            fileOutputStream.close();
            Log.i(LOG_KEY, "Partida guardada en el fichero " + fileStoreName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public GameSaved getGameSaved(String nameGameSaved){
        GameSaved gameSaved = new GameSaved();

        try {
            //BufferedReader fin = new BufferedReader(
            //        new InputStreamReader(openFileInput(fileStoreName)));
            BufferedReader fin = new BufferedReader(null);
            String linea = fin.readLine();
            if (linea != null) {
                String tablero = linea;
                Log.i(LOG_KEY, "Partida: " + linea + " recuperada del fichero " + fileStoreName);
                int minutes = Integer.decode(fin.readLine());
                int seconds = Integer.decode(fin.readLine());
                gameSaved = new GameSaved(nameGameSaved, minutes, seconds, "Jesús", tablero, 0);
            }
            fin.close();
            deleteGameSaved(nameGameSaved);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameSaved;
    }

    public void deleteGameSaved(String nameGameSaved){

    }

    public List<GameSaved> readGamesSaved(){
        List<GameSaved> gameSavedList = new LinkedList<>();



        return  gameSavedList;
    }


}
