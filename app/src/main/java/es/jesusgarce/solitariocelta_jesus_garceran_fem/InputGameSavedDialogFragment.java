package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class InputGameSavedDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity mainActivity = (MainActivity) getActivity();
        final SavedGamesManager savedGamesManager = new SavedGamesManager(mainActivity, mainActivity.miJuego, mainActivity.timeViewModel);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Nombre:");
        View viewInflated = LayoutInflater.from(mainActivity).inflate(R.layout.input_game_saved, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.inputGameSaved);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savedGamesManager.saveGame(input.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing to do
            }
        });

        return builder.create();
    }

}
