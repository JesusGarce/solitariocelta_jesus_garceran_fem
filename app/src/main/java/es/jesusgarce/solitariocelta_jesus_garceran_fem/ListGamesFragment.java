package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ListGamesFragment extends Activity implements AdapterView.OnItemClickListener {

    private static String NAME_FILE_SAVED_GAME = "lastGameList.txt";
    public final String LOG_KEY = "JGS";
    List<GameSaved> gameSavedList;
    ListView gamesSaved;

    public ListGamesFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_games);

        gameSavedList = new LinkedList<>();
        receiveGameList();

        ListGamesAdapter adapter = new ListGamesAdapter(this, R.layout.list_games, gameSavedList);

        Log.i(LOG_KEY, adapter.toString());

        gamesSaved = findViewById(R.id.listGamesSaved);

        gamesSaved.setAdapter(adapter);
        gamesSaved.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(LOG_KEY, "Seleccionada posicion: "+position);

    }

    private void receiveGameList() {
        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(openFileInput(NAME_FILE_SAVED_GAME)));
            String linea = fin.readLine();
            int index = 1;
            GameSaved gameSaved;
            while (linea != null) {
                String tablero = linea;
                linea = fin.readLine();
                String consumedTime = linea;
                linea = fin.readLine();
                consumedTime = consumedTime + ":" + linea;
                linea = fin.readLine();
                int fichasRestantes = Integer.parseInt(linea);
                gameSaved = new GameSaved("Partida guardada "+index, consumedTime, "Jesús", tablero, fichasRestantes);
                gameSavedList.add(gameSaved);
                Log.i(LOG_KEY, "Partida guardada: "+ gameSaved.toString());
                index++;
                linea = fin.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
