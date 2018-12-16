package org.atlas.mtglifecounter.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Player;

public class LifeCounter extends View {

    // Sprites
    private Sprite commanderSprite;
    private Sprite settingsSprite;
    private Sprite poisonSprite;
    private Sprite lifeSprite;
    private Sprite currentCounterSprite = lifeSprite;

    // Attrs
    private Player player;
    private int color;
    private Canvas canvas;
    private boolean loaded;

    // Paints
    Paint textPaint = new Paint();

    public LifeCounter(Context context) {
        super(context);
    }

    private void loadColors() {
        textPaint.setTextSize(this.getWidth() / 3);
        textPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorWhiteText));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!loaded) {
            loaded = true;
            this.canvas = canvas;
            loadSprites();
            loadColors();
        }
        updateLifeText();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                if (pressedSprite(x, y, settingsSprite)) {
                    pressedSettings();
                } else if (pressedSprite(x, y, poisonSprite) || pressedSprite(x, y, lifeSprite)) {
                    pressedCurrentCounter();
                } else if (pressedSprite(x, y, commanderSprite)) {
                    pressedCommander();
                } else {
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

    private void pressedLifeCounter(float y) {
        int life = player.getLife();

        float hh = canvas.getHeight() / 2;
        if (y < hh) player.setLife(life + 1);
        if (y >= hh) player.setLife(life - 1);
    }

    private boolean pressedSprite(float x, float y, Sprite sprite) {
        boolean pressed = false;
        if (x > sprite.getX() && x < sprite.getX() + sprite.getWidth()) {
            if (y > sprite.getY() && y < sprite.getY() + sprite.getHeight()) {
                pressed = true;
            }
        }

        return pressed;
    }

    private void pressedCommander() {
    }

    private void pressedCurrentCounter() {
    }

    private void pressedSettings() {
    }


    private void updateLifeText() {
        float offset = textPaint.getTextSize() / 2;
        float x = canvas.getWidth() / 2 - offset;
        float y = canvas.getHeight() / 2 + offset / 2;

        canvas.drawText(String.valueOf(player.getLife()), x, y, textPaint);
    }

    private void loadSprites() {
        // Loads the Setting Sprite
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.settings);
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        int y = this.getHeight() / 16;
        int x = this.getWidth() - bitmap.getWidth() - y;

        settingsSprite = new Sprite(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap);
        canvas.drawBitmap(bitmap, x, y, null);

        // Loads the Life/Poison Sprites

        // Loads the Commander Sprite
    }

    /** Getters and Setters **/

    public Sprite getCommanderSprite() {
        return commanderSprite;
    }

    public void setCommanderSprite(Sprite commanderSprite) {
        this.commanderSprite = commanderSprite;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
