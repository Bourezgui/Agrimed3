package com.example.ynaccache.agrimed2.other;

/**
 * Created by y.naccache on 27/11/2017.
 */

public interface DrawableClickListener {

    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);
}