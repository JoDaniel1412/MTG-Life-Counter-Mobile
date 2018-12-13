package org.atlas.mtglifecounter.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import org.atlas.mtglifecounter.controllers.SetupGameActivity;

public class LifeCounter extends View {

    private int life = SetupGameActivity.starting_life;
    private Canvas canvas;

    // Paints
    Paint textPaint = new Paint();

    public LifeCounter(Context context) {
        super(context);
    }

    private void reload() {
        textPaint.setTextSize(120);
        textPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        reload();

        // Set the life
        loadLifeText();
    }

    private void loadLifeText() {
        float offset = textPaint.getTextSize() / 2;
        float x = canvas.getWidth() / 2 - offset;
        float y = canvas.getHeight() / 2 + offset / 2;
        canvas.drawText(String.valueOf(life), x, y, textPaint);

    }
}
