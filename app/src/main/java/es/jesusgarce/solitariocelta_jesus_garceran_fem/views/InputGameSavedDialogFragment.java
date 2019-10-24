package es.jesusgarce.solitariocelta_jesus_garceran_fem.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.MainActivity;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.R;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.SavedGamesManager;

public class InputGameSavedDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity mainActivity = (MainActivity) getActivity();
        final SavedGamesManager savedGamesManager = new SavedGamesManager(mainActivity, mainActivity.miJuego, mainActivity.timeViewModel);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(R.string.txtTitleSaveGame);
        View viewInflated = LayoutInflater.from(mainActivity).inflate(R.layout.input_game_saved, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.inputGameSaved);
        builder.setView(viewInflated);

        builder.setPositiveButton(R.string.txtDialogYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (savedGamesManager.saveGame(input.getText().toString()))
                    Snackbar.make(
                            mainActivity.findViewById(android.R.id.content),
                            getString(R.string.txtSavedGameOK),
                            Snackbar.LENGTH_LONG
                    ).show();
                else
                    Snackbar.make(
                            mainActivity.findViewById(android.R.id.content),
                            getString(R.string.txtSavedGameFalse),
                            Snackbar.LENGTH_LONG
                    ).show();
            }
        });
        builder.setNegativeButton(R.string.txtDialogNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing to do
            }
        });

        return builder.create();
    }

}
