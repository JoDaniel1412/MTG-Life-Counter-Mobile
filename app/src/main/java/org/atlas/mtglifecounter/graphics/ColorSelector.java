package org.atlas.mtglifecounter.graphics;

import android.content.Context;
import android.view.View;

public class ColorSelector extends View {

    private int color;

    public ColorSelector(Context context) {
        super(context);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
