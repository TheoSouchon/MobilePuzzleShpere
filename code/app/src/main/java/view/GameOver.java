package view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.spherepuzzle.R;

/**
 * Class of the game over screen, can retry or return to level choose screen
 */
public class GameOver extends AppCompatActivity implements View.OnClickListener{
    private Button btnRetry ;
    private Button btnLevel;
    private Integer numberlvl;
    private Integer maxLvlUnlocked;

    /**
     * Method called at the creation of the game
     * Set all the findView, create the level, get "level" through intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //All the find view by id
        btnRetry = findViewById(R.id.retry);
        btnLevel = findViewById(R.id.lvlBtn);
        btnRetry.setOnClickListener(this);
        btnLevel.setOnClickListener(this);

        //getExtra, used to get variable passed through the precedent intent
        Integer lvlNumber;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                lvlNumber= null;
            } else {
                lvlNumber = extras.getInt("numberLvl");
                this.numberlvl = lvlNumber;
            }
        } else {
            lvlNumber = (Integer) savedInstanceState.getSerializable("numberLvl");
        }

        //getExtra, used to get variable passed through the precedent intent
        Integer maxLvlUnlocked;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                maxLvlUnlocked= null;
            } else {
                maxLvlUnlocked = extras.getInt("maxLvlUnlocked");
                this.maxLvlUnlocked = maxLvlUnlocked;
            }
        } else {
            maxLvlUnlocked = (Integer) savedInstanceState.getSerializable("maxLvlUnlocked");
        }
    }


    /**
     * Method call when a click is detected
     * @param v : Actual View display on the screen
     */
    @Override
    public void onClick(View v) {
        Log.d("TAG", "onClick: !");

        //if you click on the button to retry the level
        if(v.getId() == R.id.retry) {
            Log.d("TAG3", "LVL RETRIED "+numberlvl);
            Intent intent = new Intent(this,view.Game.class);
            intent.putExtra("LvlNumber",this.numberlvl);
            intent.putExtra("maxLvlUnlocked",this.maxLvlUnlocked);
            startActivity(intent);
        }

        //if you click on the button to return on the level choose screen
        if(v.getId() == R.id.lvlBtn) {
            //Log.d("TAG3", "MAX LVL "+maxLvlUnlocked);
            Intent intent = new Intent(this,view.Lvl.class);
            intent.putExtra("numberLvlUnlocked",this.maxLvlUnlocked);
            startActivity(intent);
        }
    }
}