package Helpers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import Intefaces.OnActionBarClickListener;
import Views.TextViewFont;
import irdevelopers.asemanweb2.R;

/**
 * Created by Ali on 9/24/2015.
 */
public class ActionBarHelper {

    public static void setBackActionbar(Context context,ActionBar actionBar,String title, final OnActionBarClickListener onActionBarClickListener){
        final ViewGroup actionBarLayout = (ViewGroup) ((Activity)context).getLayoutInflater().inflate(
                R.layout.action_bar_custom,
                null);

        // Set up your ActionBar

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        android.support.v7.app.ActionBar.LayoutParams layout = new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        actionBar.setCustomView(actionBarLayout, layout);

        // find views
        ImageView back = (ImageView) actionBarLayout.findViewById(R.id.action_bar_back);
        TextViewFont title_tx = (TextViewFont) actionBarLayout.findViewById(R.id.action_bar_title);

        // set - unset  visibility
        back.setVisibility(View.VISIBLE);

        // set data
        title_tx.setText(title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarClickListener.onBackPressed();
            }
        });

    }

    public static void setBackReloadActionbar(Context context,ActionBar actionBar,String title, final OnActionBarClickListener onActionBarClickListener){
        final ViewGroup actionBarLayout = (ViewGroup) ((Activity)context).getLayoutInflater().inflate(
                R.layout.action_bar_custom,
                null);

        // Set up your ActionBar

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        android.support.v7.app.ActionBar.LayoutParams layout = new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        actionBar.setCustomView(actionBarLayout, layout);

        // find views
        ImageView back = (ImageView) actionBarLayout.findViewById(R.id.action_bar_back);
        ImageView reload = (ImageView) actionBarLayout.findViewById(R.id.action_bar_reload);
        TextViewFont title_tx = (TextViewFont) actionBarLayout.findViewById(R.id.action_bar_title);

        // set - unset  visibility
        back.setVisibility(View.VISIBLE);
        reload.setVisibility(View.VISIBLE);


        // set data
        title_tx.setText(title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarClickListener.onBackPressed();
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarClickListener.onReloadPressed();
            }
        });

    }


    public static void setBackSendActionbar(Context context,ActionBar actionBar,String title, final OnActionBarClickListener onActionBarClickListener){
        final ViewGroup actionBarLayout = (ViewGroup) ((Activity)context).getLayoutInflater().inflate(
                R.layout.action_bar_custom,
                null);

        // Set up your ActionBar

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        android.support.v7.app.ActionBar.LayoutParams layout = new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        actionBar.setCustomView(actionBarLayout, layout);

        // find views
        ImageView back = (ImageView) actionBarLayout.findViewById(R.id.action_bar_back);
        ImageView send = (ImageView) actionBarLayout.findViewById(R.id.action_bar_send);
        TextViewFont title_tx = (TextViewFont) actionBarLayout.findViewById(R.id.action_bar_title);

        // set - unset  visibility
        back.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);


        // set data
        title_tx.setText(title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarClickListener.onBackPressed();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarClickListener.onSendPresses();
            }
        });

    }

    public static void setIconSettingActionbar(Context context,ActionBar actionBar,String title, final OnActionBarClickListener onActionBarClickListener){
        final ViewGroup actionBarLayout = (ViewGroup) ((Activity)context).getLayoutInflater().inflate(
                R.layout.action_bar_custom,
                null);

        // Set up your ActionBar

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        android.support.v7.app.ActionBar.LayoutParams layout = new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        actionBar.setCustomView(actionBarLayout, layout);

        // find views
        ImageView icon = (ImageView) actionBarLayout.findViewById(R.id.action_bar_icon);
        ImageView setting = (ImageView) actionBarLayout.findViewById(R.id.action_bar_setting);
        TextViewFont title_tx = (TextViewFont) actionBarLayout.findViewById(R.id.action_bar_title);

        // set - unset  visibility
        setting.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);

        // set data
        title_tx.setText(title);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarClickListener.onSettingPresses();
            }
        });

    }

}
