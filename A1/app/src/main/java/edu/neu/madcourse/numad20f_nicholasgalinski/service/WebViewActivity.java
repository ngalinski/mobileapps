package edu.neu.madcourse.networkexample;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    private EditText mWebDestEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = (WebView)findViewById(R.id.webview);
        mWebDestEditText = (EditText)findViewById(R.id.webview_edit_text);


        // Setting the WebViewClient to allow the WebView to handle
        // redirects within the WebView, as opposed to being launched in a browser.

        mWebView.setWebViewClient(new WebViewClient() {


            // Deprecated in API 24, but the alternative is not compatible with Android <19
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    public void loadWebsite(View view){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mWebDestEditText.getText().toString());
    }
}
