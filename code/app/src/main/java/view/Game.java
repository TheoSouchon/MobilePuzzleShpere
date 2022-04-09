package view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spherepuzzle.R;

import java.util.ArrayList;

import collisioner.Collision;
import loop.TimeLoop;
import model.GameEntities;

/**
 * Class of a game with a player, a goal and some obstacles
 */
public class Game extends AppCompatActivity {

    private TextView tv;
    private TimeLoop loop;
    private Collision col = new Collision();
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener accelerometerEventListener;
    private TextView x;
    private TextView y;
    private TextView z;
    private float xC;
    private float yC;
    private Guideline border_bot;
    private Guideline border_top;
    private Guideline border_right;
    private Guideline border_left;
    private RelativeLayout rl;
    private String TAG = "TAG";
    private GameEntities gameEntities;
    private Thread thread;
    private Point spawnPlayer;
    private Point spawnGoal;
    private ArrayList<Point> obstacles = new ArrayList<Point>();
    private Integer numberlvl;
    private Integer maxLvlUnlocked;

    /**
     * Method called at the creation of the game
     * Set all the findView, create the level, get "level" through intent, and instance the accelerometer sensor
     * @param savedInstanceState
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        //set all find view by id
        tv =  findViewById(R.id.time);
        x = findViewById(R.id.X);
        y = findViewById(R.id.Y);
        z = findViewById(R.id.Z);
        border_bot = findViewById(R.id.border_bot);
        border_top = findViewById(R.id.border_top);
        border_right = findViewById(R.id.border_right);
        border_left = findViewById(R.id.border_left);
        rl = findViewById(R.id.relativeLayout);

        //set time loop
        loop = new TimeLoop(this);
        startLoop();

        //set accelerometer sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //getExtra, used to get variable passed through the precedent intent
        Integer lvlNumber;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                lvlNumber= null;
            } else {
                lvlNumber = extras.getInt("LvlNumber");
                Log.d("TAG3", ""+lvlNumber);
                this.numberlvl = lvlNumber;
            }
        } else {
            lvlNumber = (Integer) savedInstanceState.getSerializable("LvlNumber");
        }

        //getExtra, used to get variable passed through the precedent intent
        Integer maxLvlUnlocked = null;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                maxLvlUnlocked = null;
            } else {
                maxLvlUnlocked = extras.getInt("maxLvlUnlocked");
                this.maxLvlUnlocked = maxLvlUnlocked;
            }
        } else {
            maxLvlUnlocked = (Integer) savedInstanceState.getSerializable("maxLvlUnlocked");
        }

        //create the level according the number of the level
        Log.d("TAG3", "LVL RECUP "+lvlNumber);
        switch (lvlNumber) {
            case 1:
                spawnPlayer = new Point(100,100);
                spawnGoal = new Point(600,100);
                obstacles.add(new Point(800,150));
                obstacles.add(new Point(700,700));
                obstacles.add(new Point(900,900));
                break;
            case 2:
                spawnPlayer = new Point(100,100);
                spawnGoal = new Point(100,700);
                obstacles.add(new Point(800,150));
                obstacles.add(new Point(700,700));
                obstacles.add(new Point(900,900));
                break;
            case 3:
                spawnPlayer = new Point(100,100);
                spawnGoal = new Point(700,700);
                obstacles.add(new Point(600,150));
                obstacles.add(new Point(700,700));
                obstacles.add(new Point(900,900));
                break;
            default:
                spawnPlayer = new Point(400,400);
                spawnGoal = new Point(400,700);
                obstacles.add(new Point(800,150));
                obstacles.add(new Point(700,700));
                obstacles.add(new Point(900,900));
                break;
        }

        //set panel to draw
        gameEntities = new GameEntities(this,spawnPlayer,spawnGoal,obstacles);

        if(accelerometer == null) {
            Toast.makeText(this, "The device has no gyroscope !",Toast.LENGTH_SHORT).show();
            finish();
        }

        //start listening from sensor of accelerometer
        accelerometerEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x1, y1, z1;
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    x1 = event.values[0];
                    y1 = event.values[1];
                    z1 = event.values[2];

                    xC=x1;
                    yC=y1;

                    x.setText(Float.toString(x1));
                    y.setText(Float.toString(y1));
                    z.setText(Float.toString(z1));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //drawn the entities
        rl.addView(gameEntities);
    }

    /**
     * Method which choose the sens of the movement
     * according the sensor and the inclination
     */
    public void movement() {
        //down
        if(yC>3) { animation("down"); }

        //up
        if (yC<-3)  { animation("up"); }

        //right
        if (xC<-3) { animation("right"); }

        //left
        if (xC>3) { animation("left"); }
    }

    /**
     * Method to update the entities in a game and to stop the game
     * @param sens : direction of the movement as it should be
     */
    public void update(String sens) {
        //update entities
        int code=gameEntities.update(sens,border_left.getX(),border_right.getX(),border_bot.getY(),border_top.getY());

        //if player reach goal
        if (code==-2) {
            //stop thread time and display win screen
            loop.setLive(false);
            thread.interrupt();
            Log.d(TAG, "update: update WIN");
            Intent intent = new Intent(this, WinPage.class);
            intent.putExtra("numberLvl",this.numberlvl);
            intent.putExtra("maxLvlUnlocked",this.maxLvlUnlocked);
            startActivity(intent);
        }

        //if player reach obstacle
        if (code == -4) {
            //stop thread time and display game over screen
            loop.setLive(false);
            thread.interrupt();
            Log.d(TAG, "update: update GAME OVER");
            Intent intent = new Intent(this, GameOver.class);
            Log.d("TAG3", "LEVEL SEND TO RETRY "+this.numberlvl);
            intent.putExtra("numberLvl",this.numberlvl);
            intent.putExtra("maxLvlUnlocked",this.maxLvlUnlocked);
            startActivity(intent);
        }
    }

    /**
     * Method number 1 to make sensor accelerometer works good
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerometerEventListener);
    }

    /**
     * Method number 2 to make sensor accelerometer works good
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerEventListener,accelerometer,SensorManager.SENSOR_DELAY_FASTEST);
    }


    /**
     * Display the time on the screen each seconds
     * @param seconds actual time in seconds to display
     */
    public void setTime(int seconds) {
        tv.setText(Integer.toString(seconds));
    }

    /**
     * Animate the movement of an object
     */
    public void animation(String direction) {

        switch (direction) {
            case "right" :
                update("right");
                break;
            case "left" :
                update("left");
                break;
            case "up" :
                update("up");
                break;
            case "down" :
                update("down");
                break;
        }
    }

    /**
     * Start the timer of a game (start the thread)
     */
    public void startLoop() {
        thread = new Thread(loop);
        thread.setDaemon(true);
        thread.start();
    }

}