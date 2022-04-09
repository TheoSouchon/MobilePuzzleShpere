package view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.spherepuzzle.R;

/**
 * Class used in the adapter to display the multiple level
 */
public class item_level extends AppCompatActivity {

    /**
     * Method called at the creation of the game
     * Set intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_level);
    }
}