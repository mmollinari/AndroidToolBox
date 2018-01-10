package fr.isen.mollinari.androidtoolbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String identifiant = getIntent().getStringExtra("strIdentifiant");
        String message = getString(R.string.home_welcome) + " : \n" + identifiant;
        ((TextView)findViewById(R.id.tvBonjour)).setText(message);

        findViewById(R.id.ivCdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LifeCycleActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.ivData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StorageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.ivPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PermissionActivity.class);
                startActivity(intent);
            }
        });

    }

    public void logOut(View v) {

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.USER_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", "");
        editor.putString("mdp", "");
        editor.apply();

        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
