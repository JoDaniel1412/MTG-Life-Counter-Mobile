package org.atlas.mtglifecounter.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import org.atlas.mtglifecounter.controllers.GameActivity;

public class ColorSelector extends View {

    private int color;
    private GameActivity gameActivity;

    public ColorSelector(Context context) {
        super(context);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) gameActivity.closeColorLayout(color);

        return true;
    }


    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }
}
