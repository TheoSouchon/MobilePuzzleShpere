package view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.spherepuzzle.R;

/**
 * Class to represent the win screen
 */
public class WinPage extends AppCompatActivity  implements View.OnClickListener{

    private Button btnNext ;
    private Button btnLevel;
    private Integer lvlNumber;
    private Integer maxLvlUnlocked;

    /**
     * Method called at the creation of the game
     * Set all the findView, get variable through intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_page);

        //set the find view view by id
        btnNext = findViewById(R.id.nextBtn);
        btnLevel = findViewById(R.id.lvlBtn2);
        btnNext.setOnClickListener(this);
        btnLevel.setOnClickListener(this);

        //getExtra, used to get variable passed through the precedent intent
        Integer lvlNumber;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                lvlNumber= null;
            } else {
                lvlNumber = extras.getInt("numberLvl");
                this.lvlNumber = lvlNumber;
            }
        } else {
            lvlNumber = (Integer) savedInstanceState.getSerializable("numberLvl");
        }

        //getExtra, used to get variable passed through the precedent intent
        Integer maxLvlUnlocked = null;
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
     * Method called when a click is detected
     * @param v : Actual View display on the screen
     * Check if permission are already granted or not
     */
    @Override
    public void onClick(View v) {
        Log.d("TAG", "onClick: !");

        // if the button "next level" is clicked
        if(v.getId() == R.id.nextBtn) {
            Log.d("TAG3", "LVL NEXT : "+(lvlNumber+1));
            Intent intent = new Intent(this,view.Game.class);
            intent.putExtra("LvlNumber",(lvlNumber+1));
            if(maxLvlUnlocked < this.lvlNumber) {
                intent.putExtra("maxLvlUnlocked",this.lvlNumber);
            } else  {
                intent.putExtra("maxLvlUnlocked",this.maxLvlUnlocked);
            }
            startActivity(intent);
        }

        // if the button to return on the level menu
        if(v.getId() == R.id.lvlBtn2) {
            Intent intent = new Intent(this,view.Lvl.class);
            if(maxLvlUnlocked < this.lvlNumber) {
                intent.putExtra("numberLvlUnlocked",this.lvlNumber);
            } else {
                intent.putExtra("numberLvlUnlocked",this.maxLvlUnlocked);
            }
            startActivity(intent);
        }
    }
}