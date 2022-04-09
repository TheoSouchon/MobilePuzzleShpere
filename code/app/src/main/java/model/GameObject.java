package model;

import android.graphics.Canvas;

/**
 * Interface of any entities present in a game
 * entities : player, goal, obstacles
 */
public interface GameObject {
    void draw(Canvas canvas);
}
