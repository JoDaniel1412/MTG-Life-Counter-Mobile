package org.atlas.mtglifecounter.controllers;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.graphics.LifeCounter;
import org.atlas.mtglifecounter.logic.Players;
import org.atlas.mtglifecounter.util.Math;

public class GameActivity extends AppCompatActivity {

    private FrameLayout game_layout;
    private boolean game_layout_loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game_layout = findViewById(R.id.game_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGrid();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!game_layout_loaded && event.getAction() == MotionEvent.ACTION_UP) {
            game_layout_loaded = true;
            loadGrid();
        }
        return super.onTouchEvent(event);
    }

    private void loadGrid() {
        int players_selected = PlayerSelectionActivity.players_selected;

        int[] colors = new int[] {Color.CYAN, Color.RED, Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.GREEN};
        int color = 0;

        int columns = (int) java.lang.Math.sqrt(players_selected);
        int rows = Math.ceilingDivision(players_selected, columns);
        int width = game_layout.getWidth();
        int height = game_layout.getHeight();
        int xOffset = width / columns;
        int yOffset = height / rows;
        int x = 0;
        int y = 0;

        int p = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //if (p == 5) xOffset *= 2;
                //if (xOffset > width) xOffset /= 2;

                // Sets the Life Counter Views
                LifeCounter lifeCounter = new LifeCounter(this);
                lifeCounter.setBackgroundColor(colors[color]);
                lifeCounter.setX(x);
                lifeCounter.setY(y);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset, yOffset);
                lifeCounter.setLayoutParams(params);

                // Load the Life Counter
                Players.getPlayers_life().add(lifeCounter);
                game_layout.addView(lifeCounter);

                color += 1;
                x += xOffset;
                p++;
            }
            x = 0;
            y += yOffset;
        }
    }
}
