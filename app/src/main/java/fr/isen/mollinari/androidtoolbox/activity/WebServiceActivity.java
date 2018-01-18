package fr.isen.mollinari.androidtoolbox.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.isen.mollinari.androidtoolbox.CallBackInterface;
import fr.isen.mollinari.androidtoolbox.R;
import fr.isen.mollinari.androidtoolbox.UsersAdapter;
import fr.isen.mollinari.androidtoolbox.WebServiceTask;
import fr.isen.mollinari.androidtoolbox.model.UserResults;

public class WebServiceActivity extends AppCompatActivity {

    private UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        String url = "https://randomuser.me/api/?results=10";

        new WebServiceTask(new CallBackInterface() {

            @Override
            public void success(String jsonResponse) {
                UserResults userResults = parseUserResultJSON(jsonResponse);
                if(userResults != null) {

                    RecyclerView recyclerView =findViewById(R.id.recyclerView);

                    mAdapter = new UsersAdapter(userResults.getResults(), WebServiceActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(WebServiceActivity.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void error() {
                Log.e("WebServiceActivity", "Le serveur n'a pas pu récupérer les résultats");
            }
        }).execute(url);
    }

    public static UserResults parseUserResultJSON(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, UserResults.class);
    }
}
