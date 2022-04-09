package collisioner;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import model.Player;

/**
 * Class to represent a manager for all the collision
 */
public class Collision extends AppCompatActivity {

    /**
     * Method to make a object collide with border
     * @param PointX : X position of the object
     * @param PointY : Y position of the object
     * @param borderPos : border position (X or Y)
     * @param sens : direction of the movement
     * @param radius : radius of the object
     * @return : true if collision else false
     */
    public boolean isCollidedBorder(float PointX,float PointY, float borderPos, String sens, int radius) {

        switch (sens) {
            case "right" :
                if (PointX+radius>=borderPos) {
                    return true;
                }
                break;
            case "left" :
                if (PointX-radius<=borderPos) {
                    return true;
                }
                break;
            case "up" :
                if (PointY-radius<=borderPos) {
                    return true;
                }
                break;
            case "down" :
                if (PointY+radius>=borderPos) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Method to make the player collide with the goal
     * @param pointGoal : object the type of Point representing the center position of the goal
     * @param playerPoint : object the type of Point representing the center position of the player
     * @param player : object the type of a player
     * @return : true if collision else false
     */
    public boolean isCollidedGoal(Point pointGoal, Point playerPoint, Player player) {
        if (Math.sqrt((pointGoal.y - playerPoint.y) * (pointGoal.y - playerPoint.y) + (pointGoal.x - playerPoint.x) * (pointGoal.x - playerPoint.x)) <= player.getRectangle().right-player.getRectangle().left) {
            return true;
        }
        return false;
    }

    /**
     * Method to make the player collide with the obstacles
     * @param playerPoint : object the type of Point representing the center position of the player
     * @param player : object the type of a player
     * @param obstaclePoint : List the type of Point representing the center position of each obstacle
     * @return : true if collision else false
     */
    public boolean isCollidedObstacle(Point playerPoint, Player player, ArrayList<Point> obstaclePoint) {
        for (Point p : obstaclePoint) {
            if (Math.sqrt((p.y - playerPoint.y) * (p.y - playerPoint.y) + (p.x - playerPoint.x) * (p.x - playerPoint.x)) <= player.getRectangle().right-player.getRectangle().left) {
                return true;

            }
        }
        return false;
    }
}
