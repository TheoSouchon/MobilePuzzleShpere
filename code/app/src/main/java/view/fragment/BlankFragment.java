package view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spherepuzzle.R;

/**
 * Class to represent the fragment of the rules on the home page
 */
public class BlankFragment extends Fragment {

    private TextView textView2;

    /**
     * Method called at the creation of the game
     * Set all the findView, get variable through intent
     * @param savedInstanceState
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank , container,true);
        textView2 = rootView.findViewById(R.id.textView2);
        return rootView;
    }
}