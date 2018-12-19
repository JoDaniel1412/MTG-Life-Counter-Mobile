package org.atlas.mtglifecounter.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import org.atlas.mtglifecounter.R;

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
        drawName();
        drawDamage();
    }

    private void loadPaints() {
        int color = Color.BLACK;
        textPaint.setTextSize(this.getWidth() / 4);
        textPaint.setColor(color);
    }

    private void drawName() {
        Rect bounds = new Rect();
        textPaint.getTextBounds(name, 0, name.length(), bounds);

        float hw = bounds.width() / 2;
        float hh = bounds.height() / 2;
        float x = canvas.getWidth() / 2 - hw;
        float y = canvas.getHeight() / 3 + hh;

        canvas.drawText(name, x, y, textPaint);
    }

    private void drawDamage() {
        EditText damageText = new EditText(this.getContext());
        damageText.setInputType(InputType.TYPE_CLASS_NUMBER);
        damageText.setText(String.valueOf(damage));
        damageText.setTextSize(textPaint.getTextSize());

        float x = canvas.getWidth() / 2 - damageText.getWidth();
        float y = canvas.getHeight() / 3 + damageText.getHeight();

        damageText.setX(x);
        damageText.setY(y);
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
