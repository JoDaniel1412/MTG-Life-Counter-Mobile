package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.atlas.mtglifecounter.R;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout infoLayout;
    private boolean inLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoLayout = findViewById(R.id.infoLayout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !inLayout) {
            pressed_start();
        }
        return super.onTouchEvent(event);

    }

    public void pressed_start() {
        Intent animation = new Intent(this, PlayerSelectionActivity.class);
        startActivity(animation);
    }

    public void pressed_info(View view) {
        inLayout = true;
        infoLayout.setVisibility(View.VISIBLE);

    }

    public void pressed_quit_info(View view) {
        inLayout = false;
        infoLayout.setVisibility(View.INVISIBLE);
    }

    public void pressed_settings(View view) {

    }
}
