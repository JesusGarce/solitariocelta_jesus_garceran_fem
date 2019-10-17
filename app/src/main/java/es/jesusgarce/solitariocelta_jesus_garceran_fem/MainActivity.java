package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static String NAME_FILE_SAVED_GAME = "lastGame.txt";
    public final String LOG_KEY = "JGS";
    SCeltaViewModel miJuego;
    private boolean changesInTheGame = false;
    private FileOutputStream fileOutputStream;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miJuego = ViewModelProviders.of(this).get(SCeltaViewModel.class);

        mostrarTablero();
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre del recurso, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(@NotNull View v) {
        changesInTheGame = true;

        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        Log.i(LOG_KEY, "fichaPulsada(" + i + ", " + j + ") - " + resourceName);
        miJuego.jugar(i, j);
        Log.i(LOG_KEY, "#fichas=" + miJuego.numeroFichas());

        mostrarTablero();
        if (miJuego.juegoTerminado()) {
            // TODO guardar puntuación
            new ExitDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        ImageView button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + i + j;
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = findViewById(idBoton);
                    if (miJuego.obtenerFicha(i, j) != JuegoCelta.FICHA)
                        button.setImageDrawable(getResources().getDrawable(R.drawable.empty_token));
                    else
                        button.setImageDrawable(getResources().getDrawable(R.drawable.orange_token));
                }
            }
    }

    /**
     * Reinicia la partida
     */

    public void restartGame() {
        Log.i(LOG_KEY, "Juego reiniciado");
        miJuego.reiniciar();
        mostrarTablero();
    }

    /**
     * Guardar la partida
     */
    private void saveGame() {
        try {
            fileOutputStream = openFileOutput(NAME_FILE_SAVED_GAME, MODE_PRIVATE);
            fileOutputStream.write(miJuego.serializaTablero().getBytes());
            fileOutputStream.close();
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.txtSavedGameOK),
                    Snackbar.LENGTH_LONG).show();
            Log.i(LOG_KEY, "Partida guardada en el fichero " + NAME_FILE_SAVED_GAME);
        } catch (IOException e) {
            e.printStackTrace();
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.txtSavedGameFalse),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void deleteGameSaved() {
        try {
            fileOutputStream = openFileOutput(NAME_FILE_SAVED_GAME, MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            Log.i(LOG_KEY, "Partida borrada del fichero " + NAME_FILE_SAVED_GAME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreGame() {
        boolean thereIsSavedGame = false;

        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(openFileInput(NAME_FILE_SAVED_GAME)));
            String linea = fin.readLine();
            if (linea != null) {
                thereIsSavedGame = true;
                miJuego.deserializaTablero(linea);
                Log.i(LOG_KEY, "Partida: " + linea + " recuperada del fichero " + NAME_FILE_SAVED_GAME);
            }
            fin.close();
            mostrarTablero();
            deleteGameSaved();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!thereIsSavedGame) {
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.txtNoSavedGame),
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            case R.id.opcReiniciarPartida:
                DialogFragment restartDialogFragment = new RestartDialogFragment();
                restartDialogFragment.show(getFragmentManager(), "RestartDialog");
                return true;
            case R.id.opcGuardarPartida:
                saveGame();
                return true;
            case R.id.opcRecuperarPartida:
                if (changesInTheGame) {
                    DialogFragment restoreDialogFragment = new RestoreDialogFragment();
                    restoreDialogFragment.show(getFragmentManager(), "RestoreDialog");
                } else {
                    restoreGame();
                }

                return true;

            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }
}
