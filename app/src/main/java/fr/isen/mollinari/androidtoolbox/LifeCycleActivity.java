package fr.isen.mollinari.androidtoolbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LifeCycleActivity extends AppCompatActivity {

    private TextView tvLog;
    private boolean isActivityRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);

        tvLog = findViewById(R.id.tvLog);
        isActivityRunning = true;

        LifeCycleFragment lifeCycleFragment;
        if(savedInstanceState != null){
            lifeCycleFragment = new LifeCycleFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, lifeCycleFragment)
                    .commit();
        }
        else {
            getSupportFragmentManager()
                    .findFragmentById(R.id.fragment);
        }


        showLog("Cycle de vie Activité : onCreate");
    }

    protected void onStart() {
        super.onStart();
        showLog("Cycle de vie Activité : onStart");
    }

    protected void onRestart() {
        super.onRestart();
        showLog("Cycle de vie Activité : onRestart");
    }

    protected void onResume() {
        super.onResume();
        showLog("Cycle de vie Activité : onResume");
    }

    protected void onPause() {
        super.onPause();
        showLog("Cycle de vie Activité : onPause");
    }

    protected void onStop() {
        super.onStop();
        isActivityRunning = false;
        showLog("Cycle de vie Activité : onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        showLog("Cycle de vie Activité : onDestroy");
    }


    public void showLog(String logMessage) {
        if(isActivityRunning) {
            String message = tvLog.getText() + logMessage + "\n";
            tvLog.setText(message);
        }
        else {
            Toast.makeText(this, logMessage, Toast.LENGTH_SHORT).show();
        }
        Log.d("LifeCycleActivity", logMessage);
    }
}
