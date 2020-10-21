package edu.neu.madcourse.networkexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


/***
 * Based on code from https://developer.android.com/training/basics/network-ops/managing.html
 *
 */
public class NetworkInfoActivity extends AppCompatActivity {

    // The BroadcastReceiver that tracks network connectivity changes.
    private NetworkReceiver receiver = new NetworkReceiver();

    private String userNetworkPref = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_info);

        // Registers BroadcastReceiver to track network connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String networkPref = sharedPrefs.getString("networkType", "wifi");
        TextView pref_tv = (TextView)findViewById(R.id.user_pref_textview);
        pref_tv.setText(networkPref);
        userNetworkPref = networkPref;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregisters BroadcastReceiver when app is destroyed.
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    public void checkConnection(View view){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        TextView wifiTV = (TextView)findViewById(R.id.wifi_connected_textview);
        TextView cellTV = (TextView)findViewById(R.id.cell_connected_textview);

        // How to do this changed in Lollipop. Including both versions here.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Even if build version makes this unneccessary, this is left in to show the old way (see the else below)

            Network[] networks = connMgr.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connMgr.getNetworkInfo(mNetwork);
                String info = getDescString(networkInfo);

                switch(networkInfo.getType()){
                    case ConnectivityManager.TYPE_WIFI:
                        wifiTV.setText(info);
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        cellTV.setText(info);
                        break;
                }
            }
        }else{
            // To account for Android API < 21.
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            wifiTV.setText(getDescString(networkInfo));

            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            cellTV.setText(getDescString(networkInfo));
        }

    }

    @NonNull
    private String getDescString(NetworkInfo networkInfo) {
        String info = Boolean.toString((networkInfo.getState() == NetworkInfo.State.CONNECTED));
        info += "; ";
        info += networkInfo.getTypeName();
        info += "; ";
        info += networkInfo.getSubtypeName();
        return info;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager conn = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();

            // Checks the user prefs and the network connection.
            // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
            if ("wi-fi only".equals(userNetworkPref) &&
                    networkInfo != null &&
                    networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                Toast.makeText(context, "Can do data task on Wi-Fi", Toast.LENGTH_SHORT).show();

                // If the setting is ANY network and there is a network connection
                // (which by process of elimination would be mobile)
            } else if ("any".equals(userNetworkPref) &&
                    networkInfo != null) {

                Toast.makeText(context, "Can do data task on mobile connection", Toast.LENGTH_SHORT).show();

                // Otherwise, the app can't download content--either because there is no network
                // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                // is no Wi-Fi connection.
            } else {
                Toast.makeText(context, "Not connected, or can't do data task on mobile. ", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
