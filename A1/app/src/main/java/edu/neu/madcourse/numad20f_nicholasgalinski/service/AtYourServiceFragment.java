package edu.neu.madcourse.numad20f_nicholasgalinski;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class AtYourServiceFragment extends AppCompatActivity {

    private TextView mTitleTextView;
    private EditText mURLEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_at_your_service);

        mTitleTextView = (TextView)findViewById(R.id.result_textview);
        mURLEditText = findViewById(R.id.url_edittext);
    }

}
