package view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.spherepuzzle.R;
import java.util.ArrayList;
import java.util.List;
import adapter.Adapter_levels;
import model.Level;

/**
 * Class to represent the level choose screen
 */
public class Lvl extends AppCompatActivity {

    // the level are put in a adapter which manage them
    // the adapter is put in the recyclerView
    private List<Level> game_levels = new ArrayList<>();
    private Adapter_levels levelAdapter;
    private RecyclerView recyclerViewLevel;
    private Integer maxLvlUnlocked;


    /**
     * Method called at the creation of the game
     * Set all the findView, adapter, the level in the game
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl);

        //getExtra, used to get variable passed through the precedent intent
        Integer lvlNumber;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                maxLvlUnlocked= null;
            } else {
                lvlNumber = extras.getInt("numberLvlUnlocked");
                this.maxLvlUnlocked = lvlNumber;
            }
        } else {
            maxLvlUnlocked = (Integer) savedInstanceState.getSerializable("LvlNumber");
        }

        recyclerViewLevel = findViewById(R.id.recyclerViewLevels);

        game_levels.add(new Level(1));
        game_levels.add(new Level(2));
        game_levels.add(new Level(3));
        game_levels.add(new Level(4));
        if(maxLvlUnlocked==null) {
            levelAdapter= new Adapter_levels(game_levels,this);
        } else {
            levelAdapter= new Adapter_levels(game_levels,this,maxLvlUnlocked);
        }


        recyclerViewLevel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewLevel.setAdapter(levelAdapter);
    }

}