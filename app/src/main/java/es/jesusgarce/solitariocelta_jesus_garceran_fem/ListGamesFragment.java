package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ListGamesFragment extends DialogFragment {

    private static String NAME_FILE_SAVED_GAME = "listGamesSaved.txt";
    public final String LOG_KEY = "JGS";
    String[] gameSavedArray;
    SavedGamesManager savedGamesManager;
    int index;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity mainActivity = (MainActivity) getActivity();
        savedGamesManager = new SavedGamesManager(mainActivity, mainActivity.miJuego, mainActivity.timeViewModel);
        receiveGameList();

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(R.string.txtOpcionRecuperar)
                .setItems(gameSavedArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(LOG_KEY, "Seleccionada posicion: " + which);

                        GameSaved gameSaved = savedGamesManager.restoreGame(gameSavedArray[which]);
                        mainActivity.miJuego.deserializaTablero(gameSaved.getTablero());
                        mainActivity.timeViewModel.setMinutes(gameSaved.getMinutes());
                        mainActivity.timeViewModel.setSeconds(gameSaved.getSeconds());
                        mainActivity.mostrarTablero();
                    }
                });
        return builder.create();
    }

    private void receiveGameList() {
        List<String> listGames = new ArrayList<>();
        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(getActivity().openFileInput((NAME_FILE_SAVED_GAME))));
            String linea = fin.readLine();
            while (linea != null) {
                if (!linea.equals(""))
                    listGames.add(linea);
                linea = fin.readLine();
            }
            fin.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        gameSavedArray = new String[listGames.size()];
        for (int i = 0; i < listGames.size(); i++) {
            gameSavedArray[i] = listGames.get(i);
            System.out.println("gamesavedarray[" + index + "] = " + gameSavedArray[i]);
        }
    }
}
