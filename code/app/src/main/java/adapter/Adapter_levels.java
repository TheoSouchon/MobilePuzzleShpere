package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spherepuzzle.R;

import java.util.List;

import model.Level;

/**
 * Class to represent a Adapter to display the list of level in the "Lvl" activity
 */
public class Adapter_levels extends RecyclerView.Adapter<Adapter_levels.mViewHolder> {

    //List of all the level
    List<Level> game_levels;
    Context myContext;
    Integer maxLvlUnlocked=0;

    public Adapter_levels(List<Level> game_levels,Context context) {
        this.game_levels = game_levels;
        this.myContext = context;
    }

    public Adapter_levels(List<Level> game_levels,Context context, Integer maxLvlUnlocked) {
        this.game_levels = game_levels;
        this.myContext = context;
        this.maxLvlUnlocked=maxLvlUnlocked;
        //Log.d("TAG3", "MAX LEVEL DEBLOQUÉ: "+maxLvlUnlocked);
    }

    /**
     * Create the block view to put the level
     * @param parent : RecycleView in our case
     * @param viewType : code representing the RecycleView
     * @return : the block viewHolder
     */
    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_item_level,parent, false);
        return new mViewHolder(view,maxLvlUnlocked);
    }

    /**
     * Method sort of initalize of each viewHolder
     * @param holder : each block view of our Adapter in our RecycleView
     * @param position : Position and so the level number displayed
     */
    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.display(game_levels.get(position));
        //start click listening on each viewHolder and crate the intent according the level clicked
        holder.level_btn.setOnClickListener((view) -> {
            Log.d("TAG3", "CLICKED IN LVL MENU : "+game_levels.get(position).getNumber());
            if (position == 0) {
                Intent intent = new Intent(myContext,view.Game.class);
                //send data to the next intent
                intent.putExtra("LvlNumber",game_levels.get(position).getNumber());
                intent.putExtra("maxLvlUnlocked",maxLvlUnlocked);
                myContext.startActivity(intent);
            }
            if (position == 1) {
                Intent intent = new Intent(myContext,view.Game.class);
                //send data to the next intent
                intent.putExtra("LvlNumber",game_levels.get(position).getNumber());
                intent.putExtra("maxLvlUnlocked",maxLvlUnlocked);
                myContext.startActivity(intent);
            }
            if (position == 2) {
                Intent intent = new Intent(myContext,view.Game.class);
                //send data to the next intent
                intent.putExtra("LvlNumber",game_levels.get(position).getNumber()+1);
                intent.putExtra("maxLvlUnlocked",maxLvlUnlocked);
                myContext.startActivity(intent);
            }
            else {
                Toast.makeText(myContext,"LEVEL UNAVAILABLE", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return game_levels.size();
    }

    /**
     * Class to represent a blockView to put our data (level)
     */
    public static class mViewHolder extends RecyclerView.ViewHolder {

        private final Button level_btn;
        private final Integer maxLvlUnlocked;

        public mViewHolder(@NonNull View itemView,Integer maxLvlUnlocked) {
            super(itemView);
            level_btn=itemView.findViewById(R.id.btn_level);
            this.maxLvlUnlocked = maxLvlUnlocked;
        }

        @SuppressLint("SetTextI18n")
        void display(Level lvl) {
//            Log.d("TAG3", "lvl number : "+lvl.getNumber());
//            Log.d("TAG3", "max lvl Unlocked : "+maxLvlUnlocked);
            if (lvl.getNumber() > maxLvlUnlocked + 1) {
                level_btn.setEnabled(false);
            }
            level_btn.setText("level "+lvl.getNumber());
        }
    }

}
