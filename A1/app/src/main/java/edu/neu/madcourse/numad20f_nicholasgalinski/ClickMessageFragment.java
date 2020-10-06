package edu.neu.madcourse.numad20f_nicholasgalinski;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClickMessageFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
    }

    public void clickLetter(View v) {
        String pressed = "Pressed: ";
        Button btn = (Button) v;
        TextView tv = (TextView) findViewById(R.id.textview_pressed);
        tv.setText(pressed + btn.getText());
    }
}