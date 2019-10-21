package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;

public class DeleteScoreDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BestScore bestScore = (BestScore) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(bestScore);
        builder
                .setTitle(R.string.txtDialogoBorrarTitulo)
                .setMessage(R.string.txtDialogoBorrarPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogYes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bestScore.deleteAll();
                                bestScore.recreate();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogNo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Acción opción No
                            }
                        }
                );

        return builder.create();
    }
}
