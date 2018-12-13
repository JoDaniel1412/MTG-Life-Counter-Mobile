package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.graphics.Colors;
import org.atlas.mtglifecounter.graphics.LifeCounter;
import org.atlas.mtglifecounter.logic.Players;
import org.atlas.mtglifecounter.util.Math;

public class GameActivity extends AppCompatActivity {

    private FrameLayout game_layout;
    private boolean game_layout_loaded = false;
    private int[] colors = Colors.colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game_layout = findViewById(R.id.game_layout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!game_layout_loaded && event.getAction() == MotionEvent.ACTION_UP) {
            game_layout_loaded = true;
            loadGrid();
        }
        return super.onTouchEvent(event);
    }

    public void pressedColorSettings(View view) {
        Intent animation = new Intent(this, ColorSettingsActivity.class);
        startActivity(animation);
    }

    private void loadGrid() {
        int players_selected = PlayerSelectionActivity.players_selected;

        int columns = (int) java.lang.Math.sqrt(players_selected);
        int rows = Math.ceilingDivision(players_selected, columns);
        int width = game_layout.getWidth();
        int height = game_layout.getHeight();
        int xOffset = width / columns;
        int yOffset = height / rows;
        int x = 0;
        int y = 0;

        int p = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int color = Math.getRandomNumberInRange(0, colors.length - 1);

                // Sets the Life Counter Views
                LifeCounter lifeCounter = new LifeCounter(this);
                lifeCounter.setX(x);
                lifeCounter.setY(y);
                lifeCounter.setPlayer(p);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset, yOffset);
                lifeCounter.setLayoutParams(params);
                if (Players.colors.size() <= p) {
                    Players.colors.add(p, colors[color]);
                }
                lifeCounter.setBackgroundColor(Players.colors.get(p));

                // Load the Life Counter
                game_layout.addView(lifeCounter);

                x += xOffset;
                p++;
            }
            x = 0;
            y += yOffset;
        }
    }
}
