package fr.isen.mollinari.androidtoolbox;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

public class StorageActivity extends AppCompatActivity {

    public static final String JSON_FILE = "json_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        saveDataToFile();
    }

    public void saveDataToFile() {
        FileOutputStream fos;
        File file = getFileStreamPath(JSON_FILE);
        try {
            if(file == null || !file.exists()) {
                fos = openFileOutput(JSON_FILE, Context.MODE_PRIVATE);
                String data = "{ 'nom': 'Mollinari', 'prenom': 'Marc', 'date_naissance': '01/10/1990' }";
                fos.write(data.getBytes());
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void showDataFromFile(View view) {
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(JSON_FILE);
            String strData = convertInputStreamToString(inputStream);
            if (!strData.equals("")) {
                JSONObject jsonData = new JSONObject(strData);

                String strDate = jsonData.optString("date_naissance");
                String[] arrayStr = strDate.split("/");

                String nom = getString(R.string.storage_name) + jsonData.optString("nom");
                String prenom = getString(R.string.storage_first_name) + jsonData.optString("prenom");
                String dateNaissance = getString(R.string.storage_date) + jsonData.optString("date_naissance");

                String age = getString(R.string.storage_age) + getAge(Integer.parseInt(arrayStr[0]),Integer.parseInt(arrayStr[1]),Integer.parseInt(arrayStr[2]));

                ((TextView)findViewById(R.id.tvNom)).setText(nom);
                ((TextView)findViewById(R.id.tvPrenom)).setText(prenom);
                ((TextView)findViewById(R.id.tvDate)).setText(dateNaissance);
                ((TextView)findViewById(R.id.tvAge)).setText(age);
            }
            inputStream.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        StringBuilder stringBuffer = new StringBuilder("");
        String readLine = bufferedReader.readLine();

        while(readLine != null){
            stringBuffer.append(readLine);
            stringBuffer.append("\n");
            readLine = bufferedReader.readLine();
        }

        inputStream.close();
        return stringBuffer.toString();

    }

    private String getAge(int day, int month, int year){
        Calendar dateNaissance = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dateNaissance.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dateNaissance.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dateNaissance.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age + " ans";
    }
}
