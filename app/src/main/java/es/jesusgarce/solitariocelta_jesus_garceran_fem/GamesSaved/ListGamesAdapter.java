package es.jesusgarce.solitariocelta_jesus_garceran_fem.GamesSaved;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.R;

public class ListGamesAdapter extends ArrayAdapter {

    private Context context;
    private int idLayout;
    private List<GameSaved> myGames;

    public ListGamesAdapter(@NonNull Context context, int resource, List<GameSaved> objects) {
        super(context, resource, objects);

        this.context = context;
        idLayout = resource;
        myGames = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout view;

        if (null != convertView) {
            view = (LinearLayout) convertView;
        } else {
            view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.game, parent, false);
        }

        TextView name = view.findViewById(R.id.nameGame);
        name.setText(myGames.get(position).getName());

        TextView dateAndUserGame = view.findViewById(R.id.dateUserAndRemainsGame);
        String dateAndUserGameString = myGames.get(position).getUserName() + " || " + myGames.get(position).getConsumedTime() + " || " +myGames.get(position).getFichasRestantes() + " fichas restantes.";
        dateAndUserGame.setText(dateAndUserGameString);

        return view;
    }

    @Override
    public String toString() {
        return "ListGamesAdapter{" +
                "context=" + context +
                ", idLayout=" + idLayout +
                ", myGames=" + myGames +
                '}';
    }
}
