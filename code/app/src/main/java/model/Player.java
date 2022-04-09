package model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Class to represent a player in a game
 */
public class Player implements GameObject {

    private final Rect rectangle;
    private final int color;

    /**
     * constructor
     * @param rectangle : rectangle of player
     * @param color : color of the player
     */
    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Method called when Player is added to the view or refresh on the view
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    /**
     * Method which update the rectangle on the screen
     * @param point : Point which position the rectangle player
     */
    public void update(Point point) {
        rectangle.set(point.x-100,point.y-100,point.x+100,point.y+100);
    }

    public Rect getRectangle() {
        return rectangle;
    }
}
