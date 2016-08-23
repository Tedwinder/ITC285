package com.wordgames.tedcu76.portabledictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
//import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

/**
 * Created by tedcu76 on 8/22/16.
 */
public class AktionBar extends AppCompatActivity {

    android.app.ActionBar actionBar;

    public void setActionBar(android.app.ActionBar actionBar) {
        this.actionBar = actionBar;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.cutom_action_bar_layout);
        View view = getSupportActionBar().getCustomView();


        //ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_forward);
    }


    @Nullable
    @Override
    public android.app.ActionBar getActionBar() {
        return actionBar;
    }
}
