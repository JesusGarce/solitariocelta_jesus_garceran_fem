package es.jesusgarce.solitariocelta_jesus_garceran_fem.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.jesusgarce.solitariocelta_jesus_garceran_fem.R;
import es.jesusgarce.solitariocelta_jesus_garceran_fem.models.Score;

public class BestScoreAdapter extends ArrayAdapter {

    private Context context;
    private int idLayout;
    private List<Score> scoreList;

    public BestScoreAdapter(@NonNull Context context, int resource, List<Score> objects) {
        super(context, resource, objects);

        this.context = context;
        idLayout = resource;
        scoreList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout view;

        if (null != convertView) {
            view = (LinearLayout) convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(idLayout, parent, false);
        }

        TextView positionText = view.findViewById(R.id.scorePosition);
        positionText.setText(String.valueOf(position + 1));

        TextView name = view.findViewById(R.id.nameUser);
        name.setText(scoreList.get(position).getNombreUsuario());

        String dataScoreString = scoreList.get(position).getFichasRestantes() + " fichas restantes || " + scoreList.get(position).getTiempo() + " || " + scoreList.get(position).getFecha();
        TextView dataScore = view.findViewById(R.id.dataScore);
        dataScore.setText(dataScoreString);

        ImageView imageView = view.findViewById(R.id.imgScore);
        if (position == 0)
            imageView.setImageResource(R.drawable.first_prize);
        else if (position == 1)
            imageView.setImageResource(R.drawable.second_prize);
        else if (position == 2)
            imageView.setImageResource(R.drawable.third_prize);

        return view;
    }

}
