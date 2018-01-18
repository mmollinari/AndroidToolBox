package fr.isen.mollinari.androidtoolbox;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WebServiceTask extends AsyncTask<String, Void, String> {

    private CallBackInterface callBackInterface;

    public WebServiceTask(CallBackInterface instance) {
        this.callBackInterface = instance;
    }

    @Override
    protected String doInBackground(String... params) {

        String result="";
        try
        {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = convertInputStreamToString(urlConnection.getInputStream());
            }
        }
        catch(Exception exception)  {
            exception.printStackTrace();
        }
        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        if(result != null && !result.isEmpty()) {
            callBackInterface.success(result);
        }
        else {
            callBackInterface.error();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

}
