package DataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import Helpers.Ram;
import Intefaces.CallBackAsync;
import Intefaces.IListViewItem;
import Utilities.DownloadImageTask;
import irdevelopers.asemanweb.R;

/**
 * Created by Alip on 7/28/2015.
 */
public class Group implements IListViewItem, Serializable {
    public String title;
    public String code;
    public String image;
    Bitmap bitmap;
    public Boolean unreadInside;

    public Group(String title, String code, String image) {
        this.title = title;
        this.code = code;
        this.image = image;
    }

    public static ArrayList<Group> getArrayListFromJson(JSONArray jsonarr) {
        ArrayList<Group> groups = new ArrayList<Group>();
        try {

            for (int i = 0; i < jsonarr.length(); i++) {
                JSONObject json = jsonarr.getJSONObject(i);
                groups.add(new Group(json.getString("groupName"), json.getString("groupCode"), json.getString("groupImage")));
            }


        } catch (JSONException e) {
            e.printStackTrace();

        }
        return groups;
    }

    @Override
    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof Group)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_group, null);
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
        holder.group = this;

        if (holder.title == null)
            holder.title = (TextView) view.findViewById(R.id.title);

        if (holder.haveNew == null)
            holder.haveNew = (TextView) view.findViewById(R.id.textViewHaveNew);

        if (holder.image == null)
            holder.image = (ImageView) view.findViewById(R.id.groupImage);

        if (Ram.updatedGroups != null) {
            if (Ram.updatedGroups.contains(code)) {
                // have new chile
                holder.haveNew.setText("خبر جدید");
            }
        }
        holder.title.setText(this.title);

        if(this.unreadInside!= null && this.unreadInside==true)
        {
        // hilight
            holder.title.setTextColor(Color.parseColor("#000000"));


        }else{
            // fade
            holder.title.setTextColor(Color.parseColor("#8c8c8c"));

        }


        if (image !=null && image.length()>1) {
            // load from address

            new DownloadImageTask(new CallBackAsync<Bitmap>() {


                @Override
                public void onSuccessFinish(Bitmap result) {
                    bitmap = result;
                    holder.image.setImageBitmap(result);
                }

                @Override
                public void onError(String errorMessage) {
                }
            }).execute(image);

        } else {
            // show default image
            holder.image.setImageResource(R.drawable.group_icon);
        }


    }

    public class Holder {
        public Group group;
        TextView title;
        TextView haveNew;
        ImageView image;
    }
}
