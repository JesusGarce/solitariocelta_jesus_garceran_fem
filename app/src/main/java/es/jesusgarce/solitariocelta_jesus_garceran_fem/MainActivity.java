package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.models.JuegoCelta;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.models.RepositoryScore;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.viewmodel.SCeltaViewModel;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.viewmodel.TimeViewModel;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.views.ExitDialogFragment;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.views.RestartDialogFragment;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.views.RestoreDialogFragment;

public class MainActivity extends AppCompatActivity {

    private static String NAME_FILE_SAVED_GAME = "lastGame.txt";
    public final String LOG_KEY = "JGS";
    public SCeltaViewModel miJuego;
    public TimeViewModel timeViewModel;
    RepositoryScore scoreDB;
    private boolean changesInTheGame = false;
    private FileOutputStream fileOutputStream;
    private int colorToken;
    private int colorButton;
    private String userName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPreferences();

        setContentView(R.layout.activity_main);

        timeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);
        final Observer<Integer> timeObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                TextView time = findViewById(R.id.tiempoValor);
                time.setText(timeViewModel.getTime());
            }
        };
        timeViewModel.getSeconds().observe(this, timeObserver);
        startTime();

        scoreDB = new RepositoryScore(getApplicationContext());
        Log.i(LOG_KEY, "scoreDB: Count: = " + scoreDB.getAllByFichasRestantes());

        changeButtonColor();

        miJuego = ViewModelProviders.of(this).get(SCeltaViewModel.class);

        mostrarTablero();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void startTime() {
        if (timeViewModel.getMinutes().getValue() != 0 || timeViewModel.getSeconds().getValue() != 0)
            return;

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        increaseTime();
                    }
                });
            }
        }, 0, 1000);

    }

    private void increaseTime() {
        timeViewModel.getSeconds().setValue(timeViewModel.getSeconds().getValue() + 1);

        if (timeViewModel.getSeconds().getValue() == 60) {
            timeViewModel.getMinutes().setValue(timeViewModel.getMinutes().getValue() + 1);
            timeViewModel.getSeconds().setValue(0);
        }
    }

    private void checkPreferences() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        changeColorToken(sharedPref.getString(
                getString(R.string.prefKeyColorFicha),
                getString(R.string.prefDefaultColorFicha)
        ));

        boolean darkMode = sharedPref.getBoolean(getString(R.string.prefKeyModoOscuro), false);

        if (darkMode)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);

        userName = sharedPref.getString(getString(R.string.prefKeyNombreJugador), "Anónimo");

        Log.i(LOG_KEY, "onCREATE: ColorToken = " + colorToken);
        Log.i(LOG_KEY, "darkMode = " + ((darkMode) ? "on" : "off"));
    }

    private void changeButtonColor() {
        ImageView tiempoFrame = findViewById(R.id.firstImageFrame);
        tiempoFrame.setImageDrawable(getResources().getDrawable(colorButton));

        ImageView puntuacionFrame = findViewById(R.id.secondImageFrame);
        puntuacionFrame.setImageDrawable(getResources().getDrawable(colorButton));
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
            addScore();
            new ExitDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    private void addScore() {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                .format(new Date());
        String tiempo = timeViewModel.getTime();

        Long idNewScore = scoreDB.add(userName, tiempo, date, miJuego.numeroFichas());
        Log.i(LOG_KEY, "Creado score con id: " + idNewScore);
        Log.i(LOG_KEY, scoreDB.getAll().toString());
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
                    else {
                        button.setImageDrawable(getResources().getDrawable(colorToken));
                    }
                }
            }

        TextView valueRemainedTokens = findViewById(R.id.fichasRestantesValue);
        valueRemainedTokens.setText(String.valueOf(miJuego.numeroFichas()));
    }

    /**
     * Reinicia la partida
     */

    public void restartGame() {
        Log.i(LOG_KEY, "Juego reiniciado");
        miJuego.reiniciar();
        timeViewModel.restartTime();
        mostrarTablero();
    }

    /**
     * Guardar la partida
     */
    private void saveGame() {
        try {
            fileOutputStream = openFileOutput(NAME_FILE_SAVED_GAME, MODE_PRIVATE);
            fileOutputStream.write(miJuego.serializaTablero().getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write(String.valueOf(timeViewModel.getMinutes().getValue()).getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.write(String.valueOf(timeViewModel.getSeconds().getValue()).getBytes());
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
                linea = fin.readLine();
                timeViewModel.setMinutes(Integer.decode(linea));
                linea = fin.readLine();
                timeViewModel.setSeconds(Integer.decode(linea));
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

    public void changeColorToken(String colorToken) {
        if (colorToken == null) {
            this.colorToken = R.drawable.orange_token;
            this.colorButton = R.drawable.orange_button;
            return;
        }

        switch (colorToken) {
            case "blue_token":
                this.colorToken = R.drawable.blue_token;
                this.colorButton = R.drawable.blue_button;
                break;
            case "green_token":
                this.colorToken = R.drawable.green_token;
                this.colorButton = R.drawable.green_buton;
                break;
            case "pink_token":
                this.colorToken = R.drawable.pink_token;
                this.colorButton = R.drawable.pink_button;
                break;
            case "red_token":
                this.colorToken = R.drawable.red_token;
                this.colorButton = R.drawable.red_button;
                break;
            case "yellow_token":
                this.colorToken = R.drawable.yellow_token;
                this.colorButton = R.drawable.yellow_button;
                break;
            default:
                this.colorToken = R.drawable.orange_token;
                this.colorButton = R.drawable.orange_button;
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

            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, BestScore.class));
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
