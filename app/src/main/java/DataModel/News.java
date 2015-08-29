package DataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import Intefaces.CallBackAsync;
import Intefaces.IListViewItem;
import Utilities.DownloadImageTask;
import irdevelopers.asemanweb.R;

/**
 * Created by Alip on 7/28/2015.
 */
public class News implements IListViewItem, Serializable {
    public String groupCode;
    public String title;
    public String image;
    public String content;
    public Bitmap bitmap;
    public String url;
    public String uid;
    public boolean readed = false;
    public boolean nofified = false;

    public News(String title, String image, String url, String uid) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.uid = uid;

    }

    public News(String title, String image, String url, String uid, String groupCode) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.uid = uid;
        this.groupCode = groupCode;


    }

    public static ArrayList<News> getArrayListFromJson(JSONArray jsonarr) {
        ArrayList<News> newses = new ArrayList<News>();
        try {

            for (int i = 0; i < jsonarr.length(); i++) {
                JSONObject json = jsonarr.getJSONObject(i);
                if (json.has("groupCode"))
                    newses.add(new News(json.getString("title"), json.getString("image"), json.getString("url"), json.getString("uid"), json.getString("groupCode")));
                else
                    newses.add(new News(json.getString("title"), json.getString("image"), json.getString("url"), json.getString("uid")));
            }


        } catch (JSONException e) {
            e.printStackTrace();

        }
        return newses;
    }


    public static String getJsonFromArrayList(ArrayList<News> newses) {
        JSONArray jsonArray = new JSONArray();

        for (News news : newses) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("title", news.title);
                jsonObject.accumulate("image", news.image);
                //jsonObject.accumulate("content", news.content);
                jsonObject.accumulate("url", news.url);
                jsonObject.accumulate("uid", news.uid);
                jsonObject.accumulate("readed", news.readed);
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return jsonArray.toString();
    }

    @Override
    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof News)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_news, null);
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
        holder.news = this;

        if (holder.title == null)
            holder.title = (TextView) view.findViewById(R.id.title);

        if (holder.imageView == null)
            holder.imageView = (ImageView) view.findViewById(R.id.newsImage);

        holder.title.setText(this.title);

        if (image !=null && image.length()>1) {
            // load from address

            new DownloadImageTask(new CallBackAsync<Bitmap>() {
                @Override
                public void onBeforStart() {
                }

                @Override
                public void onSuccessFinish(Bitmap result) {
                    bitmap = result;
                    holder.imageView.setImageBitmap(result);
                }

                @Override
                public void onError(String errorMessage) {
                }
            }).execute(image);

        } else {
            // show default image
            holder.imageView.setImageResource(R.drawable.newsss);
        }


    }

    public class Holder {
        public News news;
        TextView title;
        ImageView imageView;
    }
}
