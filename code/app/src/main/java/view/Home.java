package view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spherepuzzle.R;

/**
 * Class of home page, the first intent when the app is started
 */
public class Home extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    TextView tv;
    private int FINE_LOCATION_RQ = 101;
    private int CAMERA_RQ = 102;

    /**
     * Method called at the creation of the game
     * Set all the findView and the click listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("T0","in the 'onCreate'");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnPlay);
        tv =  findViewById(R.id.Title);
        btn.setOnClickListener(this);
    }

    /**
     * Method called on the app is paused
     */
    @Override
    protected void onPause() {
        Log.d("T1","in the 'onPause'");
        super.onPause();
    }

    /**
     * Method called when the app is stopped
     */
    @Override
    protected void onStop() {
        Log.d("T2","in the 'onStop'");
        super.onStop();
    }

    /**
     * Light save, not persistence
     * @param outState event Bundle
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("T3","in the 'onSave method'");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d("T4","in the 'onPause'");
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        Log.d("T5","in the 'onDestroy'");
        super.onDestroy();
    }


    /**
     * Method called when a click is detected
     * @param v : Actual View display on the screen
     * Check if permission are already granted or not
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnPlay) {
            checkForPermissions(Manifest.permission.ACCESS_FINE_LOCATION,"location",FINE_LOCATION_RQ);
//            Toast.makeText(getApplicationContext(),"permission granted",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this,view.Lvl.class);
//            startActivity(intent);
        }
    }

    /**
     * Method called to ask for permission
     * @param requestCode : number of the permission asked
     * @param permissions : all the permissions
     * @param grantResults : permission already granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length==0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"permission refused",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"permission granted",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,view.Lvl.class);
            startActivity(intent);
        }
    }

    /**
     * Method to check if the permission wanted are checked or not
     * @param permission : list of the permissions asked
     * @param code : code of the permission. ex : 101 = GPS
     */
    private void checkForPermissions (String permission,String name, int code) {
        //Can use permissions from the manifest only from a certain version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permissions[] = new String[5];
            permissions[0]=permission;

            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this,view.Lvl.class);
                startActivity(intent);
            } else {
                //You can directly ask for the permission.
                //The registered ActivityResultCallback gets the result of this request.
                requestPermissions(permissions, code);
            }
        } else {
            Intent intent = new Intent(this,view.Lvl.class);
            startActivity(intent);
        }
    }

}