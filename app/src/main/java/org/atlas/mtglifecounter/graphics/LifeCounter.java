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
import org.atlas.mtglifecounter.controllers.GameActivity;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;

public class LifeCounter extends View {

    private Game game = Game.getInstance();
    private GameActivity gameActivity;
    private Player player;
    private int color;
    private Canvas canvas;
    private boolean poison;
    private boolean touchable = true;

    // Sprites
    private Sprite commanderSprite;
    private Sprite vanguardSprite;
    private Sprite settingsSprite;
    private Sprite poisonSprite;
    private Sprite lifeSprite;
    private Sprite poisonWaterMark;

    // Paints
    Paint LifeTextPaint = new Paint();
    Paint NameTextPaint = new Paint();
    Paint lifePaint = new Paint();
    Paint poisonPaint = new Paint();

    public LifeCounter(Context context) {
        super(context);
        init();
    }

    private void init() {
        poisonPaint.setAlpha(100);
    }

    private void loadPaints() {
        LifeTextPaint.setTextSize(this.getWidth() / 3);
        LifeTextPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorWhiteText));
        NameTextPaint.setTextSize(this.getWidth() / 8);
        NameTextPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorWhiteText));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        loadPaints();
        drawWaterMarks();
        drawSprites();
        updateLifeText();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!touchable) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                if (pressedSprite(x, y, settingsSprite)) {
                    pressedSettings();
                } else if (pressedSprite(x, y, poisonSprite)) {
                    pressedPoison();
                } else if (pressedSprite(x, y, lifeSprite)) {
                    pressedLife();
                } else if (game.isCommander() && pressedSprite(x, y, commanderSprite)) {
                    pressedCommander();
                } else if (game.isVanguard() && pressedSprite(x, y, vanguardSprite)) {
                    pressedVanguard();
                } else {
                    pressedLifeCounter(y);
                }
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                //pressedLifeCounter(event.getY());
                break;
        }
        this.invalidate();
        return true;
    }

    private void pressedLifeCounter(float y) {
        float hh = canvas.getHeight() / 2;
        int life;

        if (!poison) {
            life = player.getLife();
            if (y < hh) player.setLife(life + 1);
            if (y >= hh) player.setLife(life - 1);
        } else {
            life = player.getPoison();
            if (y < hh) player.setPoison(life + 1);
            if (y >= hh) player.setPoison(life - 1);
        }
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
        gameActivity.openCommanderLayout(player);
    }

    private void pressedVanguard() {
    }

    private void pressedPoison() {
        poisonPaint.setAlpha(255);
        lifePaint.setAlpha(100);
        poison = true;

        // Draws a watermark
        int width = this.getWidth() * 2 / 3;
        int height = this.getWidth() * 2 / 3;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.poison);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        int x = this.getWidth() / 2 - width / 2;
        int y = this.getHeight() / 2 - height / 2;

        poisonWaterMark = new Sprite(x, y, width, height, bitmap);
    }

    private void pressedLife() {
        lifePaint.setAlpha(255);
        poisonPaint.setAlpha(100);
        poison = false;

        poisonWaterMark = null;
    }

    private void pressedSettings() {
        gameActivity.openColorLayout(player);
    }

    private void updateLifeText() {
        String text;
        if (!poison) text = String.valueOf(player.getLife());
        else text = String.valueOf(player.getPoison());

        float offsetX = LifeTextPaint.getTextSize() / 2;
        float offsetY = offsetX / 2;
        if (text.length() == 1) offsetX /= 2;
        float x = canvas.getWidth() / 2 - offsetX;
        float y = canvas.getHeight() / 2 + offsetY;

        canvas.drawText(text, x, y, LifeTextPaint);
    }

    private void drawSprites() {
        int width = this.getWidth() / 8;
        int height = this.getWidth() / 8;

        // Loads the Setting Sprite
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.settings);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        int y = this.getHeight() / 16;
        int x = this.getWidth() - bitmap.getWidth() - this.getWidth() / 16;

        settingsSprite = new Sprite(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap);
        canvas.drawBitmap(bitmap, x, y, null);

        // Loads the Life Sprite
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        x = this.getWidth() / 16;
        y = this.getHeight() / 16;

        lifeSprite = new Sprite(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap);
        canvas.drawBitmap(bitmap, x, y, lifePaint);

        // Loads the Poison Sprite
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.poison);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        x = this.getWidth() / 16 + width;
        y = this.getHeight() / 16;

        poisonSprite = new Sprite(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap);
        canvas.drawBitmap(bitmap, x, y, poisonPaint);

        // Loads the Name
        x = this.getWidth() / 2 - (int) NameTextPaint.getTextSize() / 2;
        y = this.getHeight() / 18 + height;
        String name = player.getName();
        canvas.drawText(name, x, y, NameTextPaint);

        // Loads the Commander Sprite
        if (game.isCommander()) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.commander17);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            x = this.getWidth() / 16;
            y = this.getHeight() - bitmap.getHeight() - x;

            commanderSprite = new Sprite(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap);
            canvas.drawBitmap(bitmap, x, y, null);
        }

        // Loads the Vanguard Sprite
        if (game.isVanguard()) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.commander);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            int z = this.getWidth() / 16;
            y = this.getHeight() - bitmap.getHeight() - z;
            x = this.getWidth() - bitmap.getWidth() - z;

            vanguardSprite = new Sprite(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap);
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    private void drawWaterMarks() {
        if (poisonWaterMark != null) {
            Bitmap bitmap = poisonWaterMark.getBitmap();
            int x = poisonWaterMark.getX();
            int y = poisonWaterMark.getY();

            canvas.drawBitmap(bitmap, x, y, lifePaint);
        }
    }


    /**
     * Getters and Setters
     **/

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

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

    public boolean isTouchable() {
        return touchable;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }
}
