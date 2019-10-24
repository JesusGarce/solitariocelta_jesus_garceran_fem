package es.jesusgarce.solitariocelta_jesus_garceran_fem.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.MainActivity;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.R;

public class RestoreDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity mainActivity = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.txtDialogRestore))
                .setMessage(getString(R.string.txtDialogRestoreDescription))
                .setPositiveButton(
                        getString(R.string.txtDialogYes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DialogFragment listGamesDialogFragment = new ListGamesFragment();
                                listGamesDialogFragment.show(getFragmentManager(), "listGamesDialog");
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
