package edu.neu.madcourse.numad20f_nicholasgalinski.service;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import edu.neu.madcourse.numad20f_nicholasgalinski.*;

// started from WebServiceActivity start code
// Async was deprecated - https://www.techyourchance.com/asynctask-deprecated/

public class AtYourServiceFragment extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";

    private String mURL;
    private TextView quote_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_at_your_service);

        mURL = "https://zenquotes.io/api/random";
        quote_result = (TextView)findViewById(R.id.result_quote);
    }

    public void callWebserviceButtonHandler(View view){
        PingWebServiceTask task = new PingWebServiceTask();
        new Thread(task).start();

        Toast.makeText(getApplicationContext(),"Quote obtained.", Toast.LENGTH_SHORT).show();
    }

    private class PingWebServiceTask implements Runnable{

        @Override
        public void run() {
            String[] results = new String[2];
            URL url = null;
            try {
                url = new URL(mURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();

                InputStream inputStream = conn.getInputStream();
                final String resp = convertStreamToString(inputStream);

                JSONArray jsonarray = new JSONArray(resp);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String quote = jsonobject.getString("q");
                    String author = jsonobject.getString("a");
                    results[0] = quote;
                    results[1] = author;
                }

            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            }

            Button pingWeb = findViewById(R.id.visit_website);
            pingWeb.setText("Website active. Click again to get a new quote.");

            Button prompt = findViewById(R.id.get_quote);
            prompt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView result_view = (TextView)findViewById(R.id.result_quote);
                    result_view.setText(results[0]);
                }
            });

            Button author = findViewById(R.id.get_author);
            author.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView result_view = (TextView)findViewById(R.id.quote_author);
                    result_view.setText(results[1]);
                }
            });
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}