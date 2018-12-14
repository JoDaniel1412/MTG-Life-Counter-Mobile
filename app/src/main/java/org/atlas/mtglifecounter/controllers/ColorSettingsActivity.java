package org.atlas.mtglifecounter.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.graphics.ColorSelector;
import org.atlas.mtglifecounter.graphics.Colors;
import org.atlas.mtglifecounter.graphics.LifeCounter;
import org.atlas.mtglifecounter.logic.Players;
import org.atlas.mtglifecounter.util.Math;

public class ColorSettingsActivity extends AppCompatActivity {

    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_settings);

        layout = findViewById(R.id.colors_pane);
        loadColors();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            loadColors();
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void loadColors() {
        int colors_size = Colors.colors.length;
        int columns = (int) java.lang.Math.sqrt(colors_size);
        int rows = Math.ceilingDivision(colors_size, columns);
        int width = layout.getWidth();
        int height = layout.getHeight();
        int xOffset = width / columns;
        int yOffset = height / rows;
        int x = xOffset / 4;
        int y = yOffset / 4;

        int color = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (color >= colors_size) return;

                // Sets the Rectangle in the Canvas
                ColorSelector rect = new ColorSelector(this);
                rect.setX(x);
                rect.setY(y);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset/2, yOffset/2);
                rect.setLayoutParams(params);
                rect.setBackgroundColor(Colors.colors[color]);
                rect.setColor(Colors.colors[color]);
                rect.setOnTouchListener(this::colorSelected);

                // Load the Rect
                layout.addView(rect);
                x += xOffset;
                color++;
            }
            x = xOffset / 4;
            y += yOffset;
        }
    }

    private boolean colorSelected(View v, MotionEvent event) {
        ColorSelector rect = (ColorSelector) v;
        Players.colors.set(GameActivity.currentPlayerSettings, rect.getColor());
        Intent animation = new Intent(this, GameActivity.class);
        startActivity(animation);
        return true;
    }
}
