package org.atlas.mtglifecounter.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class CommanderCounter extends View {

    private Canvas canvas;
    private String name;
    private int damage;

    // Paints
    Paint textPaint = new Paint();

    public CommanderCounter(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        loadPaints();
        drawTexts();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) pressedCommanderCounter(event.getY());
        this.invalidate();
        return true;
    }

    private void pressedCommanderCounter(float y) {
        float hh = canvas.getHeight() / 2;
        if (y < hh) damage++;
        if (y >= hh) damage--;
    }

    private void loadPaints() {
        int color = Color.BLACK;
        textPaint.setTextSize(this.getWidth() / 4);
        textPaint.setColor(color);
    }

    private void drawTexts() {
        String name = this.name;
        String damageStr = String.valueOf(damage);

        Rect nameBounds = new Rect();
        Rect damageBounds = new Rect();
        textPaint.getTextBounds(name, 0, name.length(), nameBounds);
        textPaint.getTextBounds(damageStr, 0, damageStr.length(), damageBounds);

        int hw = canvas.getWidth() / 2;
        int hh = canvas.getHeight() / 2;

        float x = hw - nameBounds.width() / 2;
        float y = hh - nameBounds.height() / 3;

        float x2 = hw - damageBounds.width() / 2;
        float y2 = hh + damageBounds.height();


        // Draws the texts in the canvas
        canvas.drawText(name, x, y, textPaint);
        canvas.drawText(damageStr, x2, y2, textPaint);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
