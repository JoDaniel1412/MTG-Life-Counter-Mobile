package org.atlas.mtglifecounter.controllers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.atlas.mtglifecounter.R;
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

    private void loadColors() {
        int colors_size = Colors.colors.length;
        int columns = (int) java.lang.Math.sqrt(colors_size);
        int rows = Math.ceilingDivision(colors_size, columns);
        int width = 400;
        int height = 400;
        int xOffset = width / columns;
        int yOffset = height / rows;
        int x = 0;
        int y = 0;

        int color = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (color >= colors_size) return;

                // Sets the Rectangle in the Canvas
                Paint paint = new Paint();
                paint.setColor(Colors.colors[color]);
                Rect rect = new Rect();
                rect.set(x, y, xOffset, yOffset);
                Canvas canvas = new Canvas();
                canvas.drawRect(rect, paint);

                // Loads the Canvas
                layout.draw(canvas);
                x += xOffset;
                color++;
            }
            x = 0;
            y += yOffset;
        }
    }
}
