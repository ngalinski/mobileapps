package edu.neu.madcourse.numad20f_nicholasgalinski;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.neu.madcourse.numad20f_nicholasgalinski.link_collector.*;
import edu.neu.madcourse.numad20f_nicholasgalinski.locator.*;

public class MainActivity extends AppCompatActivity {

    private static String pressedText = "Pressed: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "More surprises to come!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void aboutText(View v) {
        TextView t = findViewById(R.id.about_text);
        if (t.getVisibility() == View.VISIBLE) {
            t.setVisibility(View.GONE);
        } else {
            t.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    public static String getPressedText() {
        return pressedText;
    }

    public static void setPressedText(String textToSet) {
        pressedText = textToSet;
    }
*/
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_third:
                Intent intent = new Intent(MainActivity.this, ClickMessageFragment.class);
                startActivity(intent);
                break;
            case R.id.link_collector:
                startActivity(new Intent(MainActivity.this, LinkCollectorFragment.class));
                break;
            case R.id.button_locator:
                startActivity(new Intent(MainActivity.this, LocatorFragment.class));
                break;
        }
    }
}