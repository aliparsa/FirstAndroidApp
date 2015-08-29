package DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Intefaces.IListViewItem;
import irdevelopers.asemanweb.R;

/**
 * Created by Alip on 7/26/2015.
 */
public class Soal implements IListViewItem {
    public String question;
    public String answer;

    public Soal(String q, String a) {
        question = q;
        answer = a;
    }

    public static ArrayList<Soal> getArrayListFromJson(JSONArray jsonarr) {
        ArrayList<Soal> soals = new ArrayList<Soal>();
        try {


            for (int i = 0; i < jsonarr.length(); i++) {

                JSONObject json = jsonarr.getJSONObject(i);

                soals.add(new Soal(json.getString("question"), json.getString("answer")));
            }


        } catch (JSONException e) {
            e.printStackTrace();

        }
        return soals;
    }

    @Override
    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof Soal)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_soal, null);
            final Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(context, holder, oldView);
            return oldView;
        } else {
            Holder holder = (Holder) oldView.getTag();
            getItem(context, holder, oldView);
            return oldView;
        }
    }

    @Override
    public void setSelected(boolean flag) {

    }

    private void getItem(final Context context, final Holder holder, View view) {
        holder.soal = this;

        if (holder.question == null)
            holder.question = (TextView) view.findViewById(R.id.question);

        if (holder.answer == null)
            holder.answer = (TextView) view.findViewById(R.id.answer);

        holder.question.setText(this.question);
        holder.answer.setText(this.answer);

        //  FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.question);
        //  FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.answer);

    }

    public class Holder {
        public Soal soal;
        TextView question;
        TextView answer;
    }
}
