package es.jesusgarce.solitariocelta_jesus_garceran_fem.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.util.List;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.GameSaved;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.MainActivity;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.R;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.SavedGamesManager;

public class ListGamesFragment extends DialogFragment {

    public final String LOG_KEY = "JGS";
    String[] gameSavedArray;
    SavedGamesManager savedGamesManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity mainActivity = (MainActivity) getActivity();
        savedGamesManager = new SavedGamesManager(mainActivity, mainActivity.miJuego, mainActivity.timeViewModel);

        List<String> listGames = savedGamesManager.getGamesSaved();

        gameSavedArray = new String[listGames.size()];
        for (int i = 0; i < listGames.size(); i++) {
            gameSavedArray[i] = listGames.get(i);
        }

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

                        Snackbar.make(
                                mainActivity.findViewById(android.R.id.content),
                                getString(R.string.txtDialogRestoreOK),
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                });
        return builder.create();
    }

}
