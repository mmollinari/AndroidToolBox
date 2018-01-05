package fr.isen.mollinari.androidtoolbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String GOOD_IDENTIFIANT = "admin";
    private final String GOOD_MDP = "123";

    public static final String USER_PREFS = "user_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        String savedIdentifiant = sharedPreferences.getString("id", "");
        String savedMdp = sharedPreferences.getString("mdp", "");

        if(savedIdentifiant.equals(GOOD_IDENTIFIANT) && savedMdp.equals(GOOD_MDP)) {
            goToHome(savedIdentifiant, true);
        }

        Button btnValider = findViewById(R.id.button);
        final EditText etIdentifiant = findViewById(R.id.editTextIdentifiant);
        final EditText etMdp = findViewById(R.id.editTextMdp);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String identifiant = etIdentifiant.getText().toString();
                String mdp = etMdp.getText().toString();
                if (GOOD_IDENTIFIANT.equals(identifiant) && GOOD_MDP.equals(mdp)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id", identifiant);
                    editor.putString("mdp", mdp);
                    editor.apply();
                    goToHome(identifiant, false);
                } else {
                    Toast.makeText(LoginActivity.this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void goToHome(String identifiant, boolean clearCache) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("strIdentifiant", identifiant);
        if(clearCache) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        if(clearCache) finish();
    }
}
