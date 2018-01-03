package fr.isen.mollinari.androidtoolbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnValider = findViewById(R.id.button);
        final EditText etIdentifiant = findViewById(R.id.editTextIdentifiant);
        final EditText etMdp = findViewById(R.id.editTextMdp);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String identifiant = etIdentifiant.getText().toString();
                String mdp = etMdp.getText().toString();
                if ("admin".equals(identifiant) && "123".equals(mdp)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("strIdentifiant", identifiant);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
