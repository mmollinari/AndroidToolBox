package fr.isen.mollinari.androidtoolbox;

import android.content.Intent;
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

    }
}
