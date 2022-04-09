package loop;

import android.util.Log;

import java.util.Timer;

import view.Game;

/**
 * Class to represent the time loop of a game
 */
public class TimeLoop implements Runnable {

    private boolean live = true;
    private int seconds = 0;
    private Game game;
    private int tick;

    public TimeLoop(Game game) {
        this.game = game;
    }


    /**
     * method run each 10 ms
     * tick : used for animated movement : 10 ms
     * seconds : used for the timer in game : 1000ms
     */
    @Override
    public void run() {

        while (live) {
            try {
                //Done each tick
                Thread.sleep(10);
                tick+=10;
                //update movement of entities
                game.runOnUiThread(() -> game.movement());

                //Done each second
                if(tick%1000==0) {
                    Log.e("TIME :", Integer.toString(seconds));
                    seconds++;
                    //increment and set time as seconds
                    game.runOnUiThread(() -> game.setTime(seconds));
                }

            } catch (InterruptedException e) {
                    e.printStackTrace();
                    live = false;
            }
        }
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
