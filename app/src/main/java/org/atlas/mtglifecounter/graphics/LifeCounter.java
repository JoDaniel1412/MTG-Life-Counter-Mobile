package org.atlas.mtglifecounter.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.controllers.GameActivity;
import org.atlas.mtglifecounter.controllers.SetupGameActivity;

public class LifeCounter extends View {

    private GameActivity parent;
    private int life = SetupGameActivity.starting_life;
    private Canvas canvas;
    private Color color;
    private Sprite settingsSprite;
    private int player;

    // Paints
    Paint textPaint = new Paint();

    public LifeCounter(Context context) {
        super(context);
        parent = (GameActivity) context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        init();

        // Sets the life
        loadLifeText();

        // Sets the buttons
        loadButtons();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!pressedSettings(event.getX(), event.getY())) {
                    pressedLifeCounter(event.getY());
                }
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                pressedLifeCounter(event.getY());
                break;
        }
        this.invalidate();
        return true;
    }

    private void init() {
        textPaint.setTextSize(this.getWidth() / 3);
        textPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorWhiteText));
    }

    private void pressedLifeCounter(float y) {
        float hh = canvas.getHeight() / 2;
        if (y < hh) life++;
        if (y >= hh) life--;
    }

    private boolean pressedSettings(float x, float y) {
        boolean pressed = false;
        if (x > settingsSprite.getX() && x < settingsSprite.getX() + settingsSprite.getWidth()) {
            if (y > settingsSprite.getY() && y < settingsSprite.getY() + settingsSprite.getHeight()) {
                pressed = true;
                parent.pressedColorSettings(this);
            }
        }

        return pressed;
    }

    private void loadLifeText() {
        float offset = textPaint.getTextSize() / 2;
        float x = canvas.getWidth() / 2 - offset;
        float y = canvas.getHeight() / 2 + offset / 2;
        canvas.drawText(String.valueOf(life), x, y, textPaint);
    }

    private void loadButtons() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.settings);
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        int y = this.getHeight() / 16;
        int x = this.getWidth() - bitmap.getWidth() - y;

        settingsSprite = new Sprite(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap);
        canvas.drawBitmap(bitmap, x, y , null);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
