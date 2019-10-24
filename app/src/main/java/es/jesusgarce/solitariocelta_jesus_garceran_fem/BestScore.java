package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.models.RepositoryScore;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.models.Score;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.views.BestScoreAdapter;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.views.DeleteScoreDialogFragment;

public class BestScore extends AppCompatActivity implements AdapterView.OnItemClickListener {

    RepositoryScore scoreDB;
    Button buttonDelete;
    BestScoreAdapter adapter;
    private List<Score> scoreList;
    private ListView lvScores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkMode = sharedPref.getBoolean(getString(R.string.prefKeyModoOscuro), false);

        if (darkMode)
            setTheme(R.style.AppThemeDark);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_best_score);

        scoreDB = new RepositoryScore(getApplicationContext());
        scoreList = scoreDB.getAllByFichasRestantes();

        adapter = new BestScoreAdapter(this, R.layout.score_list, scoreList);

        Log.i("JGS", "scoreDB: MyAdapter: = " + scoreList);

        buttonDelete = findViewById(R.id.buttonDeleteScores);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment deleteScoreDialogFragment = new DeleteScoreDialogFragment();
                deleteScoreDialogFragment.show(getFragmentManager(), "DeleteScoreDialogFragment");
            }
        });

        lvScores = findViewById(R.id.listViewScores);
        lvScores.setAdapter(adapter);
        lvScores.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //nothing to do
    }

    public void deleteAll() {
        scoreDB.deleteAll();
        adapter.clear();
    }

}
