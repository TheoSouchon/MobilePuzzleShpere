package model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

import collisioner.Collision;

/**
 * Class to represent any entity in a game
 */
public class GameEntities extends View {

    private final Player player;
    private final Player goal;
    private final ArrayList<Player> obstaclePlayer = new ArrayList<>();
    private final Point playerPoint;
    private final Point goalPoint;
    private final Collision col = new Collision();
    private final int radius;
    private final ArrayList<Point> obstaclesPoint;

    /**
     * Constructor
     * @param context
     * @param playerPoint
     * @param goalPoint
     * @param obstacles
     */
    public GameEntities(Context context,Point playerPoint, Point goalPoint, ArrayList<Point> obstacles) {
        super(context);
        this.playerPoint = playerPoint;
        this.obstaclesPoint = obstacles;
        this.goalPoint = goalPoint;

        //rectangle of the player and the goal
        player = new Player(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        goal = new Player(new Rect(100, 100, 200, 200), Color.rgb(0, 0, 255));

        //All of the rectangle for the obstacle
        for (Point pt : obstacles) {
            obstaclePlayer.add(new Player(new Rect(100,100,350,150),Color.rgb(0,150,150)));
        }

        int n=0;
        for (Player pl : obstaclePlayer) {
            pl.update(obstaclesPoint.get(n));
            n++;
        }

        radius = player.getRectangle().right-player.getRectangle().left;
        goal.update(goalPoint);

        //Refresh view
        invalidate();
    }

    /**
     * Method to move the rectangle according the direction and the sens of the movement
     * @param sens : sens of the movement
     * @param yBorderLeft : y position of the left border for the screen
     * @param yBorderRight : y position of the right border for the screen
     * @param xBorderBottom : x position of the bottom border for the screen
     * @param xBorderTop : x position of the top border for the screen
     * @return : result code
     */
    public int update(String sens,float yBorderLeft, float yBorderRight, float xBorderBottom, float xBorderTop) {
        switch (sens) {
            case "right":
                if (!col.isCollidedGoal(new Point(goalPoint.x + 4, goalPoint.y),playerPoint,player)) {
                    if (!col.isCollidedBorder(playerPoint.x,playerPoint.y,yBorderRight,sens,radius)) {
                        if (!col.isCollidedObstacle(playerPoint,player,obstaclesPoint)) {
                            //move the Point of the player
                            player.update(playerPoint);
                            //move the rectangle according his point
                            playerPoint.set(playerPoint.x + 4, playerPoint.y);
                            invalidate();
                        } else return -4;
                    } else return -3;
                } else return -2;
                break;
            case "left":
                if (!col.isCollidedGoal(new Point(goalPoint.x - 4, goalPoint.y),playerPoint,player)) {
                    if (!col.isCollidedBorder(playerPoint.x,playerPoint.y,yBorderLeft,sens,radius)) {
                        if (!col.isCollidedObstacle(playerPoint,player,obstaclesPoint)) {
                            //move the Point of the player
                            player.update(playerPoint);
                            //move the rectangle according his point
                            playerPoint.set(playerPoint.x - 4, playerPoint.y);
                            invalidate();
                        } else return -4;
                    }
                } else return -2;
                break;
            case "up":
                if (!col.isCollidedGoal(new Point(goalPoint.x, goalPoint.y - 4),playerPoint,player)) {
                    if (!col.isCollidedBorder(playerPoint.x,playerPoint.y,xBorderTop,sens,radius)) {
                        if (!col.isCollidedObstacle(playerPoint,player,obstaclesPoint)) {
                            //move the Point of the player
                            player.update(playerPoint);
                            //move the rectangle according his point
                            playerPoint.set(playerPoint.x, playerPoint.y - 4);
                            invalidate();
                        } else return -4;
                    }
                } else return -2;
                break;
            case "down":
                if (!col.isCollidedGoal(new Point(goalPoint.x, goalPoint.y + 4),playerPoint,player)) {
                    if (!col.isCollidedBorder(playerPoint.x,playerPoint.y,xBorderBottom,sens,radius)) {
                        if (!col.isCollidedObstacle(playerPoint,player,obstaclesPoint)) {
                            //move the Point of the player
                            player.update(playerPoint);
                            //move the rectangle according his point
                            playerPoint.set(playerPoint.x, playerPoint.y + 4);
                            invalidate();
                        } else return -4;
                    }
                } else return -2;
                break;
        }
        return 0;
    }

    /**
     * Method called when we add a element in a view or when we refresh the view
     * @param canvas : relative layout of the game
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //draw the player
        player.draw(canvas);
        //draw the goal
        goal.draw(canvas);
        //draw all the obstacles
        for (Player p : obstaclePlayer) {
            p.draw(canvas);
        }
    }
}
